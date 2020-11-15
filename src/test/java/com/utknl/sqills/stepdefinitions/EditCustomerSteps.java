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
import org.junit.Assert;

import java.net.http.HttpResponse;

public class EditCustomerSteps {

    CustomerClient customerClient = new CustomerClient();
    CustomerRequest customerRequest = new CustomerRequest();
    CustomerResponse customerResponse = new CustomerResponse();
    Gson g = new Gson();
    HttpResponse<String> clientResponse;
    String previousLastName = "";

    @When("non-existing customer info is edited")
    public void nonExistingCustomerInfoIsEdited() {
        customerRequest.setFirst_name(RandomStringUtils.randomAlphanumeric(8));
        customerRequest.setLast_name(RandomStringUtils.randomAlphanumeric(8));
        clientResponse = customerClient.editCustomer(customerRequest.toJson());
    }

    @Then("edit endpoint status code should be {int}")
    public void editEndpointStatusCodeShouldBe(int statusCode) {
        Assert.assertEquals("status code is not " + statusCode, statusCode, clientResponse.statusCode());
    }

    @Given("existing customer info to edit")
    public void existingCustomerInfoToEdit() {
        customerRequest.setFirst_name(RandomStringUtils.randomAlphanumeric(8));
        customerRequest.setLast_name(RandomStringUtils.randomAlphanumeric(8));
        customerClient.postCustomer(customerRequest.toJson());
    }

    @When("existing customer info is edited")
    public void existingCustomerInfoIsEdited() {
        customerRequest.setLast_name(RandomStringUtils.randomAlphanumeric(8));
        clientResponse = customerClient.editCustomer(customerRequest.toJson());
    }

    @Then("edit response body should not be empty")
    public void editResponseBodyShouldNotBeEmpty() {
        Assert.assertNotEquals("Response body should not be empty",
                "", clientResponse.body());
    }

    @Then("customer last name should be changed")
    public void customerLastNameShouldBeChanged() {
        clientResponse = customerClient.getCustomer(customerRequest.getFirst_name());
        customerResponse = g.fromJson(clientResponse.body(), CustomerResponse.class);
        Assert.assertEquals(customerRequest.getFirst_name(), customerResponse.getFirst_name());
        Assert.assertNotEquals(previousLastName, customerResponse.getLast_name());
    }

    @And("previous last name")
    public void previousLastName() {
        previousLastName = customerRequest.getLast_name();
    }

}
