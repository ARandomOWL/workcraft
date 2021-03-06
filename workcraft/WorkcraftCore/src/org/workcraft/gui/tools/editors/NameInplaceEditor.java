package org.workcraft.gui.tools.editors;

import org.workcraft.dom.visual.VisualComponent;
import org.workcraft.dom.visual.VisualModel;
import org.workcraft.exceptions.ArgumentException;
import org.workcraft.gui.tools.GraphEditor;
import org.workcraft.utils.DialogUtils;
import org.workcraft.workspace.WorkspaceEntry;

public class NameInplaceEditor extends AbstractInplaceEditor {
    private final VisualComponent component;
    private final VisualModel model;

    public NameInplaceEditor(GraphEditor editor, VisualComponent component) {
        super(editor, component);
        this.component = component;
        this.model = editor.getModel();
    }

    @Override
    public void processResult(String text) {
        WorkspaceEntry we = getEditor().getWorkspaceEntry();
        try {
            we.captureMemento();
            model.setMathName(component, text);
            we.saveMemento();
        } catch (ArgumentException e) {
            DialogUtils.showError(e.getMessage());
        }
    }

}
