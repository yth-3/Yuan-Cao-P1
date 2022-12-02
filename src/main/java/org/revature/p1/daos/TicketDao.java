package org.revature.p1.daos;

import org.revature.p1.models.Ticket;
import org.revature.p1.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TicketDao implements CrudDao<Ticket> {
    @Override
    public void create(Ticket obj) throws SQLException{
        Connection con = ConnectionFactory.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO ers_reimbursements (REIMB_ID, AMOUNT, SUBMITTED, DESCRIPTION, AUTHOR_ID, STATUS_ID, TYPE_ID) " +
                        "VALUES (?, ?, current_timestamp, ?, ?, 'PENDING', ?)"
        );
        ps.setString(1, "SOME UNIQUE ID"); // need to generate id
        ps.setDouble(2, Double.valueOf(obj.getAmount()));
        ps.setString(3, obj.getDescription());
        ps.setString(4, obj.getSubmitterId());
        ps.setString(5, obj.getType().toString());
        ps.executeUpdate();

        System.out.println("GOT HERE");
    }

    @Override
    public Ticket read(Ticket obj) {
        return null;
    }

    @Override
    public void update(Ticket obj) {

    }

    @Override
    public void delete(Ticket obj) {

    }

    @Override
    public List<Ticket> findAll() {
        return null;
    }
}
