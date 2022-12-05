package org.revature.p1.services;

import org.junit.Before;
import org.junit.Test;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.services.TokenService;
import org.revature.p1.utils.JwtConfig;
import org.revature.p1.utils.enums.ClientUserType;

import static org.junit.Assert.*;
import static org.revature.p1.utils.enums.ClientUserType.EMPLOYEE;
import static org.revature.p1.utils.enums.ClientUserType.MANAGER;

public class TokenServiceTest {
    private final JwtConfig config = new JwtConfig();
    private TokenService sut;

    private String p1_id = "testID1";
    private String p1_name = "John";
    private ClientUserType p1_type1 = EMPLOYEE;
    private ClientUserType p1_type2 = MANAGER;
    private boolean p1_activity = true;

    private Principal p1;
    private String token1;
    private Principal p2;
    private String token2;

    @Before
    public void init() {
        sut = new TokenService(config);
        p1 = new Principal(p1_id, p1_name, p1_type1, p1_activity);
        p2 = new Principal(p1_id, p1_name, p1_type2, p1_activity);
        token1 = sut.generateToken(p1);
        token2 = sut.generateToken(p2);
    }

    @Test
    public void test_createsDeterministicTokens_createUniqueTokens() {
        assertNotEquals(token1, token2);
    }

    @Test
    public void test_extractInformation_extractId() {
        assertEquals(p1_id, sut.extractUserId(token1));
        assertEquals(p1_id, sut.extractUserId(token2));
    }

    @Test
    public void test_extractInformation_extractType() {
        assertEquals(p1_type1, sut.extractUserType(token1));
        assertEquals(p1_type2, sut.extractUserType(token2));
    }
}
