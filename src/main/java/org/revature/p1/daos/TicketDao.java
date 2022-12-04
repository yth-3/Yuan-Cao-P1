package org.revature.p1.daos;

import org.revature.p1.models.Ticket;
import org.revature.p1.models.TicketStub;
import org.revature.p1.models.User;
import org.revature.p1.utils.ConnectionFactory;
import org.revature.p1.utils.enums.TicketStatus;
import org.revature.p1.utils.enums.TicketType;
import org.revature.p1.utils.exceptions.AttemptUpdatingFinalizedTicketException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketDao implements CrudDao<TicketStub> {
    @Override
    public void create(TicketStub obj) throws SQLException{
        Connection con = ConnectionFactory.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO ers_reimbursements (REIMB_ID, AMOUNT, SUBMITTED, DESCRIPTION, AUTHOR_ID, STATUS_ID, TYPE_ID) " +
                        "VALUES (?, ?, current_timestamp, ?, ?, 'PENDING', ?)"
        );

        String uuidString = UUID.nameUUIDFromBytes((obj.getDescription() + obj.getSubmitterId() + "DEADBEEF").getBytes()).toString();

        ps.setString(1, uuidString); // need to generate id
        ps.setDouble(2, Double.valueOf(obj.getAmount()));
        ps.setString(3, obj.getDescription());
        ps.setString(4, obj.getSubmitterId());
        ps.setString(5, obj.getType().toString());
        ps.executeUpdate();
    }

    @Override
    public TicketStub read(TicketStub obj) {
        return null;
    }

    @Override
    public void update(TicketStub obj) {

    }

    @Override
    public void delete(TicketStub obj) {

    }

    @Override
    public List<TicketStub> findAll() {
        return null;
    }

    public List<Ticket> findAllPending() {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE status_id = 'PENDING';");
            ResultSet rs = ps.executeQuery();

            List<Ticket> tickets = new ArrayList<>();
            while (rs.next()) {
                Ticket ticket = new Ticket();

                ticket.setId(rs.getString("reimb_id"));
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setSubmitDate(rs.getTimestamp("submitted").getTime());

                if (rs.getTimestamp("resolved") != null) {
                    ticket.setResolveDate(rs.getTimestamp("resolved").getTime());
                }
                ticket.setDescription(rs.getString("description"));
                ticket.setReceipt(rs.getBytes("receipt"));
                ticket.setPaymentId(rs.getString("payment_id"));

                User issuer = new User();
                issuer.setId(rs.getString("author_id"));
                ticket.setIssuer(issuer);

                // Deal with getting the resolver better; not really handled atm
                rs.getString("resolver_id");
                User resolver = new User();
                ticket.setResolver(resolver);

                ticket.setStatus(TicketStatus.PENDING);
                ticket.setType(TicketType.valueOf(rs.getString("type_id")));

                tickets.add(ticket);
            }
            return tickets;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ticket> getAllPreviousTicketsByStatus(String userId, TicketStatus status) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps;
            if (status == null) {
                ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE author_id = ?");
                ps.setString(1, userId);
            } else {
                ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE author_id = ? AND status_id = ?");
                ps.setString(1, userId);
                ps.setString(2, status.toString());
            }

            ResultSet rs = ps.executeQuery();

            List<Ticket> tickets = new ArrayList<>();
            while (rs.next()) {
                Ticket ticket = new Ticket();

                ticket.setId(rs.getString("reimb_id"));
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setSubmitDate(rs.getTimestamp("submitted").getTime());

                if (rs.getTimestamp("resolved") != null) {
                    ticket.setResolveDate(rs.getTimestamp("resolved").getTime());
                }
                ticket.setDescription(rs.getString("description"));
                ticket.setReceipt(rs.getBytes("receipt"));
                ticket.setPaymentId(rs.getString("payment_id"));

                User issuer = new User();
                issuer.setId(rs.getString("author_id"));
                ticket.setIssuer(issuer);

                // Deal with getting the resolver better; not really handled atm
                rs.getString("resolver_id");
                User resolver = new User();
                ticket.setResolver(resolver);

                ticket.setStatus(TicketStatus.PENDING);
                ticket.setType(TicketType.valueOf(rs.getString("type_id")));

                tickets.add(ticket);
            }
            return tickets;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void approveTicket(String ticketId, String userId) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE ers_reimbursements " +
                            "SET " +
                            "status_id = 'APPROVED', " +
                            "resolver_id = ? " +
                            "WHERE reimb_id = ? AND status_id = 'PENDING'"
            );

            ps.setString(1, userId);
            ps.setString(2, ticketId);
            int result = ps.executeUpdate();
            if (result == 0) {
                throw new AttemptUpdatingFinalizedTicketException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void denyTicket(String ticketId, String userId) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE ers_reimbursements " +
                            "SET " +
                            "status_id = 'DENIED', " +
                            "resolver_id = ? " +
                            "WHERE reimb_id = ? AND status_id = 'PENDING'"
            );

            ps.setString(1, userId);
            ps.setString(2, ticketId);

            int result = ps.executeUpdate();
            if (result == 0) {
                throw new AttemptUpdatingFinalizedTicketException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
