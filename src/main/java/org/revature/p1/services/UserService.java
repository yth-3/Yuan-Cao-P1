package org.revature.p1.services;

import org.revature.p1.daos.UserDao;
import org.revature.p1.dtos.requests.AccountActivationRequest;
import org.revature.p1.dtos.requests.AccountDeactivationRequest;
import org.revature.p1.dtos.requests.LoginRequest;
import org.revature.p1.dtos.requests.RegistrationRequest;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.models.User;
import org.revature.p1.utils.exceptions.DuplicateUserException;
import org.revature.p1.utils.exceptions.InvalidLoginException;
import org.revature.p1.utils.exceptions.InvalidRegistrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserDao userDao;
    private final static Logger logger = LoggerFactory.getLogger("UserService");

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(RegistrationRequest req) {
        logger.info("... registering user ...");
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        user.setEmail(req.getEmail());
        user.setForename(req.getForename());
        user.setSurname(req.getSurname());
        user.setRole(req.getRole());

        if (isGoodCandidate(user)) {
            try {
                userDao.createUser(user);
            } catch (SQLException e) {
                throw new DuplicateUserException();
            }
        }
    }

    public Principal loginUser(LoginRequest req) {
        logger.info("... logging in user ...");
        User user = userDao.getUserById(req.getUsername(), req.getPassword());
        if (user == null || !user.isActive()) {
            throw new InvalidLoginException("Invalid username or password, or inactive account.");
        }

        return new Principal(user.getId(), user.getForename(), user.getRole(), user.isActive());
    }

    public List<User> getAllUsers() {
        logger.info("... getting all users ...");
        return userDao.getAllUsers();
    }

    public void activateUser(AccountActivationRequest req) {
        logger.info("... activating user ...");
        userDao.activateUser(req.getId());
    }

    public void deactivateUser(AccountDeactivationRequest req) {
        logger.info("... deactivating user ...");
        userDao.deactivateUser(req.getId());
    }

    // Helper Functions
    private boolean isGoodCandidate(User user) {
        // TODO: Assess if need to refactor to an util class

        if (user.getUsername() == null) {
            throw new InvalidRegistrationException("missing username");
        } else if (user.getEmail() == null) {
            throw new InvalidRegistrationException("missing email");
        } else if (user.getPassword() == null) {
            throw new InvalidRegistrationException("missing password");
        } else if (user.getForename() == null) {
            throw new InvalidRegistrationException("missing forename");
        } else if (user.getSurname() == null) {
            throw new InvalidRegistrationException("missing surname");
        } else if (user.getRole() == null) {
            throw new InvalidRegistrationException("missing user role");
        } else if (!isGoodPassword(user.getPassword())) {
            throw new InvalidRegistrationException("missing strong password");
        }

        return true;
    }

    private boolean isGoodPassword(String password) {
        if (password.length() < 12) {
            return false;
        }

        return true;
    }
}
