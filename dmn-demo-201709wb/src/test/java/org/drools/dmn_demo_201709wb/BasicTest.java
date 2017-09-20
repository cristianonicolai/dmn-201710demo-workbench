package org.drools.dmn_demo_201709wb;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.runtime.manager.context.EmptyContext;

public class BasicTest extends JbpmJUnitBaseTestCase {

    @Test
    public void test_basic() {
        Map<String, ResourceType> resources = new HashMap<>();
        resources.put("org/drools/dmn_demo_201709wb/simple process routing with DMN.bpmn2", ResourceType.BPMN2);
        resources.put("org/drools/dmn_demo_201709wb/DMN presentation simplified.dmn", ResourceType.DMN);
        RuntimeManager manager = createRuntimeManager(resources, "BasicTest");
        RuntimeEngine engine = getRuntimeEngine(EmptyContext.get());
        KieSession ksession = engine.getKieSession();

        Map<String, Object> application = new HashMap<>();
        application.put("Name", "Matteo");
        application.put("Age", 35);
        application.put("Country", "IT");
        application.put("Employed", true);
        Map<String, Object> processVariables = new HashMap<>();
        processVariables.put("Application", application);
        ProcessInstance processInstance = ksession.startProcess("dmn-demo-201709wb.simpleprocessroutingwithDMN", processVariables);

    }
}
