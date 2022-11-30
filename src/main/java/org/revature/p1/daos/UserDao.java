package org.revature.p1.daos;

import org.revature.p1.models.User;
import org.revature.p1.utils.ConnectionFactory;
import org.revature.p1.utils.enums.ClientUserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class UserDao implements CrudDao<User> {
    @Override
    public void create(User obj) throws SQLException {
        String uuidString = UUID.nameUUIDFromBytes((obj.getUsername() + obj.getPassword() + "DEADBEEF").getBytes()).toString();
        Connection con = ConnectionFactory.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(
                "insert into ERS_USERS(user_id, username, email, password, given_name, surname, is_active, role_id) " +
                "values (?, ?, ?, ?, ?, ?, false, ?);");
        ps.setString(1, uuidString);
        ps.setString(2, obj.getUsername());
        ps.setString(3, obj.getEmail());
        ps.setString(4, obj.getPassword());
        ps.setString(5, obj.getForename());
        ps.setString(6, obj.getSurname());
        ps.setString(7, String.valueOf(obj.getRole()));
        ps.executeUpdate();
    }

    @Override
    public User read(User obj) {
        return null;
    }

    @Override
    public void update(User obj) {

    }

    @Override
    public void delete(User obj) {

    }

    @Override
    public List<User> findAll() {
        return null;
    }

    public User getUserById(String username, String password) {
        User user = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_users WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();

                user.setId(rs.getString("user_id"));
                user.setForename(rs.getString("given_name"));
                user.setRole(ClientUserType.valueOf(rs.getString("role_id")));

                boolean active = rs.getString("is_active").equals("t");
                user.setActive(active);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
