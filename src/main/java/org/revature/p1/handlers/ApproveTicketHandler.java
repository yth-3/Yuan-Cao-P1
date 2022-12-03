package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.dtos.requests.JudgeTicketRequest;
import org.revature.p1.services.TicketService;
import org.revature.p1.services.TokenService;
import org.revature.p1.utils.enums.ClientUserType;
import org.revature.p1.utils.exceptions.AttemptUpdatingFinalizedTicketException;

public class ApproveTicketHandler {
    private ObjectMapper mapper;
    private TicketService ticketService;
    private  TokenService tokenService;

    public ApproveTicketHandler(ObjectMapper mapper, TicketService ticketService, TokenService tokenService) {
        this.mapper = mapper;
        this.ticketService = ticketService;
        this.tokenService = tokenService;
    }

    public void approveTicket(Context ctx) {
        try {
            String token = ctx.req.getHeader("Authorization");
            ClientUserType userType = tokenService.extractUserType(token);
            String userId = tokenService.extractUserId(token);
            if (userType == null || userType != ClientUserType.MANAGER) {
                ctx.status(401);
                return;
            }
            JudgeTicketRequest req = mapper.readValue(ctx.req.getInputStream(), JudgeTicketRequest.class);
            ticketService.approveTicket(req, userId);
            ctx.status(200);
        } catch (AttemptUpdatingFinalizedTicketException e) {
            ctx.status(401);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
