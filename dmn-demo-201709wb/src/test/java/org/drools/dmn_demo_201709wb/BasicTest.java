package org.drools.dmn_demo_201709wb;

import org.junit.Test;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.context.EmptyContext;

public class BasicTest {

    @Test
    public void test_basic() {
        RuntimeEnvironment environment = RuntimeEnvironmentBuilder.Factory.get()
                                                                          .newDefaultBuilder()
                                                                          .addAsset(ResourceFactory.newClassPathResource("org/drools/dmn_demo_201709wb/simple process routing with DMN.bpmn2"), ResourceType.BPMN2)
                                                                          .addAsset(ResourceFactory.newClassPathResource("org/drools/dmn_demo_201709wb/DMN presentation simplified.dmn"), ResourceType.DMN)
                                                                          .persistence(false)
                                                                          .get();

        RuntimeManager manager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(environment);

        RuntimeEngine runtime = manager.getRuntimeEngine(EmptyContext.get());

        KieSession kieSession = runtime.getKieSession();

        kieSession.startProcess("dmn-demo-201709wb.simpleprocessroutingwithDMN", null);

    }
}
