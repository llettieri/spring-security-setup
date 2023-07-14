package ch.lorele.newspringtest.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInRequest {
    private String email;
    private String password;
}
