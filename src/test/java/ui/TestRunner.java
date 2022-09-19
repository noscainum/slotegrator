package ui;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import java.awt.*;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/scenarios/slotegrator.feature"},
        glue = {"ui.stepsDefinition"}
)

public class TestRunner {
}
