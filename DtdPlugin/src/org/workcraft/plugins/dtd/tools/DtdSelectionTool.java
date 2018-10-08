package org.workcraft.plugins.dtd.tools;

import org.workcraft.dom.Node;
import org.workcraft.dom.visual.ConnectionHelper;
import org.workcraft.dom.visual.HitMan;
import org.workcraft.dom.visual.VisualModel;
import org.workcraft.dom.visual.connections.ControlPoint;
import org.workcraft.dom.visual.connections.VisualConnection;
import org.workcraft.gui.DesktopApi;
import org.workcraft.gui.events.GraphEditorMouseEvent;
import org.workcraft.gui.graph.tools.GraphEditor;
import org.workcraft.gui.graph.tools.SelectionTool;
import org.workcraft.plugins.dtd.*;
import org.workcraft.plugins.dtd.utils.DtdUtils;
import org.workcraft.workspace.WorkspaceEntry;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DtdSelectionTool extends SelectionTool {

    public DtdSelectionTool() {
        super(false, false, false, false);
    }

    @Override
    public void setPermissions(final GraphEditor editor) {
        WorkspaceEntry we = editor.getWorkspaceEntry();
        we.setCanModify(true);
        we.setCanSelect(true);
        we.setCanCopy(false);
    }

    public DtdSelectionTool(boolean enableGroupping, boolean enablePaging, boolean enableFlipping, boolean enableRotating) {
        super(enableGroupping, enablePaging, enableFlipping, enableRotating);
    }

    @Override
    public void mouseClicked(GraphEditorMouseEvent e) {
        boolean processed = false;
        WorkspaceEntry we = e.getEditor().getWorkspaceEntry();
        VisualDtd dtd = (VisualDtd) e.getModel();
        if ((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() > 1)) {
            Node node = HitMan.hitFirstInCurrentLevel(e.getPosition(), dtd);
            if (node instanceof VisualSignal) {
                VisualSignal signal = (VisualSignal) node;
                Signal.State state = getLastState(dtd, signal);
                TransitionEvent.Direction direction = getDesiredDirection(state, e.getKeyModifiers());
                if (direction != null) {
                    we.saveMemento();
                    dtd.appendSignalEvent(signal, direction);
                }
                processed = true;
            }
        }
        if (!processed) {
            super.mouseClicked(e);
        }
    }

    private Signal.State getLastState(VisualDtd dtd, VisualSignal signal) {
        VisualExitEvent exit = signal.getVisualSignalExit();
        VisualEvent event = DtdUtils.getPrevVisualEvent(dtd, exit);
        return DtdUtils.getNextState(event.getReferencedSignalEvent());
    }

    private TransitionEvent.Direction getDesiredDirection(Signal.State state, int mask) {
        if (state == Signal.State.UNSTABLE) {
            switch (mask) {
            case MouseEvent.SHIFT_DOWN_MASK:
                return TransitionEvent.Direction.RISE;
            case MouseEvent.CTRL_DOWN_MASK:
                return TransitionEvent.Direction.FALL;
            default:
                return TransitionEvent.Direction.STABILISE;
            }
        }
        if (state == Signal.State.HIGH) {
            switch (mask) {
            case MouseEvent.SHIFT_DOWN_MASK | MouseEvent.CTRL_DOWN_MASK:
                return TransitionEvent.Direction.DESTABILISE;
            default:
                return TransitionEvent.Direction.FALL;
            }
        }
        if (state == Signal.State.LOW) {
            switch (mask) {
            case MouseEvent.SHIFT_DOWN_MASK | MouseEvent.CTRL_DOWN_MASK:
                return TransitionEvent.Direction.DESTABILISE;
            default:
                return TransitionEvent.Direction.RISE;
            }
        }
        return null;
    }

    @Override
    public void beforeSelectionModification(final GraphEditor editor) {
        super.beforeSelectionModification(editor);
        VisualModel model = editor.getModel();
        ArrayList<Node> selection = new ArrayList<>(model.getSelection());
        for (Node node : selection) {
            VisualConnection connection = null;
            if (node instanceof VisualConnection) {
                connection = (VisualConnection) node;
            } else if (node instanceof ControlPoint) {
                connection = ConnectionHelper.getParentConnection((ControlPoint) node);
            }
            if (connection instanceof VisualLevelConnection) {
                model.removeFromSelection(node);
            }
        }
    }

    @Override
    public String getHintText(final GraphEditor editor) {
        return "Double-click on a signal to add an event. In unstable state hold Shift to rise, " +
                DesktopApi.getMenuKeyMaskName() + " to fall; in high/low state hold both keys to destabilise.";
    }

}
