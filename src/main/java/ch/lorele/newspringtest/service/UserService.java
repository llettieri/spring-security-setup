package ch.lorele.newspringtest.service;

import ch.lorele.newspringtest.model.entity.User;
import ch.lorele.newspringtest.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * This service is used for the user stuff
 */
public interface UserService {
    /**
     * UserDetailsService is used for the security context
     *
     * @return userDetailsService
     */
    UserDetailsService userDetailsService();

    /**
     * Create a new user
     *
     * @param user
     * @return user
     */
    User createUser(User user);

    /**
     * Fetch a user by email
     *
     * @param email
     * @return user
     */
    User fetchByEmail(String email);

    /**
     * Fetch a user by id
     *
     * @param id
     * @return user
     */
    User fetchById(Long id);

    /**
     * Fetch all users
     *
     * @return users
     */
    List<User> fetchAllUsers();

    User updateUser(Long id, UserDto userDto);
}
