package com.utknl.sqills.stepdefinitions;

import com.utknl.sqills.client.CustomerClient;
import com.utknl.sqills.request.CustomerRequest;
import com.utknl.sqills.response.CustomerResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;

import java.net.http.HttpResponse;

public class GetCustomerSteps {

    CustomerClient customerClient = new CustomerClient();
    CustomerRequest customerRequest = new CustomerRequest();
    CustomerResponse customerResponse = new CustomerResponse();
    Gson g = new Gson();
    HttpResponse<String> clientResponse;

    @When("non-existing customer info is queried")
    public void nonExistingCustomerInfoIsQueried() {
        String firstName = RandomStringUtils.randomAlphanumeric(8);
        clientResponse = customerClient.getCustomer(firstName);
    }

    @Then("get endpoint status code should be {int}")
    public void getEndpointStatusCodeShouldBe(int statusCode) {
        Assert.assertEquals("status code is not " + statusCode, statusCode, clientResponse.statusCode());
    }

    @Given("existing customer")
    public void existingCustomer() {
        customerRequest.setFirst_name(RandomStringUtils.randomAlphanumeric(8));
        customerRequest.setLast_name(RandomStringUtils.randomAlphanumeric(8));
        customerClient.postCustomer(customerRequest.toJson());
    }

    @When("existing customer info is queried")
    public void existingCustomerInfoIsQueried() {
        String firstName = customerRequest.getFirst_name();
        clientResponse = customerClient.getCustomer(firstName);
    }

    @When("empty request is queried")
    public void emptyRequestIsQueried() {
        clientResponse = customerClient.getCustomer("");
    }

    @Then("response body should contain the queried customer information")
    public void responseBodyShouldContainTheQueriedCustomerInformation() {
        customerResponse = g.fromJson(clientResponse.body(), CustomerResponse.class);
        Assert.assertEquals(customerRequest.getFirst_name(), customerResponse.getFirst_name());
        Assert.assertEquals(customerRequest.getLast_name(), customerResponse.getLast_name());
    }

}
