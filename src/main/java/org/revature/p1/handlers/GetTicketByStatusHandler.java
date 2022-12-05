package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.dtos.requests.TicketsByStatusRequest;
import org.revature.p1.models.Ticket;
import org.revature.p1.services.TicketService;
import org.revature.p1.services.TokenService;
import org.revature.p1.utils.enums.ClientUserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GetTicketByStatusHandler {
    private ObjectMapper mapper;
    private TicketService ticketService;
    private TokenService tokenService;
    private final static Logger logger = LoggerFactory.getLogger("TicketByStatusListing");

    public GetTicketByStatusHandler(ObjectMapper mapper, TicketService ticketService, TokenService tokenService) {
        this.mapper = mapper;
        this.ticketService = ticketService;
        this.tokenService = tokenService;
    }


    public void getTicketsByStatus(Context ctx) {
        logger.info("Attempting to received ticket list...");
        try {
            String token = ctx.req.getHeader("Authorization");
            String userId = tokenService.extractUserId(token);
            ClientUserType userType = tokenService.extractUserType(token);
            if (userType == null || userType == ClientUserType.ADMIN) {
                ctx.status(401);
                ctx.result("Only managers and employees have access to tickets.");
                return;
            } else if (userType == ClientUserType.MANAGER) {
                logger.info("... manager request accepted.");
            } else {
                logger.info("... user request accepted.");
            }

            TicketsByStatusRequest req = mapper.readValue(ctx.req.getInputStream(), TicketsByStatusRequest.class);
            List<Ticket> tickets = ticketService.getAllPreviousTicketsByStatus(req, userId, userType);
            ctx.json(tickets);
            ctx.status(200);
        } catch (Exception e) {
            logger.warn("... Unexpected error encountered.");
            throw new RuntimeException(e);
        }


        ctx.status(200);
    }
}
