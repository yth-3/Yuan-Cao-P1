package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.dtos.requests.TicketCreationRequest;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.services.TicketService;
import org.revature.p1.services.TokenService;
import org.revature.p1.utils.enums.ClientUserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class CreateTicketHandler {
    private ObjectMapper mapper;
    private TicketService ticketService;
    private TokenService tokenService;
    private final static Logger logger = LoggerFactory.getLogger("TicketCreation");

    public CreateTicketHandler(ObjectMapper mapper, TicketService ticketService, TokenService tokenService) {
        this.mapper = mapper;
        this.ticketService = ticketService;
        this.tokenService = tokenService;
    }


    public void createTicket(Context ctx) {
        logger.info("Attempting to create a new ticket...");
        try {
            String token = ctx.req.getHeader("Authorization");
            Principal principal = tokenService.extractPrincipal(token);
            if (principal == null
                    || principal.getType() == ClientUserType.ADMIN
                    || principal.getType() == ClientUserType.MANAGER) {
                logger.warn("... rejected due to mismatched privilege.");
                ctx.status(401);
                return;
            }

            TicketCreationRequest req = mapper.readValue(ctx.req.getInputStream(), TicketCreationRequest.class);
            if (req.getAmount() == null || Double.valueOf(req.getAmount()) < 0) {
                logger.info("... rejected, due to no/incorrect claim amount.");
                ctx.status(400);
                ctx.result("Reimbursement claims must have a positive amount.");
                return;
            } else if (req.getDescription() == null) {
                logger.info("... rejected, due to having no claim reason.");
                ctx.status(400);
                ctx.result("Reimbursement claims must have a reason or description.");
                return;
            } else if (req.getType() == null) {
                logger.info("... rejected, due to having no claim type.");
                ctx.status(400);
                ctx.result("Reimbursement claims must use one of the established claim categories.");
                return;
            }

            ticketService.createTicket(req, principal.getId());
            logger.info("... accepted.");
            ctx.status(200);
        } catch (Exception e) {
            logger.warn("... unexpected error encounter.");
            e.printStackTrace();
        }
    }
}
