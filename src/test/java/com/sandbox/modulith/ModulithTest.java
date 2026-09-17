package com.sandbox.modulith;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import com.sandbox.SandboxApplication;

@SpringBootTest
public class ModulithTest {

    ApplicationModules modules = ApplicationModules.of(SandboxApplication.class);

    @Test
    void verifiesModularStructure() {
        modules.verify();
    }

    @Test
    void createModuleDocumentation() {
        new Documenter(modules)
                .writeDocumentation()
                .writeIndividualModulesAsPlantUml();
    }

    @Test
    void createApplicationModuleModel() {
        modules.forEach(System.out::println);
    }
}
