package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.dtos.requests.JudgeTicketRequest;
import org.revature.p1.dtos.requests.TicketsByStatusRequest;
import org.revature.p1.models.Ticket;
import org.revature.p1.services.TicketService;
import org.revature.p1.services.TokenService;
import org.revature.p1.utils.enums.ClientUserType;

import java.util.List;

public class GetTicketByStatusHandler {
    private ObjectMapper mapper;
    private TicketService ticketService;
    private TokenService tokenService;

    public GetTicketByStatusHandler(ObjectMapper mapper, TicketService ticketService, TokenService tokenService) {
        this.mapper = mapper;
        this.ticketService = ticketService;
        this.tokenService = tokenService;
    }


    public void getTicketsByStatus(Context ctx) {
        try {
            String token = ctx.req.getHeader("Authorization");
            ClientUserType userType = tokenService.extractUserType(token);
            if (userType == null || userType != ClientUserType.EMPLOYEE) {
                ctx.status(401);
                return;
            }

            String userId = tokenService.extractUserId(token);
            TicketsByStatusRequest req = mapper.readValue(ctx.req.getInputStream(), TicketsByStatusRequest.class);
            List<Ticket> tickets = ticketService.getAllPreviousTicketsByStatus(req, userId);
            ctx.json(tickets);
            ctx.status(200);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        ctx.status(200);
    }
}
