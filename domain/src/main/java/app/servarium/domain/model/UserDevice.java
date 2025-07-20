package app.servarium.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UserDevice {
    private Long userId;

    private UUID deviceId;

    private String aliasName;
}
