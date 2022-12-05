package org.revature.p1.services;

import org.revature.p1.daos.TicketDao;
import org.revature.p1.dtos.requests.JudgeTicketRequest;
import org.revature.p1.dtos.requests.TicketCreationRequest;
import org.revature.p1.dtos.requests.TicketsByStatusRequest;
import org.revature.p1.models.Ticket;
import org.revature.p1.dtos.responses.TicketStub;
import org.revature.p1.utils.enums.ClientUserType;
import org.revature.p1.utils.enums.TicketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class TicketService {
    private TicketDao ticketDao;

    public TicketService(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }
    private final static Logger logger = LoggerFactory.getLogger("UserService");

    public void createTicket(TicketCreationRequest req, String id) throws SQLException {
        TicketStub ticketStub = new TicketStub(id, req.getAmount(), req.getDescription(), TicketType.valueOf(req.getType().toUpperCase()));
        // Fill in info from req and id
        this.ticketDao.createTicket(ticketStub);
    }

    public void approveTicket(JudgeTicketRequest req, String userId) {
        ticketDao.approveTicket(req.getTicketId(), userId);
    }

    public void denyTicket(JudgeTicketRequest req, String userId) {
        ticketDao.denyTicket(req.getTicketId(), userId);
    }

    public List<Ticket> getAllPreviousTicketsByStatus(TicketsByStatusRequest req, String userId, ClientUserType userType) {
        return ticketDao.getAllPreviousTicketsByStatus(userId, req.getStatus(), userType);
    }
}
