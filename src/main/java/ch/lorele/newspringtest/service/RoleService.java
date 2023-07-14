package ch.lorele.newspringtest.service;

import ch.lorele.newspringtest.model.entity.Role;

/**
 * This service is for the role stuff
 */
public interface RoleService {
    /**
     * Create a new Role
     * @param role
     * @return role
     */
    Role createRole(String role);

    /**
     * Delete role
     * @param role
     */
    void deleteRole(String role);
}
