package org.workcraft.plugins.circuit;

import org.junit.jupiter.api.Assertions;
import org.workcraft.Framework;
import org.workcraft.commands.AbstractSynthesisCommand;
import org.workcraft.dom.math.PageNode;
import org.workcraft.dom.references.Identifier;
import org.workcraft.exceptions.DeserialisationException;
import org.workcraft.plugins.stg.Mutex;
import org.workcraft.plugins.stg.Signal;
import org.workcraft.plugins.stg.Stg;
import org.workcraft.plugins.stg.utils.MutexUtils;
import org.workcraft.utils.Hierarchy;
import org.workcraft.utils.WorkspaceUtils;
import org.workcraft.workspace.WorkspaceEntry;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class TestUtils {

    public static <C extends AbstractSynthesisCommand> void checkSynthesisCommand(Class<C> cls, String workName,
            int expectedComponentCount)
            throws DeserialisationException, InstantiationException, IllegalAccessException {
        checkSynthesisCommand(cls, workName, expectedComponentCount, expectedComponentCount);
    }

    public static <C extends AbstractSynthesisCommand> void checkSynthesisCommand(Class<C> cls, String workName,
            int minComponentCount, int maxComponentCount)
            throws DeserialisationException, InstantiationException, IllegalAccessException {

        final Framework framework = Framework.getInstance();
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL srcUrl = classLoader.getResource(workName);

        WorkspaceEntry srcWe = framework.loadWork(srcUrl.getFile());
        Stg srcStg = WorkspaceUtils.getAs(srcWe, Stg.class);
        Set<String> srcInputs = srcStg.getSignalReferences(Signal.Type.INPUT);
        Set<String> srcOutputs = srcStg.getSignalReferences(Signal.Type.OUTPUT);
        Set<String> srcMutexes = MutexUtils.getMutexPlaceReferences(srcStg);
        Set<String> srcPageRefs = new HashSet<>();
        for (PageNode page: Hierarchy.getChildrenOfType(srcStg.getRoot(), PageNode.class)) {
            boolean hasInputs = !srcStg.getSignalNames(Signal.Type.INPUT, page).isEmpty();
            boolean hasOutputs = !srcStg.getSignalNames(Signal.Type.OUTPUT, page).isEmpty();
            if (hasInputs || hasOutputs) {
                String srcPageRef = srcStg.getNodeReference(page);
                srcPageRefs.add(srcPageRef);
            }
        }

        C command = cls.newInstance();
        WorkspaceEntry dstWe = command.execute(srcWe);
        Circuit dstCircuit = WorkspaceUtils.getAs(dstWe, Circuit.class);
        Set<String> dstInputs = new HashSet<>();
        Set<String> dstOutputs = new HashSet<>();
        // Process primary ports
        for (Contact port: dstCircuit.getPorts()) {
            String dstSignal = dstCircuit.getNodeReference(port);
            if (port.isInput()) {
                dstInputs.add(dstSignal);
            }
            if (port.isOutput()) {
                dstOutputs.add(dstSignal);
            }
        }
        // Process environment pins
        Set<String> dstPageRefs = new HashSet<>();
        for (PageNode page: Hierarchy.getChildrenOfType(dstCircuit.getRoot(), PageNode.class)) {
            for (Contact port: dstCircuit.getPorts()) {
                if (port.getParent() == page) {
                    dstPageRefs.add(dstCircuit.getNodeReference(page));
                    break;
                }
            }
        }
        Set<String> dstMutexes = getMutexComponentReferences(dstCircuit);
        int dstComponentCount = dstCircuit.getFunctionComponents().size();

        Assertions.assertEquals(srcInputs, dstInputs);
        Assertions.assertEquals(srcOutputs, dstOutputs);
        Assertions.assertEquals(srcMutexes, dstMutexes);
        Assertions.assertTrue(minComponentCount <= dstComponentCount);
        Assertions.assertTrue(maxComponentCount >= dstComponentCount);
        Assertions.assertEquals(srcPageRefs, dstPageRefs);
    }

    private static Set<String> getMutexComponentReferences(Circuit circuit) {
        HashSet<String> result = new HashSet<>();
        Mutex mutex = CircuitSettings.parseMutexData();
        for (FunctionComponent component: circuit.getFunctionComponents()) {
            if (mutex.name.equals(component.getModule())) {
                String ref = circuit.getNodeReference(component);
                result.add(Identifier.truncateNamespaceSeparator(ref));
            }
        }
        return result;
    }

    public static void collectNodes(WorkspaceEntry we, Set<String> inputs, Set<String> outputs, Set<String> gates) {
        Circuit circuit = WorkspaceUtils.getAs(we, Circuit.class);
        for (Contact port: circuit.getPorts()) {
            if (port.isInput()) {
                inputs.add(circuit.getNodeReference(port));
            }
            if (port.isOutput()) {
                outputs.add(circuit.getNodeReference(port));
            }
        }
        for (FunctionComponent component: circuit.getFunctionComponents()) {
            String ref = circuit.getNodeReference(component);
            gates.add(component.getModule() + " " + Identifier.truncateNamespaceSeparator(ref));
        }
    }

}
