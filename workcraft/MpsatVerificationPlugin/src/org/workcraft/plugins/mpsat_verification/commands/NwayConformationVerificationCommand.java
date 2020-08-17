package org.workcraft.plugins.mpsat_verification.commands;

import org.workcraft.Framework;
import org.workcraft.commands.ScriptableDataCommand;
import org.workcraft.gui.MainWindow;
import org.workcraft.gui.workspace.Path;
import org.workcraft.plugins.mpsat_verification.gui.NwayConformationDialog;
import org.workcraft.plugins.mpsat_verification.tasks.NwayConformationChainResultHandlingMonitor;
import org.workcraft.plugins.mpsat_verification.tasks.NwayConformationTask;
import org.workcraft.plugins.mpsat_verification.utils.MpsatUtils;
import org.workcraft.plugins.pcomp.utils.PcompUtils;
import org.workcraft.plugins.stg.StgModel;
import org.workcraft.tasks.ProgressMonitor;
import org.workcraft.tasks.Result;
import org.workcraft.tasks.TaskManager;
import org.workcraft.utils.WorkspaceUtils;
import org.workcraft.workspace.Workspace;
import org.workcraft.workspace.WorkspaceEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NwayConformationVerificationCommand extends org.workcraft.commands.AbstractVerificationCommand
        implements ScriptableDataCommand<Boolean, List<WorkspaceEntry>> {

    @Override
    public String getDisplayName() {
        return "N-way conformation [MPSat]...";
    }

    @Override
    public boolean isApplicableTo(WorkspaceEntry we) {
        return (we == null) || WorkspaceUtils.isApplicable(we, StgModel.class);
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public Position getPosition() {
        return Position.BOTTOM_MIDDLE;
    }

    @Override
    public void run(WorkspaceEntry we) {
        List<WorkspaceEntry> wes = new ArrayList<>();
        Framework framework = Framework.getInstance();
        Workspace workspace = framework.getWorkspace();

        MainWindow mainWindow = framework.getMainWindow();
        NwayConformationDialog dialog = new NwayConformationDialog(mainWindow);
        if (dialog.reveal()) {
            Set<Path<String>> paths = dialog.getSourcePaths();
            if (paths != null) {
                for (Path<String> path : paths) {
                    wes.add(workspace.getWork(path));
                }
            }

            NwayConformationChainResultHandlingMonitor monitor = new NwayConformationChainResultHandlingMonitor(wes);
            run(we, wes, monitor);
        }
    }

    @Override
    public void run(WorkspaceEntry we, List<WorkspaceEntry> wes, ProgressMonitor monitor) {
        if (wes.size() < 2) {
            monitor.isFinished(Result.exception("At least two STGs are required for N-way conformation."));
            return;
        }

        NwayConformationTask task = new NwayConformationTask(wes);
        TaskManager manager = Framework.getInstance().getTaskManager();
        String titles = wes.stream().map(w -> w.getTitle()).collect(Collectors.joining(", "));
        String description = MpsatUtils.getToolchainDescription(titles);
        manager.queue(task, description, monitor);
    }

    @Override
    public List<WorkspaceEntry> deserialiseData(String data) {
        return PcompUtils.deserealiseData(data);
    }

    @Override
    public Boolean execute(WorkspaceEntry we, List<WorkspaceEntry> wes) {
        NwayConformationChainResultHandlingMonitor monitor = new NwayConformationChainResultHandlingMonitor(wes);
        run(we, wes, monitor);
        return monitor.waitForHandledResult();
    }

}
