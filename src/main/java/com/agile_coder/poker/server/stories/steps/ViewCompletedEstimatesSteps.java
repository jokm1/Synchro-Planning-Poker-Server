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

public class ViewCompletedEstimatesSteps extends BaseSteps {

    private String response;
    private int sessionId;

    @Given("all of the estimates are unlocked")
    public void unlock() throws IOException {
        startServer();
        sessionId = createSession();
        submitEstimate(sessionId, "Test1", "3");
        submitEstimate(sessionId, "Test2", "5");
        submitEstimate(sessionId, "Test3", "3");
        reveal(sessionId);
    }

    @When("I request the estimate list")
    public void getEstimates() throws IOException {
        try {
            response = getStatus(sessionId);
        } finally {
            stopServer();
        }
    }

    @Then("I receive the list of everyone's estimates")
    public void validate() {
        JSONObject expected = JSONObject
                .fromObject("{Test1: 'THREE', Test2: 'FIVE', Test3: 'THREE'}");
        JSONObject observed = JSONObject.fromObject(response);
        assertEquals(expected, observed);
    }
}
