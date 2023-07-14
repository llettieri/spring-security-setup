package ch.lorele.newspringtest.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequest {
    private String fName;
    private String lName;
    private String email;
    private String password;
}
