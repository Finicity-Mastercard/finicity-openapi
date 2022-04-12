package com.mastercard.finicity.client.api;

import com.mastercard.finicity.client.ApiException;
import com.mastercard.finicity.client.model.CustomerAccount;
import com.mastercard.finicity.client.test.BaseTest;
import com.mastercard.finicity.client.test.utils.AccountUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentsApiTest extends BaseTest {

    private final PaymentsApi api = new PaymentsApi(apiClient);

    private static CustomerAccount existingAccount;

    @BeforeAll
    static void beforeAll() {
        try {
            var accountApi = new AccountsApi(apiClient);
            existingAccount = AccountUtils.getCustomerAccounts(accountApi, CUSTOMER_ID, "savings").get(0);
        } catch (ApiException e) {
            logApiException(e);
            fail();
        }
    }

    @Test
    void getAccountACHDetailsTest() {
        try {
            var achDetails = api.getAccountACHDetails(CUSTOMER_ID, existingAccount.getId());
            assertNotNull(achDetails.getRealAccountNumber());
            assertNotNull(achDetails.getRoutingNumber());
        } catch (ApiException e) {
            logApiException(e);
            fail();
        }
    }

    @Test
    void getAvailableBalanceTest() {
        try {
            var balance = api.getAvailableBalance(CUSTOMER_ID, existingAccount.getId());
            assertNotNull(balance.getAvailableBalance());
        } catch (ApiException e) {
            logApiException(e);
            fail();
        }
    }

    @Test
    void getAvailableBalanceLiveTest() {
        try {
            var balance = api.getAvailableBalanceLive(CUSTOMER_ID, existingAccount.getId());
            assertNotNull(balance.getAvailableBalance());
        } catch (ApiException e) {
            logApiException(e);
            fail();
        }
    }
}
