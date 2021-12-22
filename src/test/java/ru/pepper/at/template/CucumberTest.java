package ru.pepper.at.template;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"ru.pepper.at.template.stepdefs"},
        features = "src/test/resources/features",
        plugin = {"io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm", "pretty"}
)
public class CucumberTest {

    private static final Hooks hooks = new Hooks();

    @BeforeClass
    public static void setup() {
        hooks.setup();
    }
}
