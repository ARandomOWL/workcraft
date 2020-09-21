package org.workcraft.plugins.circuit.commands;

import org.workcraft.exceptions.InvalidConnectionException;
import org.workcraft.plugins.circuit.VisualCircuit;
import org.workcraft.plugins.circuit.utils.ScanUtils;
import org.workcraft.utils.WorkspaceUtils;
import org.workcraft.workspace.WorkspaceEntry;

public class ScanInsertionCommand extends AbstractInsertionCommand {

    @Override
    public String getDisplayName() {
        return "Insert scan for path breaker components";
    }

    @Override
    public void insert(WorkspaceEntry we) {
        we.saveMemento();
        VisualCircuit circuit = WorkspaceUtils.getAs(we, VisualCircuit.class);
        try {
            ScanUtils.insertScan(circuit);
        } catch (InvalidConnectionException e) {
            throw new RuntimeException(e);
        }
    }

}
