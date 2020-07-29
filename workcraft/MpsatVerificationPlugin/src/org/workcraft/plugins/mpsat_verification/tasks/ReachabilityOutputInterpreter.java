package org.workcraft.plugins.mpsat_verification.tasks;

import org.workcraft.Framework;
import org.workcraft.gui.MainWindow;
import org.workcraft.gui.dialogs.ReachabilityDialog;
import org.workcraft.plugins.mpsat_verification.utils.OutcomeUtils;
import org.workcraft.plugins.pcomp.ComponentData;
import org.workcraft.plugins.pcomp.CompositionData;
import org.workcraft.plugins.pcomp.tasks.PcompOutput;
import org.workcraft.plugins.stg.StgModel;
import org.workcraft.plugins.stg.utils.StgUtils;
import org.workcraft.tasks.AbstractOutputInterpreter;
import org.workcraft.tasks.ExportOutput;
import org.workcraft.traces.Solution;
import org.workcraft.traces.Trace;
import org.workcraft.utils.TraceUtils;
import org.workcraft.workspace.WorkspaceEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ReachabilityOutputInterpreter extends AbstractOutputInterpreter<MpsatOutput, Boolean> {

    private final ExportOutput exportOutput;
    private final PcompOutput pcompOutput;

    private CompositionData compositionData = null;

    ReachabilityOutputInterpreter(WorkspaceEntry we, ExportOutput exportOutput,
            PcompOutput pcompOutput, MpsatOutput mpsatOutput, boolean interactive) {

        super(we, mpsatOutput, interactive);
        this.exportOutput = exportOutput;
        this.pcompOutput = pcompOutput;
    }

    public ExportOutput getExportOutput() {
        return exportOutput;
    }

    public PcompOutput getPcompOutput() {
        return pcompOutput;
    }

    public void reportSolutions(List<Solution> solutions) {
    }

    public List<Solution> processSolutions(WorkspaceEntry we, List<Solution> solutions) {
        List<Solution> result = new LinkedList<>();
        ComponentData data = getCompositionData(we);
        Map<String, String> substitutions = getSubstitutions(we);
        for (Solution solution: solutions) {
            Trace mainTrace = getProjectedTrace(solution.getMainTrace(), data, substitutions);
            Trace branchTrace = getProjectedTrace(solution.getBranchTrace(), data, substitutions);
            String comment = solution.getComment();
            Solution processedSolution = new Solution(mainTrace, branchTrace, comment);
            result.add(processedSolution);
        }
        return result;
    }

    public Trace getProjectedTrace(Trace trace, ComponentData data, Map<String, String> substitutions) {
        if ((trace == null) || trace.isEmpty() || (data == null)) {
            return trace;
        }
        Trace result = new Trace();
        for (String ref: trace) {
            String srcRef = data.getSrcTransition(ref);
            if (srcRef != null) {
                if (substitutions.containsKey(srcRef)) {
                    srcRef = substitutions.get(srcRef);
                }
                result.add(srcRef);
            }
        }
        return result;
    }

    public Map<String, String> getSubstitutions(WorkspaceEntry we) {
        if (getExportOutput() instanceof SubExportOutput) {
            SubExportOutput exportOutput = (SubExportOutput) getExportOutput();
            return exportOutput.getSubstitutions();
        }
        return new HashMap<>();
    }

    public ComponentData getCompositionData(WorkspaceEntry we) {
        return getCompositionData(0);
    }

    public ComponentData getCompositionData(int index) {
        if (compositionData == null) {
            if (getPcompOutput() != null) {
                File detailFile = getPcompOutput().getDetailFile();
                try {
                    compositionData = new CompositionData(detailFile);
                } catch (FileNotFoundException e) {
                }
            }
        }
        return compositionData == null ? null : compositionData.getComponentData(index);
    }

    public StgModel getSrcStg(WorkspaceEntry we) {
        // If the property is verified on a composition STG, then use the
        // corresponding component STG; otherwise use input STG of MPSat task.
        StgModel stg = null;
        ComponentData data = getCompositionData(we);
        if (data != null) {
            File file = new File(data.getFileName());
            if ((file != null) && file.exists()) {
                stg = StgUtils.importStg(file);
            }
        } else {
            stg = getOutput().getInputStg();
        }
        return stg;
    }

    public String getMessage(boolean propertyHolds) {
        String propertyName = getOutput().getVerificationParameters().getDescription();
        if ((propertyName == null) || propertyName.isEmpty()) {
            propertyName = "Property";
        }
        return propertyName + (propertyHolds ? " holds." :  " is violated.");
    }

    public String extendMessage(String message) {
        boolean inversePredicate = getOutput().getVerificationParameters().getInversePredicate();
        String traceCharacteristic = inversePredicate ? "problematic" : "sought";
        String traceInfo = "Trace(s) leading to the " + traceCharacteristic + " state(s):";
        return "<html>" + message + "<br><br>" + traceInfo + "</html>";
    }

    @Override
    public Boolean interpret() {
        if (getOutput() == null) {
            return null;
        }
        List<Solution> solutions = getOutput().getSolutions();
        boolean predicateSatisfiable = TraceUtils.hasTraces(solutions);
        boolean inversePredicate = getOutput().getVerificationParameters().getInversePredicate();
        boolean propertyHolds = predicateSatisfiable != inversePredicate;
        String message = getMessage(propertyHolds);
        if (!predicateSatisfiable) {
            OutcomeUtils.showOutcome(propertyHolds, message, isInteractive());
        } else {
            OutcomeUtils.logOutcome(propertyHolds, message);
            reportSolutions(solutions);
            List<Solution> processedSolutions = processSolutions(getWorkspaceEntry(), solutions);
            if (isInteractive()) {
                message = extendMessage(message);
                MainWindow mainWindow = Framework.getInstance().getMainWindow();
                ReachabilityDialog solutionsDialog = new ReachabilityDialog(
                        mainWindow, getWorkspaceEntry(), OutcomeUtils.TITLE, message, processedSolutions);

                solutionsDialog.reveal();
            }
        }
        return propertyHolds;
    }

}
