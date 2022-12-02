package org.revature.p1.services;

import org.revature.p1.daos.TicketDao;
import org.revature.p1.dtos.requests.JudgeTicketRequest;
import org.revature.p1.dtos.requests.TicketCreationRequest;
import org.revature.p1.models.Ticket;
import org.revature.p1.models.TicketStub;
import org.revature.p1.utils.enums.TicketType;

import java.sql.SQLException;
import java.util.List;

public class TicketService {
    private TicketDao ticketDao;

    public TicketService(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public void createTicket(TicketCreationRequest req, String id) throws SQLException {
        TicketStub ticketStub = new TicketStub(id, req.getAmount(), req.getDescription(), TicketType.valueOf(req.getType()));
        // Fill in info from req and id
        this.ticketDao.create(ticketStub);
    }

    public List<Ticket> getAllTickets() {
        return this.ticketDao.findAllPending();
    }

    public void approveTicket(JudgeTicketRequest req, String userId) {
        ticketDao.approveTicket(req.getTicketId(), userId);
    }

    public void denyTicket(JudgeTicketRequest req, String userId) {
        ticketDao.denyTicket(req.getTicketId(), userId);
    }
}
