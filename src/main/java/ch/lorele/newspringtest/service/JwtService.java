package ch.lorele.newspringtest.service;

import ch.lorele.newspringtest.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * This service is for the JWT stuff
 */
public interface JwtService {
    /**
     * Extract the email from the accessToken
     *
     * @param token accessToken
     * @return email
     */
    String extractEmail(String token);

    /**
     * Generate a new accessToken for the  user
     *
     * @param userDetails logged in user
     * @return accessToken
     */

    String generateAccessToken(UserDetails userDetails);

    /**
     * Generate a new refreshToken for the user
     *
     * @param user logged in user
     * @return refreshToken
     */
    String generateRefreshToken(User user);

    /**
     * Check if accessToken is valid
     *
     * @param token       user accessToken
     * @param userDetails logged in user
     * @return true/false
     */

    boolean isAccessTokenValid(String token, UserDetails userDetails);

    /**
     * Check if refreshToken is valid
     *
     * @param refreshToken user freshToken
     * @return true/false
     */

    boolean isRefreshTokenValid(String refreshToken);
}
