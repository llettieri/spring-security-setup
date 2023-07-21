package ch.lorele.newspringtest.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Data
@Builder
public class RefreshToken {
    @Id
    private String name;
    @NonNull
    private String userId;
    @Indexed(expireAfterSeconds = 604800)
    private Instant createdAt;
}
