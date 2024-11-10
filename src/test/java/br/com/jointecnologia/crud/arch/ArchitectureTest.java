package br.com.jointecnologia.crud.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

/**
 * Architecture tests to ensure that naming conventions and package structures are followed
 * in the CRUD project. This test uses the ArchUnit library to verify organizational rules
 * for classes, such as naming conventions, suffixes, and package locations.
 * The goal is to ensure that system components (Controllers, Services, Repositories, etc.)
 * are organized according to good practices and defined standards.
 */
@AnalyzeClasses(
        packages = "br.com.jointecnologia.crud",
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
class ArchitectureTest {

    @ArchTest
    static final ArchRule testControllerNameConvention =
            classes()
                    .that()
                    .resideInAPackage("br.com.jointecnologia.crud.controller..")
                    .and()
                    .areAnnotatedWith(RestController.class)
                    .should()
                    .haveSimpleNameEndingWith("Controller")
                    .because("Every class annotated with '@RestController' should end with 'Controller'");

    @ArchTest
    static final ArchRule testControllerPackageConvention =
            classes()
                    .that()
                    .haveSimpleNameEndingWith("Controller")
                    .should()
                    .resideInAPackage("br.com.jointecnologia.crud.controller..")
                    .because("Every class ending with 'Controller' should reside in the 'controller' package");

    @ArchTest
    static final ArchRule testServiceNameConvention =
            classes()
                    .that()
                    .haveSimpleNameEndingWith("Service")
                    .should()
                    .resideInAPackage("br.com.jointecnologia.crud.service..")
                    .because("Every service class should end with 'Service' and reside in the 'service' package");

    @ArchTest
    static final ArchRule testRepositoryNameConvention =
            classes()
                    .that()
                    .haveSimpleNameEndingWith("Repository")
                    .should()
                    .resideInAPackage("br.com.jointecnologia.crud.repository..")
                    .because("Every repository class should end with 'Repository' and reside in the 'repository' package");

}
