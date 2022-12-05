package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.dtos.requests.JudgeTicketRequest;
import org.revature.p1.services.TicketService;
import org.revature.p1.services.TokenService;
import org.revature.p1.utils.enums.ClientUserType;
import org.revature.p1.utils.exceptions.AttemptUpdatingFinalizedTicketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DenyTicketHandler {
    private ObjectMapper mapper;
    private TicketService ticketService;
    private TokenService tokenService;
    private final static Logger logger = LoggerFactory.getLogger("ApproveTicket");

    public DenyTicketHandler(ObjectMapper mapper, TicketService ticketService, TokenService tokenService) {
        this.mapper = mapper;
        this.ticketService = ticketService;
        this.tokenService = tokenService;
    }

    public void denyTicket(Context ctx) {
        logger.info("Attempting to approve a ticket... ");
        try {
            String token = ctx.req.getHeader("Authorization");
            ClientUserType userType = tokenService.extractUserType(token);
            String userId = tokenService.extractUserId(token);
            if (userType == null || userType != ClientUserType.MANAGER) {
                ctx.status(401);
                ctx.result("Only a manager can approve a ticket.");
                logger.warn("... rejected, unauthorized attempt to approve a ticket.");
                return;
            }
            JudgeTicketRequest req = mapper.readValue(ctx.req.getInputStream(), JudgeTicketRequest.class);
            ticketService.denyTicket(req, userId);
            ctx.status(200);
            logger.info("... accepted.");
        } catch (AttemptUpdatingFinalizedTicketException e) {
            ctx.status(401);
            ctx.result("A ticket's status cannot be changed once approved.");
            logger.warn("... rejected, finalized tickets cannot be changed..");
        } catch (Exception e) {
            logger.warn("Unexpected error encountered.");
            e.printStackTrace();
        }
    }
}
