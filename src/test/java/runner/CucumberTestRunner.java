package runner;

import org.junit.platform.suite.api.*;

/**
 * Cucumber Test Runner using JUnit Platform Suite.
 * Configures feature file path and step definitions package.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue", value = "stepDefinitions")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty, html:target/cucumber-report.html, json:target/cucumber-report.json")
@ConfigurationParameter(key = "cucumber.publish.quiet", value = "true")
public class CucumberTestRunner {
    // JUnit Platform Suite entry point for Cucumber
    // No implementation required - annotations configure execution
}
