package com.mycompany.revolutmoneytransfer.controller;

import com.mycompany.revolutmoneytransfer.exceptions.ObjectModificationException;
import com.mycompany.revolutmoneytransfer.model.Transfer;
import com.mycompany.revolutmoneytransfer.service.TransfersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(TransfersController.BASE_URL)
@Produces(MediaType.APPLICATION_JSON)
public class TransfersController {
    private final Logger log = LoggerFactory.getLogger(TransfersController.class);

    public static final String BASE_URL = "/transfers";
    public static final String GET_TRANSFER_BY_ID_PATH = "id";

    private TransfersService transfersService = TransfersService.getInstance();

    @GET
    public Response getAllPendingTransfers() {
        log.debug("getAllPendingTransfers invoked");
        return Response.ok().entity(transfersService.getAllTransfers()).build();
    }

    @GET()
    @Path("{" + GET_TRANSFER_BY_ID_PATH + "}")
    public Response getTransactionById(@PathParam(GET_TRANSFER_BY_ID_PATH) Long id) {
        log.debug("getTransactionById invoked with ID: " +id);
        return Response.ok().entity(transfersService.getTransactionById(id)).build();
    }

    @POST()
    public Response createTransactionToTransferMoney(Transfer transfer) throws ObjectModificationException {
        log.debug("createTransactionToTransferMoney invoked with transfer : " +transfer);
        transfer = transfersService.createTransactionToTransferMoney(transfer);

        return Response.ok().entity(transfer).build();
    }
}
