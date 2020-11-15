package com.utknl.sqills.stepdefinitions;

import com.utknl.sqills.client.CustomerClient;
import com.utknl.sqills.request.CustomerRequest;
import com.utknl.sqills.response.CustomerResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Assert;

import java.net.http.HttpResponse;

public class PostCustomerSteps {

    CustomerClient customerClient = new CustomerClient();
    CustomerRequest customerRequest = new CustomerRequest();
    CustomerResponse customerResponse = new CustomerResponse();
    JSONObject jsonObject = new JSONObject();
    Gson g = new Gson();
    HttpResponse<String> clientResponse;

    @Given("an invalid request body")
    public void anInvalidRequestBody() {
        jsonObject.accumulate("first_name", "abc");
        jsonObject.accumulate("last_name", "def");
        jsonObject.accumulate("mail", "abc.def@gmail.com");
    }

    @When("invalid request is sent to client")
    public void invalidRequestIsSentToClient() {
        clientResponse = customerClient.postCustomer(jsonObject);
    }

    @When("a valid request is sent to client")
    public void aValidRequestIsSentToClient() {
        clientResponse = customerClient.postCustomer(customerRequest.toJson());
    }

    @Given("valid request body")
    public void validRequestBody() {
        customerRequest.setFirst_name(RandomStringUtils.randomAlphanumeric(8));
        customerRequest.setLast_name(RandomStringUtils.randomAlphanumeric(8));
    }

    @Given("request body with only first name")
    public void requestBodyWithOnlyFirstName() {
        customerRequest.setFirst_name(RandomStringUtils.randomAlphanumeric(8));
    }

    @Given("request body with only last name")
    public void requestBodyWithOnlyLastName() {
        customerRequest.setLast_name(RandomStringUtils.randomAlphanumeric(8));
    }

    @Given("valid request body with {string} and {string} name")
    public void validRequestBodyWithFirstAndLastName(String first, String last) {
        customerRequest.setFirst_name(first);
        customerRequest.setLast_name(last);
    }

    @And("same customer is added again")
    public void sameCustomerIsAddedAgain() {
        clientResponse = customerClient.postCustomer(customerRequest.toJson());
    }

    @Then("client response should contain customer information")
    public void clientResponseShouldContainCustomerInformation() {
        customerResponse = g.fromJson(clientResponse.body(), CustomerResponse.class);
        Assert.assertEquals(customerRequest.getFirst_name(), customerResponse.getFirst_name());
        Assert.assertEquals(customerRequest.getLast_name(), customerResponse.getLast_name());
    }

    @Then("post endpoint status code should be {int}")
    public void postEndpointStatusCodeShouldBe(int statusCode) {
        Assert.assertEquals("status code is not " + statusCode, statusCode, clientResponse.statusCode());
    }
}
