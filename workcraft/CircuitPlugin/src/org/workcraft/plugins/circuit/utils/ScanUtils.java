package org.workcraft.plugins.circuit.utils;

import org.workcraft.dom.Node;
import org.workcraft.dom.hierarchy.NamespaceHelper;
import org.workcraft.dom.references.Identifier;
import org.workcraft.dom.visual.VisualNode;
import org.workcraft.dom.visual.connections.VisualConnection;
import org.workcraft.exceptions.InvalidConnectionException;
import org.workcraft.formula.Zero;
import org.workcraft.plugins.circuit.*;
import org.workcraft.plugins.circuit.genlib.UnaryGateInterface;
import org.workcraft.plugins.circuit.renderers.ComponentRenderingResult;
import org.workcraft.utils.DialogUtils;

import java.util.*;

public class ScanUtils {

    public static Set<VisualFunctionComponent> insertTestableGates(VisualCircuit circuit) {
        Set<VisualFunctionComponent> components = new HashSet<>();
        for (VisualFunctionComponent component : circuit.getVisualFunctionComponents()) {
            for (VisualContact contact : component.getVisualOutputs()) {
                if (contact.getReferencedComponent().getPathBreaker()) {
                    contact.getReferencedComponent().setPathBreaker(false);
                    VisualFunctionComponent testableGate = insertTestableGate(circuit, contact);
                    if (testableGate != null) {
                        components.add(testableGate);
                    }
                }
            }
        }
        return components;
    }

    private static VisualFunctionComponent insertTestableGate(VisualCircuit circuit, VisualContact contact) {
        VisualFunctionComponent result = getAdjacentBufferOrInverter(circuit, contact);
        if (result == null) {
            SpaceUtils.makeSpaceAfterContact(circuit, contact, 3.0);
            result = GateUtils.createBufferGate(circuit);
            GateUtils.insertGateAfter(circuit, result, contact);
            GateUtils.propagateInitialState(circuit, result);
            result.getGateOutput().getReferencedComponent().setPathBreaker(true);
        }
        UnaryGateInterface testableGateInterface = result.isInverter() ? CircuitSettings.parseTinvData() : CircuitSettings.parseTbufData();
        result.getReferencedComponent().setModule(testableGateInterface.name);

        VisualFunctionContact inputContact = result.getFirstVisualInput();
        VisualFunctionContact outputContact = result.getFirstVisualOutput();
        // Temporary rename gate output, so there is no name clash on renaming gate input
        circuit.setMathName(outputContact, Identifier.makeInternal(testableGateInterface.output));
        circuit.setMathName(inputContact, testableGateInterface.input);
        circuit.setMathName(outputContact, testableGateInterface.output);
        result.getGateOutput().getReferencedComponent().setPathBreaker(true);
        return result;
    }

    private static VisualFunctionComponent getAdjacentBufferOrInverter(VisualCircuit circuit, VisualContact contact) {
        Node parent = contact.getParent();
        if (parent instanceof VisualFunctionComponent) {
            VisualFunctionComponent component = (VisualFunctionComponent) parent;
            if (component.isBuffer() || component.isInverter()) {
                return component;
            }
        }
        if (contact.isOutput()) {
            Collection<VisualContact> drivenContacts = CircuitUtils.findDriven(circuit, contact, false);
            if (drivenContacts.size() == 1) {
                VisualContact drivenContact = drivenContacts.iterator().next();
                return getAdjacentBufferOrInverter(circuit, drivenContact);
            }
        }
        return null;
    }

    public static void insertScan(VisualCircuit circuit) throws InvalidConnectionException {
        List<VisualFunctionComponent> pathbreakerComponents = new ArrayList<>();
        for (VisualFunctionComponent component : circuit.getVisualFunctionComponents()) {
            if (hasPathBreakerOutput(component.getReferencedComponent())) {
                pathbreakerComponents.add(component);
            }
        }
        // Arrange SCAN components from right-to-left (and in the same column from bottom-to-top)
        Collections.sort(pathbreakerComponents, (o1, o2) -> {
            int result = Double.compare(o2.getRootSpaceX(), o1.getRootSpaceX());
            return result == 0 ? Double.compare(o2.getRootSpaceY(), o1.getRootSpaceY()) : result;
        });

        if (!pathbreakerComponents.isEmpty()) {
            String ckName = CircuitSettings.parseScanckPortPin().getFirst();
            VisualFunctionContact ckPort = CircuitUtils.getOrCreatePort(
                    circuit, ckName, Contact.IOType.INPUT, VisualContact.Direction.WEST);

            String enName = CircuitSettings.parseScanenPortPin().getFirst();
            VisualFunctionContact enPort = CircuitUtils.getOrCreatePort(
                    circuit, enName, Contact.IOType.INPUT, VisualContact.Direction.WEST);

            String tmName = CircuitSettings.parseScantmPortPin().getFirst();
            VisualFunctionContact tmPort = CircuitUtils.getOrCreatePort(
                    circuit, tmName, Contact.IOType.INPUT, VisualContact.Direction.WEST);

            String inName = CircuitSettings.parseScaninPortPin().getFirst();
            VisualFunctionContact inPort = CircuitUtils.getOrCreatePort(
                    circuit, inName, Contact.IOType.INPUT, VisualContact.Direction.EAST);

            insertScanChain(circuit, pathbreakerComponents, ckPort, enPort, tmPort, inPort);
        }
    }

    private static void insertScanChain(VisualCircuit circuit, List<VisualFunctionComponent> pathbreakerComponents,
            VisualFunctionContact ckPort, VisualFunctionContact enPort, VisualFunctionContact tmPort,
            VisualFunctionContact inPort) throws InvalidConnectionException {

        // Is scanin port is connected then prepend existing scan chain, otherwise create a new scan chain
        Set<VisualNode> inPostset = new HashSet<>(circuit.getPostset(inPort));
        if (!inPostset.isEmpty()) {
            for (VisualNode succNode : inPostset) {
                VisualConnection connection = circuit.getConnection(inPort, succNode);
                if (connection != null) {
                    circuit.remove(connection);
                }
            }
            VisualFunctionContact outPin = createScanChain(circuit, pathbreakerComponents, ckPort, enPort, tmPort, inPort);
            for (VisualNode succNode : inPostset) {
                circuit.connect(outPin, succNode);
            }
        } else {
            VisualFunctionContact outPin = createScanChain(circuit, pathbreakerComponents, ckPort, enPort, tmPort, inPort);
            SpaceUtils.positionPort(circuit, ckPort, false);
            ckPort.setSetFunction(Zero.getInstance());

            SpaceUtils.positionPort(circuit, enPort, false);
            enPort.setSetFunction(Zero.getInstance());

            SpaceUtils.positionPort(circuit, tmPort, false);
            tmPort.setSetFunction(Zero.getInstance());

            SpaceUtils.positionPort(circuit, inPort, true);
            inPort.setSetFunction(Zero.getInstance());

            // If the last pin of SCAN chain is connected to an output port, use that port as scanout.
            // (This is necessary because a fork to several output ports is not allowed.)

            String outName = CircuitSettings.parseScanoutPortPin().getFirst();

            Contact existingPort = CircuitUtils.getDrivenOutputPort(circuit.getMathModel(), outPin.getReferencedComponent());
            if (existingPort == null) {
                VisualFunctionContact outPort = CircuitUtils.getOrCreatePort(
                        circuit, outName, Contact.IOType.OUTPUT, VisualContact.Direction.EAST);

                circuit.connect(outPin, outPort);
                SpaceUtils.positionPort(circuit, outPort, true);
                outPort.setSetFunction(Zero.getInstance());
            } else {
                String ref = circuit.getMathReference(existingPort);
                if (!outName.equals(ref)) {
                    String msg = "Existing port '" + ref + "' is used as SCAN chain output instead of '" + outName + "'."
                            + "\nThis is necessary because a fork on several output ports is not allowed.";

                    DialogUtils.showWarning(msg, "Insertion of SCAN");
                }
            }
        }
    }

    private static VisualFunctionContact createScanChain(VisualCircuit circuit, List<VisualFunctionComponent> components,
            VisualFunctionContact ckPort, VisualFunctionContact enPort, VisualFunctionContact tmPort, VisualFunctionContact inPort) {

        VisualFunctionContact outPin = inPort;
        for (VisualFunctionComponent component : components) {
            outPin = insertScanChain(circuit, component, ckPort, enPort, tmPort, outPin);
        }
        return outPin;
    }

    private static VisualFunctionContact insertScanChain(VisualCircuit circuit, VisualFunctionComponent component,
            VisualFunctionContact ckPort, VisualFunctionContact enPort, VisualFunctionContact tmPort,
            VisualFunctionContact dataContact) {

        component.setRenderType(ComponentRenderingResult.RenderType.BOX);
        String moduleName = component.getReferencedComponent().getModule();
        String scanSuffix = CircuitSettings.getScanSuffix();
        if (!moduleName.isEmpty() && !moduleName.endsWith(scanSuffix)) {
            component.getReferencedComponent().setModule(moduleName + scanSuffix);
        }

        VisualFunctionContact ckPin = CircuitUtils.getOrCreateContact(circuit, component,
                CircuitSettings.parseScanckPortPin().getSecond(),
                Contact.IOType.INPUT, VisualContact.Direction.WEST);

        VisualFunctionContact enPin = CircuitUtils.getOrCreateContact(circuit, component,
                CircuitSettings.parseScanenPortPin().getSecond(),
                Contact.IOType.INPUT, VisualContact.Direction.WEST);

        VisualFunctionContact tmPin = CircuitUtils.getOrCreateContact(circuit, component,
                CircuitSettings.parseScantmPortPin().getSecond(),
                Contact.IOType.INPUT, VisualContact.Direction.WEST);

        String inName = CircuitSettings.parseScaninPortPin().getSecond();
        String ref = NamespaceHelper.getReference(circuit.getMathReference(component), inName);
        boolean noScaninPin = circuit.getVisualComponentByMathReference(ref, VisualFunctionContact.class) == null;
        VisualFunctionContact inPin = CircuitUtils.getOrCreateContact(circuit, component,
                inName, Contact.IOType.INPUT, VisualContact.Direction.EAST);

        if (noScaninPin) {
            inPin.setY(inPin.getY() + 1.5);
        }

        VisualFunctionContact outPin = null;
        if (component.getVisualOutputs().size() == 1) {
            outPin = component.getFirstVisualOutput();
        } else {
            outPin = CircuitUtils.getOrCreateContact(circuit, component,
                    CircuitSettings.parseScanoutPortPin().getSecond(),
                    Contact.IOType.OUTPUT, VisualContact.Direction.EAST);
        }

        try {
            if (circuit.getConnections(ckPin).isEmpty()) {
                circuit.connect(ckPort, ckPin);
            }
            if (circuit.getConnections(enPin).isEmpty()) {
                circuit.connect(enPort, enPin);
            }
            if (circuit.getConnections(tmPin).isEmpty()) {
                circuit.connect(tmPort, tmPin);
            }
            if (circuit.getConnections(inPin).isEmpty()) {
                circuit.connect(dataContact, inPin);
            }
        } catch (InvalidConnectionException e) {
            throw new RuntimeException(e);
        }
        return outPin;
    }

    public static boolean hasPathBreakerOutput(CircuitComponent component) {
        for (Contact outputContact : component.getOutputs()) {
            if (outputContact.getPathBreaker()) {
                return true;
            }
        }
        return false;
    }

}
