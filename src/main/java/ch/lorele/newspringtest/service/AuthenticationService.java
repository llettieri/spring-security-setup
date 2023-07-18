package ch.lorele.newspringtest.service;

import ch.lorele.newspringtest.model.dto.AuthenticationResponse;
import ch.lorele.newspringtest.model.dto.SignInRequest;
import ch.lorele.newspringtest.model.dto.SignUpRequest;

import java.util.List;

/**
 * This service is for all the auth stuff
 */
public interface AuthenticationService {
    /**
     * Register a new User
     *
     * @param request users data
     * @return accessToken, refreshToken and user
     */
    AuthenticationResponse signup(SignUpRequest request);

    /**
     * Login a user
     *
     * @param request users credentials
     * @return accessToken, refreshToken and user
     */
    AuthenticationResponse signin(SignInRequest request);

    /**
     * Refresh an access token with the refresh token
     *
     * @param refreshToken
     * @return accessToken, refreshToken and user
     */
    AuthenticationResponse tryRefresh(String refreshToken);

    /**
     * Used for external authorization in the frontend
     *
     * @return user roles
     */
    List<String> externalAuthorization();
}
