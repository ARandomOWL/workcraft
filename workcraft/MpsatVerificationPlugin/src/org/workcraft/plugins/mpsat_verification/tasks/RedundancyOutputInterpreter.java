package org.workcraft.plugins.mpsat_verification.tasks;

import org.workcraft.plugins.pcomp.tasks.PcompOutput;
import org.workcraft.tasks.ExportOutput;
import org.workcraft.workspace.WorkspaceEntry;

class RedundancyOutputInterpreter extends ReachabilityOutputInterpreter {

    RedundancyOutputInterpreter(WorkspaceEntry we, ExportOutput exportOutput,
            PcompOutput pcompOutput, MpsatOutput mpsatOutput, boolean interactive) {

        super(we, exportOutput, pcompOutput, mpsatOutput, interactive);
    }

    @Override
    public String getMessage(boolean propertyHolds) {
        return "The selected places are " + (propertyHolds ? "redundant" : "essential");
    }

    @Override
    public String extendMessage(String message) {
        String traceInfo = "&#160;Trace(s) leading to the witness state(s):<br><br>";
        return "<html><br>&#160;" + message + "<br><br>" + traceInfo + "</html>";
    }

}
