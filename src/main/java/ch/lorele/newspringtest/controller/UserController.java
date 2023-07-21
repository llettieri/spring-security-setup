package ch.lorele.newspringtest.controller;

import ch.lorele.newspringtest.model.dto.DetailUserDto;
import ch.lorele.newspringtest.model.dto.UpdateUserDto;
import ch.lorele.newspringtest.model.entity.User;
import ch.lorele.newspringtest.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/users")
@Slf4j
@AllArgsConstructor
@Secured("ADMIN")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = this.userService.fetchAllUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/me")
    @Secured("USER")
    public ResponseEntity<DetailUserDto> me() {
        return ResponseEntity.ok(this.userService.getAuthenticatedUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        User user = this.userService.fetchById(id);

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody UpdateUserDto updateUserDto) {
        if (updateUserDto.getPassword() != null) {
            updateUserDto.setPassword(this.passwordEncoder.encode(updateUserDto.getPassword()));
        }

        User user = this.userService.updateUser(id, updateUserDto);

        return ResponseEntity.ok(user);
    }
}
