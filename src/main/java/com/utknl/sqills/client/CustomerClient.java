package com.utknl.sqills.client;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerClient {

    private static final String URL = "http://localhost:8080/customers/";

    private final Logger logger = Logger.getLogger(getClass().getSimpleName());
    private final HttpClient client = HttpClient.newHttpClient();
    private HttpResponse<String> clientResponse;

    public HttpResponse<String> postCustomer(JSONObject jsonObject) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .build();
        try {
            clientResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to post customers", e);
        }

        return clientResponse;
    }

    public HttpResponse<String> getCustomer(String firstName) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + firstName))
                .header("Content-Type", "application/json")
                .build();
        try {
            clientResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to get customers", e);
        }

        return clientResponse;
    }

    public HttpResponse<String> deleteCustomer(String firstName) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + firstName))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        try {
            clientResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to delete customers", e);
        }

        return clientResponse;
    }

    public HttpResponse<String> editCustomer(JSONObject jsonObject) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .build();
        try {
            clientResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to patch customers", e);
        }

        return clientResponse;
    }

}
