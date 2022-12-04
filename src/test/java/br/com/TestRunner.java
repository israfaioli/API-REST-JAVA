package br.com;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/cucumber-reports/Report.json"},
        features = "src/test/resources/features",
        glue = "br.com.steps_definitions",
        tags = {""}
)

public class TestRunner {
}
