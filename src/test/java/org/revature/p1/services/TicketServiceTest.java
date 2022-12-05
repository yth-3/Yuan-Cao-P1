package org.revature.p1.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.revature.p1.daos.TicketDao;
import org.revature.p1.dtos.requests.JudgeTicketRequest;
import org.revature.p1.dtos.requests.TicketCreationRequest;
import org.revature.p1.dtos.requests.TicketsByStatusRequest;
import org.revature.p1.models.Ticket;
import org.revature.p1.utils.enums.TicketStatus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

public class TicketServiceTest {
    private final TicketDao mockTicketDao = Mockito.mock(TicketDao.class);
    private TicketService sut;

    @Before
    public void init() {
        sut = new TicketService(mockTicketDao);
    }

    @Test
    public void test_CreateTicket_validTicket() {
        TicketCreationRequest reqStub = new TicketCreationRequest("50", "Candy", "FOOD");
        try {
            doNothing().when(mockTicketDao).createTicket(null);
            sut.createTicket(reqStub, "someID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test_ApproveTicket_validSelection() {
        doNothing().when(mockTicketDao).approveTicket(null, null);
        JudgeTicketRequest reqStub = new JudgeTicketRequest(null);
        sut.approveTicket(reqStub, null);
    }

    @Test
    public void test_DenyTicket() {
        doNothing().when(mockTicketDao).denyTicket(null, null);
        JudgeTicketRequest reqStub = new JudgeTicketRequest(null);
        sut.approveTicket(reqStub, null);
    }

    @Test
    public void test_GetAllPreviousTicketsByStatus() {
        List<Ticket> list = new ArrayList<>();
        list.add(null);
        Mockito.when(mockTicketDao.getAllPreviousTicketsByStatus(null, TicketStatus.PENDING, null)).thenReturn(list);

        TicketsByStatusRequest reqStub = new TicketsByStatusRequest(TicketStatus.PENDING);
        List<Ticket> testList = sut.getAllPreviousTicketsByStatus(reqStub, null, null);
        assertEquals(list.size(), testList.size());
    }
}