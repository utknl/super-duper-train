package com.utknl.sqills.stepdefinitions;

import com.utknl.sqills.client.CustomerClient;
import com.utknl.sqills.request.CustomerRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;

import java.net.http.HttpResponse;

public class DeleteCustomerSteps {

    CustomerClient customerClient = new CustomerClient();
    CustomerRequest customerRequest = new CustomerRequest();
    HttpResponse<String> clientResponse;

    @Given("existing customer to delete")
    public void existingCustomerToDelete() {
        customerRequest.setFirst_name(RandomStringUtils.randomAlphanumeric(8));
        customerRequest.setLast_name(RandomStringUtils.randomAlphanumeric(8));
        customerClient.postCustomer(customerRequest.toJson());
    }

    @When("existing customer info is deleted")
    public void existingCustomerInfoIsDeleted() {
        String firstName = customerRequest.getFirst_name();
        clientResponse = customerClient.deleteCustomer(firstName);
    }

    @Then("delete endpoint status code should be {int}")
    public void deleteEndpointStatusCodeShouldBe(int statusCode) {
        Assert.assertEquals("status code is not " + statusCode, statusCode, clientResponse.statusCode());
    }

    @When("non-existing customer info is deleted")
    public void nonExistingCustomerInfoIsDeleted() {
        String firstName = RandomStringUtils.randomAlphanumeric(8);
        clientResponse = customerClient.deleteCustomer(firstName);
    }

    @Then("response body should not be empty")
    public void responseBodyShouldNotBeEmpty() {
        Assert.assertNotEquals("Response body should not be empty",
                "", clientResponse.body());
    }

    @When("empty request is deleted")
    public void emptyRequestIsDeleted() {
        clientResponse = customerClient.deleteCustomer("");
    }

}
