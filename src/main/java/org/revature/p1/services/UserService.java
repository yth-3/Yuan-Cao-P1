package org.revature.p1.services;

import org.revature.p1.daos.UserDao;
import org.revature.p1.dtos.requests.LoginRequest;
import org.revature.p1.dtos.requests.RegistrationRequest;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.models.User;
import org.revature.p1.utils.enums.ClientUserType;
import org.revature.p1.utils.exceptions.DuplicateUserException;
import org.revature.p1.utils.exceptions.InvalidLoginException;

import java.sql.SQLException;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Principal loginUser(LoginRequest req) {
        User user = userDao.getUserById(req.getUsername(), req.getPassword());
        if (user == null || !user.isActive()) {
            throw new InvalidLoginException("Invalid username or password.");
        }

        return new Principal(user.getId(), user.getForename(), user.getRole(), user.isActive());
    }

    public void registerUser(RegistrationRequest req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        user.setEmail(req.getEmail());
        user.setForename(req.getForename());
        user.setSurname(req.getSurname());
        user.setRole(req.getRole());

        if (isGoodCandidate(user)) {
            try {
                userDao.create(user);
            } catch (SQLException e) {
                throw new DuplicateUserException();
            }
        }
    }

    // Helper Functions
    private boolean isGoodCandidate(User user) {
        // TODO: Implement more checks if there is time
        // TODO: Assess if need to refactor to an util class
        return true;
    }
}
