package com.mastercard.finicity.client.test;

import com.google.gson.Gson;
import com.mastercard.finicity.client.ApiClient;
import com.mastercard.finicity.client.ApiException;
import com.mastercard.finicity.client.Configuration;
import com.mastercard.finicity.client.api.AuthenticationApi;
import com.mastercard.finicity.client.model.ErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BaseTest {

    protected final static String PARTNER_ID = System.getProperty("partnerId");
    protected final static String PARTNER_SECRET = System.getProperty("partnerSecret");
    protected final static String APP_KEY = System.getProperty("appKey");
    protected final static String CUSTOMER_ID = System.getProperty("customerId");

    protected final static ApiClient apiClient = Configuration.getDefaultApiClient();
    protected final static AuthenticationApi authenticationApi = new AuthenticationApi(apiClient);

    @BeforeEach
    void setUp() throws ApiException {
        // Prism
        // apiClient.setBasePath("http://localhost:4010");

        // Client logging
        apiClient.setDebugging(true);
    }

    protected void logApiException(ApiException e) {
        System.err.println("Status code: " + e.getCode());
        System.err.println("Reason: " + e.getResponseBody());
        System.err.println("Response headers: " + e.getResponseHeaders());
    }

    private static ErrorMessage parseError(ApiException e) {
        return new Gson().fromJson(e.getResponseBody(), ErrorMessage.class);
    }

    protected void assertErrorCodeEquals(int code, ApiException e) {
        assertEquals(code, parseError(e).getCode());
    }

    protected void assertErrorMessageEquals(String message, ApiException e) {
        assertEquals(message, parseError(e).getMessage());
    }

    protected void fail() {
        Assertions.fail("Shouldn't reach this line");
    }
}
