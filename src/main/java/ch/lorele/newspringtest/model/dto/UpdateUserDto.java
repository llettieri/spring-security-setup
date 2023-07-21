package ch.lorele.newspringtest.model.dto;

import ch.lorele.newspringtest.model.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UpdateUserDto {
    private String email;
    private String fName;
    private String lName;
    private String password;
    private Set<Role> roles;
}
