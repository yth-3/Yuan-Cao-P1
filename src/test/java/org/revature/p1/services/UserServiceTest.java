package org.revature.p1.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.revature.p1.daos.UserDao;
import org.revature.p1.dtos.requests.AccountActivationRequest;
import org.revature.p1.dtos.requests.AccountDeactivationRequest;
import org.revature.p1.dtos.requests.LoginRequest;
import org.revature.p1.dtos.requests.RegistrationRequest;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.models.User;
import org.revature.p1.utils.enums.ClientUserType;
import org.revature.p1.utils.exceptions.DuplicateUserException;
import org.revature.p1.utils.exceptions.InvalidLoginException;
import org.revature.p1.utils.exceptions.InvalidRegistrationException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.revature.p1.utils.enums.ClientUserType.EMPLOYEE;

public class UserServiceTest {
    private final UserDao mockUserDao = Mockito.mock(UserDao.class);
    private UserService sut;

    private String validUserName = "jdoe";
    private String validPassword = "i$_aV3ryGo0D_psw0rD!";
    private String validEmail = "jdoe@company.inc";
    private String validForename = "John";
    private String validSurname = "Doe";
    private ClientUserType validType = EMPLOYEE;

    private RegistrationRequest regReqStub;
    private LoginRequest loginReqStub;
    private User userStub;

    @Before
    public void init() {
        sut = new UserService(mockUserDao);
        regReqStub = new RegistrationRequest(
                validUserName,
                validPassword,
                validEmail,
                validForename,
                validSurname,
                validType
        );

        loginReqStub = new LoginRequest(validUserName, validPassword);

        userStub = new User();
        userStub.setUsername(validUserName);
        userStub.setPassword(validPassword);
        userStub.setEmail(validEmail);
        userStub.setForename(validForename);
        userStub.setSurname(validSurname);
        userStub.setRole(validType);
        userStub.setActive(true);
    }

    @Test
    public void test_registerUser_validRegistration() {
        try {
            doNothing().when(mockUserDao).createUser(userStub);
            sut.registerUser(regReqStub);
            assertEquals(true, true);
        } catch (SQLException e) {
            fail("Should not throw error message when creating a valid user.");
        }
    }

    @Test
    public void test_registerUser_badPassword() {
        try {
            doNothing().when(mockUserDao).createUser(userStub);
            regReqStub.setPassword("password");
            assertThrows(InvalidRegistrationException.class, () -> {
                sut.registerUser(regReqStub);
            });
        } catch (SQLException e) {
            fail("Should not throw error message when creating a valid user.");
        }
    }

    @Test
    public void test_registerUser_missingValue() {
        try {
            doNothing().when(mockUserDao).createUser(userStub);
            regReqStub.setSurname(null);
            assertThrows(InvalidRegistrationException.class, () -> {
                sut.registerUser(regReqStub);
            });
        } catch (SQLException e) {
            fail("Should not throw error message when creating a valid user.");
        }
    }

    @Test
    public void test_registerUser_duplicateUser() {
        try {
            doThrow(SQLException.class).when(mockUserDao).createUser(any(User.class));
            assertThrows(DuplicateUserException.class, () -> {
                sut.registerUser(regReqStub);
            });
        } catch (Exception e) {
            fail("Only DuplicateUserException should be thrown.");
        }
    }

    @Test
    public void test_loginUser_validLogin() {
        Mockito.when(mockUserDao.getUserById(validUserName, validPassword)).thenReturn(userStub);
        Principal p = sut.loginUser(loginReqStub);
        assertEquals(p.getForename(), validForename);
        assertEquals(p.getType(), validType);
    }

    @Test
    public void test_loginUser_invalidLoginInactiveAccount() {
        userStub.setActive(false);
        Mockito.when(mockUserDao.getUserById(validUserName, validPassword)).thenReturn(userStub);
        assertThrows(InvalidLoginException.class, () -> {
            Principal p = sut.loginUser(loginReqStub);
        });
    }

    @Test
    public void test_loginUser_invalidLoginNonExistentAccount() {
        Mockito.when(mockUserDao.getUserById(validUserName, validPassword)).thenReturn(null);
        assertThrows(InvalidLoginException.class, () -> {
            Principal p = sut.loginUser(loginReqStub);
        });
    }

    @Test
    public void test_getAllUsers_validGet() {
        List<User> list = new ArrayList<>();
        list.add(userStub);
        Mockito.when(mockUserDao.getAllUsers()).thenReturn(list);
        List<User> testList = sut.getAllUsers();
        assertEquals(list, testList);
    }

    @Test
    public void test_activateUser_validActivation() {
        AccountActivationRequest req = new AccountActivationRequest("userID");
        doNothing().when(mockUserDao).activateUser(req.getId());
        sut.activateUser(req);
        assertEquals(true, true);
    }

    @Test
    public void test_deactivateUser_validDeactivation() {
        AccountDeactivationRequest req = new AccountDeactivationRequest("userID");
        doNothing().when(mockUserDao).deactivateUser(req.getId());
        sut.deactivateUser(req);
        assertEquals(true, true);
    }
}
