package ua.edu.ukma.event_management_system.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration
class JwtServiceTest {

    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtService();
        jwtService.setUserDetailsService(userDetailsService);
    }

    @Test
    void testGenerateToken() {
        String username = "testUser";
        UserDetails userDetails = new User(username, "password",
                Collections.singletonList(new SimpleGrantedAuthority("USER")));

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        String token = jwtService.generateToken(username);

        assertNotNull(token, "Token should not be null");
        assertTrue(token.startsWith("ey"), "Token should start with 'ey' (JWT format)");
    }

    @Test
    void testExtractUsername() {
        String username = "testUser";
        UserDetails userDetails = new User(username, "password",
                Collections.singletonList(new SimpleGrantedAuthority("USER")));

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        String token = jwtService.generateToken(username);
        String extractedUsername = jwtService.extractUsername(token);

        assertEquals(username, extractedUsername, "Extracted username should match the original username");
    }

    @Test
    void testValidateToken() {
        String username = "testUser";
        UserDetails userDetails = new User(username, "password",
                Collections.singletonList(new SimpleGrantedAuthority("USER")));

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        String token = jwtService.generateToken(username);
        boolean isValid = jwtService.validate(token, userDetails);

        assertTrue(isValid, "Token should be valid for the given user");
    }

    @Test
    void testTokenExpiration() {
        String username = "testUser";
        UserDetails userDetails = new User(username, "password",
                Collections.singletonList(new SimpleGrantedAuthority("USER")));

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        String token = jwtService.generateToken(username);

        UserDetails userDetailsTest = new User(username.concat("1"), "password",
                Collections.singletonList(new SimpleGrantedAuthority("USER")));

        boolean isValid = jwtService.validate(token, userDetailsTest);
        assertFalse(isValid, "Token should be invalid after expiration");
    }

}
