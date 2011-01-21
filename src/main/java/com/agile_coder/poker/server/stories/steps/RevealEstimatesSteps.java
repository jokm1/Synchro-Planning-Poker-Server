/*
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2011 the original author or authors.
 */
package com.agile_coder.poker.server.stories.steps;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class RevealEstimatesSteps extends BaseSteps {

    @Given("all members have voted")
    public void setVotes() throws IOException {
        startServer();
        submitEstimate("Test1", "3");
        submitEstimate("Test2", "5");
        submitEstimate("Test3", "3");
    }

    @When("I request to reveal the estimates")
    public void revealEstimates() throws IOException {
        reveal();
    }

    @Then("requests for the estimate list return all of the estimates")
    public void validateResponse() throws IOException {
        final String expected = "{Test1: 'THREE', Test2: 'FIVE', Test3: 'THREE'}";
        String status;
        try {
            status = getStatus();
        } finally {
            stopServer();
        }
        JSONObject expect = JSONObject.fromObject(expected);
        JSONObject result = JSONObject.fromObject(status);
        assertEquals(expect, result);
    }
}