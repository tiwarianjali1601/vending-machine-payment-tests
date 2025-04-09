package com.gym.vending.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",           // path to your feature files
        glue = "com.gym.vending.stepdefs",                  // package where your step defs are
        plugin = {
                "pretty",                                        // readable console logs
                "html:target/cucumber-report.html",             // generates HTML report
                "json:target/cucumber-report.json"              // generates JSON report
        },
        monochrome = true,                                   // removes unreadable characters from console output
        publish = false                                      // set true if you want to publish results online
)
public class TestRunner {}
