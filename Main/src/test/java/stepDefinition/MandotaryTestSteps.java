package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MandotaryTestSteps {

    protected Logger logger = LogManager.getLogger(MandotaryTestSteps.class);

        @Given("I am running scenario {int} from feature {int}")
        public void i_am_running_scenario_from_feature(int scenarioNumber, int featureNumber) {
            System.out.println("Running Scenario " + scenarioNumber + " from Feature " + featureNumber);
        }

        @When("I execute scenario {int}")
        public void i_execute_scenario(int scenarioNumber) throws InterruptedException {
            System.out.println("Executing Scenario " + scenarioNumber);
            logger.info("step executed" + scenarioNumber);
            Thread.sleep(8000);
        }

        @Then("I verify scenario {int} is complete")
        public void i_verify_scenario_is_complete(int scenarioNumber) {
            assertTrue(true, "Scenario " + scenarioNumber + " executed successfully.");
        }
    }


