package com.mycompany.revolutmoneytransfer.controller;

import com.mycompany.revolutmoneytransfer.exceptions.ObjectModificationException;
import com.mycompany.revolutmoneytransfer.model.BankAccount;
import com.mycompany.revolutmoneytransfer.model.Currency;
import com.mycompany.revolutmoneytransfer.service.BankAccountService;
import java.math.BigDecimal;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path(BankAccountsController.BASE_URL)
@Produces(MediaType.APPLICATION_JSON)
public class BankAccountsController {

    public static final String BASE_URL = "/bankAccounts";
    public static final String GET_BANK_ACCOUNT_BY_ID_PATH = "id";
    public static final String GET_ACTION_ON_BANK_ACCOUNT = "action";
    public static final String GET_NAME_TO_UPDATE_FOR_BANK_ACCOUNT = "name";
    public static final String GET_DEPOSIT_AMOUNT_ON_BANK_ACCOUNT = "amount";

    private final Logger log = LoggerFactory.getLogger(TransfersController.class);

    private final static BankAccountService BANK_ACCOUNT_SERVICE = BankAccountService.getInstance();

    @GET
    public Response getAllBankAccounts() {
        Collection<BankAccount> bankAccounts;
        bankAccounts = BANK_ACCOUNT_SERVICE.getAllBankAccounts();
        if (bankAccounts == null) {
            log.debug("No accounts available.");
            Response.noContent().build();
        }
        log.debug("BankAccounts retrieved: " + bankAccounts);
        return Response.ok(bankAccounts).build();
    }

    @GET
    @Path("{" + GET_BANK_ACCOUNT_BY_ID_PATH + "}")
    public Response getBankAccountById(@PathParam(GET_BANK_ACCOUNT_BY_ID_PATH) Long id) {
        BankAccount bankAccount;

        bankAccount = BANK_ACCOUNT_SERVICE.getBankAccountById(id);

        if (bankAccount == null) {
            throw new WebApplicationException("Invalid ID. The bank account does not exist.", Response.Status.NOT_FOUND);
        }
        log.debug("BankAccount retrieved: " + bankAccount);
        return Response.ok(bankAccount).build();
    }

    @PUT
    @Path("{" + GET_BANK_ACCOUNT_BY_ID_PATH + "}")
    public Response updateBankAccount(@PathParam(GET_BANK_ACCOUNT_BY_ID_PATH) Long id, @QueryParam(GET_ACTION_ON_BANK_ACCOUNT) String action, 
            @QueryParam(GET_NAME_TO_UPDATE_FOR_BANK_ACCOUNT) String newName, @QueryParam(GET_DEPOSIT_AMOUNT_ON_BANK_ACCOUNT) String depositAmount) throws ObjectModificationException {
        BankAccount existingBankAccount = BANK_ACCOUNT_SERVICE.getBankAccountById(id);

        if (existingBankAccount == null) {
            throw new WebApplicationException("Invalid ID. The bank account does not exist.", Response.Status.NOT_FOUND);
        }

        if (action.equalsIgnoreCase("deposit")) {
            if (depositAmount == null) {
                throw new WebApplicationException("Invalid request.", Response.Status.BAD_REQUEST);
            }
            existingBankAccount.setBalance(existingBankAccount.getBalance().add(new BigDecimal(depositAmount)));
            BankAccountService.getInstance().depositMoneyIntoBankAccount(existingBankAccount);
            log.debug("Money deposited into BankAccount : " + existingBankAccount);

        }
        if (action.equalsIgnoreCase("updateName")) {
            existingBankAccount.setName(newName);
            if (newName == null) {
                throw new WebApplicationException("Invalid request.", Response.Status.BAD_REQUEST);
            }
            BankAccountService.getInstance().updateBankAccount(existingBankAccount);
            log.debug("BankAccount updated: " + existingBankAccount);

        }
        return Response.ok(existingBankAccount).build();

    }

    @POST
    public Response createBankAccount(BankAccount bankAccount) throws ObjectModificationException {
        BankAccount createdBankAccount;
        bankAccount.setId(new Random().nextLong());
        bankAccount.setBalance(BigDecimal.ZERO);
        bankAccount.setBlockedAmount(BigDecimal.ZERO);
        bankAccount.setCurrency(Currency.GBP);
        createdBankAccount = BANK_ACCOUNT_SERVICE.createBankAccount(bankAccount);
        log.debug("BankAccounts created: " + bankAccount);

        return Response.ok(createdBankAccount).build();
    }
    
}
