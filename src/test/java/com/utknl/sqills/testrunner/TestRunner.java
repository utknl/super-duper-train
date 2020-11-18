package com.utknl.sqills.testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/java/com/utknl/sqills/features"},
        glue = {"com.utknl.sqills.stepdefinitions"},
        plugin = {"pretty", "html:src/test/resources/report.html"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        stepNotifications = true
)
public class TestRunner {
    //hello
}
