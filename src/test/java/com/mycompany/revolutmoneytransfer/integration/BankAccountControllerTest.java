package com.mycompany.revolutmoneytransfer.integration;

import com.mycompany.revolutmoneytransfer.MoneyTransferApplication;
import com.mycompany.revolutmoneytransfer.controller.BankAccountsController;
import com.mycompany.revolutmoneytransfer.dto.BankAccountDto;
import com.mycompany.revolutmoneytransfer.model.BankAccount;
import com.mycompany.revolutmoneytransfer.service.BankAccountService;
import org.glassfish.grizzly.http.server.HttpServer;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.Assert.assertNotEquals;

public class BankAccountControllerTest {
    private static HttpServer server;
    private static WebTarget target;

    @BeforeClass
    public static void beforeAll() {
        // start the server
        server = MoneyTransferApplication.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        target = c.target(MoneyTransferApplication.BASE_URI);
    }

    @AfterClass
    public static void afterAll() {
        server.shutdownNow();
    }


    @Test
    public void testGetAllBankAccounts() {
        Response response = target.path(BankAccountsController.BASE_URL)
                .request().get();

        assertEquals(Response.Status.OK, response.getStatusInfo().toEnum());

        Collection<BankAccount> bankAccount = response.readEntity(new GenericType<Collection<BankAccount>>(){});

        assertEquals(bankAccount.size(), BankAccountDto.getInstance().getAllBankAccounts().size());
    }


    @Test
    public void testGetBankAccountById() {
        Response response = getById(BankAccountDto.SA_BANK_ACCOUNT_ID);

        assertEquals(Response.Status.OK, response.getStatusInfo().toEnum());

        BankAccount bankAccount = response.readEntity(BankAccount.class);

        assertEquals(bankAccount.getId(), BankAccountDto.SA_BANK_ACCOUNT_ID);
    }

    @Test
    public void testGetNullBankAccount() {
        Response response = getById(null);

        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo().toEnum());
    }


    @Test
    public void testNonExistingBankAccountById() {
        Response response = getById(new Random().nextLong());

        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo().toEnum());
    }


    @Test
    public void testUpdateBankAccount_DepositMoney() {
        BankAccountService bankAccountService = BankAccountService.getInstance();

        BankAccount account = bankAccountService.getBankAccountById(BankAccountDto.ES_BANK_ACCOUNT_ID);
        BigDecimal accountBalance = account.getBalance();
        accountBalance = accountBalance.add(BigDecimal.TEN);
        Response response = target.path(BankAccountsController.BASE_URL+"/"+BankAccountDto.ES_BANK_ACCOUNT_ID)
                .queryParam("action", "deposit")
                .queryParam("amount", "10")
                .request()
                .put(from(account));

        assertEquals(Response.Status.OK, response.getStatusInfo().toEnum());

        BankAccount updatedAccount = bankAccountService.getBankAccountById(BankAccountDto.ES_BANK_ACCOUNT_ID);

        assertThat(accountBalance, Matchers.comparesEqualTo(updatedAccount.getBalance()));
    }


    @Test
    public void testUpdateNonExistingBankAccount_DepositMoney() {
       BankAccountService bankAccountService = BankAccountService.getInstance();
       BankAccount account = bankAccountService.getBankAccountById(BankAccountDto.ES_BANK_ACCOUNT_ID);
       Response response = target.path(BankAccountsController.BASE_URL+"/"+20)
                .queryParam("action", "deposit")
                .queryParam("amount", "10")
                .request()
                .put(from(account));

        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo().toEnum());
    }

    
    @Test
    public void testUpdateBankAccount_UpdateName() {
        BankAccountService bankAccountService = BankAccountService.getInstance();

        BankAccount account = bankAccountService.getBankAccountById(BankAccountDto.ES_BANK_ACCOUNT_ID);
        String accountHolderName = "New Name";
        Response response = target.path(BankAccountsController.BASE_URL+"/"+BankAccountDto.ES_BANK_ACCOUNT_ID)
                .queryParam(BankAccountsController.GET_ACTION_ON_BANK_ACCOUNT, "updateName")
                .queryParam(BankAccountsController.GET_NAME_TO_UPDATE_FOR_BANK_ACCOUNT, accountHolderName)
                .request()
                .put(from(account));

        assertEquals(Response.Status.OK, response.getStatusInfo().toEnum());

        BankAccount updatedAccount = bankAccountService.getBankAccountById(BankAccountDto.ES_BANK_ACCOUNT_ID);

        assertEquals(accountHolderName, updatedAccount.getName());
    }

    @Test
    public void testUpdateNonExistingBankAccount_UpdateName() {
       BankAccountService bankAccountService = BankAccountService.getInstance();
       BankAccount account = bankAccountService.getBankAccountById(BankAccountDto.ES_BANK_ACCOUNT_ID);
       Response response = target.path(BankAccountsController.BASE_URL+"/"+20)
                .queryParam("action", "updateName")
                .queryParam("name", "New Name")
                .request()
                .put(from(account));

        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo().toEnum());
    }    

    @Test
    public void testIncorrectUpdateBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(new Random().nextLong());

        Response response = target.path(BankAccountsController.BASE_URL +"/1")
                .request()
                .put(from(bankAccount));

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo().toEnum());
    }


    @Test
    public void testCreateBankAccount() {
        BankAccountService bankAccountService = BankAccountService.getInstance();
        String OWNER_NAME = "Eli";

        BankAccount bankAccount = new BankAccount(OWNER_NAME, BigDecimal.ZERO, BigDecimal.ZERO);

        Response response = target.path(BankAccountsController.BASE_URL)
                .request()
                .post(from(bankAccount));

        assertEquals(Response.Status.OK, response.getStatusInfo().toEnum());

        BankAccount returnedAccount = response.readEntity(BankAccount.class);
        BankAccount createdAccount = bankAccountService.getBankAccountById(returnedAccount.getId());

        assertNotNull(returnedAccount);
        assertNotNull(createdAccount);

        assertNotEquals(returnedAccount.getId(), bankAccount.getId());
        assertEquals(returnedAccount.getId(), createdAccount.getId());
        assertEquals(OWNER_NAME, createdAccount.getName());
    }

    private Response getById(Long id) {
        return target.path(BankAccountsController.BASE_URL + "/{" + BankAccountsController.GET_BANK_ACCOUNT_BY_ID_PATH + "}")
                .resolveTemplate("id", id == null ? "null" : id)
                .request().get();
    }

    private static Entity from(BankAccount bankAccount) {
        return Entity.entity(bankAccount, MediaType.valueOf(MediaType.APPLICATION_JSON));
    }
}
