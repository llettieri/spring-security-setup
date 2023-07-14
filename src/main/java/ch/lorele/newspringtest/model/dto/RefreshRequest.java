package ch.lorele.newspringtest.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshRequest {
    private String refreshToken;
}
