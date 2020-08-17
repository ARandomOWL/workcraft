package org.workcraft.plugins.mpsat_verification.tasks;

import org.workcraft.plugins.mpsat_verification.utils.EnablednessUtils;
import org.workcraft.plugins.pcomp.ComponentData;
import org.workcraft.plugins.pcomp.CompositionData;
import org.workcraft.plugins.pcomp.tasks.PcompOutput;
import org.workcraft.plugins.petri.Place;
import org.workcraft.plugins.petri.utils.PetriUtils;
import org.workcraft.plugins.stg.Signal;
import org.workcraft.plugins.stg.SignalTransition;
import org.workcraft.plugins.stg.StgModel;
import org.workcraft.tasks.ExportOutput;
import org.workcraft.traces.Solution;
import org.workcraft.traces.Trace;
import org.workcraft.utils.LogUtils;
import org.workcraft.workspace.WorkspaceEntry;

import java.util.*;

class OutputDeterminacyOutputInterpreter extends AbstractCompositionOutputInterpreter {

    OutputDeterminacyOutputInterpreter(WorkspaceEntry we, ExportOutput exportOutput,
            PcompOutput pcompOutput, MpsatOutput mpsatOutput, boolean interactive) {

        super(we, exportOutput, pcompOutput, mpsatOutput, interactive);
    }

    @Override
    public List<Solution> processSolutions(List<Solution> solutions) {
        List<Solution> result = new LinkedList<>();

        StgModel compStg = getOutput().getInputStg();
        StgModel stg = getStg();
        CompositionData compositionData = getCompositionData();
        ComponentData devData = compositionData.getComponentData(0);
        ComponentData envData = compositionData.getComponentData(1);
        Map<String, String> substitutions = getSubstitutions();

        for (Solution solution: solutions) {
            Trace compTrace = solution.getMainTrace();
            LogUtils.logMessage("Violation trace of the auto-composition: " + compTrace.toString());

            Trace devTrace = getProjectedTrace(compTrace, devData, substitutions);
            Trace envTrace = getProjectedTrace(compTrace, envData, substitutions);
            LogUtils.logMessage("Projected pair of traces:\n    " + devTrace.toString() + "\n    " + envTrace.toString());

            Enabledness compEnabledness = EnablednessUtils.getEnablednessAfterTrace(compStg, compTrace);
            Solution projectedSolution = new Solution(devTrace, envTrace);
            Solution processedSolution = processSolution(stg, projectedSolution, compEnabledness);
            if (processedSolution != null) {
                result.add(processedSolution);
            }
        }
        return result;
    }

    private Solution processSolution(StgModel stg, Solution solution, Enabledness compEnabledness) {
        // Execute trace to potentially interesting state
        HashMap<Place, Integer> marking = PetriUtils.getMarking(stg);
        Trace trace = solution.getMainTrace();
        if (!PetriUtils.fireTrace(stg, trace)) {
            PetriUtils.setMarking(stg, marking);
            throw new RuntimeException("Cannot execute projected trace: " + trace.toString());
        }
        // Check if any output can be fired that is not enabled in the composition
        HashSet<String> suspiciousSignals = EnablednessUtils.getEnabledSignals(stg, Signal.Type.OUTPUT);
        suspiciousSignals.removeAll(compEnabledness.getEnabledSet());
        suspiciousSignals.removeAll(compEnabledness.getDisabledSet());
        if (suspiciousSignals.size() == 1) {
            compEnabledness.disable(suspiciousSignals);
        }

        SignalTransition problematicTransition = null;
        for (SignalTransition transition: stg.getSignalTransitions(Signal.Type.OUTPUT)) {
            String signalRef = stg.getSignalReference(transition);
            if (stg.isEnabled(transition) && compEnabledness.isDisabled(signalRef)) {
                problematicTransition = transition;
                break;
            }
        }
        String comment = null;
        if (problematicTransition != null) {
            String ref = stg.getSignalReference(problematicTransition);
            LogUtils.logWarning("Output '" + ref + "' is non-deterministically enabled");
            comment = "Non-deterministic enabling of output '" + ref + "'";
        } else if (!suspiciousSignals.isEmpty()) {
            String refs = String.join(", ", suspiciousSignals);
            LogUtils.logWarning("One of these outputs is non-deterministically enabled (via internal signals or dummies):\n" + refs);
            comment = "Non-deterministic enabling of one of the outputs: " + refs;
        }
        PetriUtils.setMarking(stg, marking);
        return new Solution(solution.getMainTrace(), solution.getBranchTrace(), comment);
    }

}
