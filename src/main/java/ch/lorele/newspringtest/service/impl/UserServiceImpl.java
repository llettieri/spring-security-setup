package ch.lorele.newspringtest.service.impl;

import ch.lorele.newspringtest.model.entity.Role;
import ch.lorele.newspringtest.model.entity.User;
import ch.lorele.newspringtest.model.dto.UserDto;
import ch.lorele.newspringtest.repo.UserRepository;
import ch.lorele.newspringtest.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;


@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;

    @Override
    public UserDetailsService userDetailsService() {
        return email -> userRepo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User createUser(User user) {
        return this.userRepo.save(user);
    }

    @Override
    public User fetchByEmail(String email) {
        return this.userRepo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    }

    @Override
    public User fetchById(Long id) {
        return this.userRepo.findById(id).orElseGet(() -> handleMissingUser(id));
    }

    @Override
    public List<User> fetchAllUsers() {
        return this.userRepo.findAll();
    }

    @Override
    public User updateUser(Long id, UserDto userDto) {
        User user = this.userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[UserService] - User with id " + id + " doesn't exist!"));

        String newPassword = userDto.getPassword();
        String newEmail = userDto.getEmail();
        String newFName = userDto.getFName();
        String newLName = userDto.getLName();
        Set<Role> newRoles = userDto.getRoles();

        if (newPassword != null) {
            user.setPassword(newPassword);
            log.info("[UserService] - password for user {} changed", id);
        }
        if (newEmail != null) {
            user.setEmail(newEmail);
        }
        if (newFName != null) {
            user.setFName(newFName);
        }
        if (newLName != null) {
            user.setLName(newLName);
        }
        if (newRoles != null && !newRoles.isEmpty()) {
            user.addRoles(newRoles);
        }

        return this.userRepo.save(user);
    }

    private User handleMissingUser(Long id) {
        log.warn("[UserService] - User with id {} not found", id);
        return User.builder().id(id).email("Doesn't exists!").roles(Collections.emptySet()).build();
    }
}
