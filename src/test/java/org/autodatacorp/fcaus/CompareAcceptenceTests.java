package org.autodatacorp.fcaus;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/cucumber/reports/style.json"},
        features = {"classpath:features"},
        glue = {"classpath:org.autodatacorp.fcaus.steps"}
)
public class CompareAcceptenceTests {

}

