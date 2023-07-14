package ch.lorele.newspringtest.controller;

import ch.lorele.newspringtest.model.entity.Role;
import ch.lorele.newspringtest.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/roles")
@Slf4j
@AllArgsConstructor
@Secured("ADMIN")
public class RoleController {
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> addRole(@RequestBody String name) {
        Role role = this.roleService.createRole(name);
        return ResponseEntity.ok(role);
    }

    @DeleteMapping("/{role}")
    public ResponseEntity<String> deleteRole(@PathVariable String role) {
        this.roleService.deleteRole(role);
        return ResponseEntity.ok("Role '" + role + "' was deleted");
    }
}
