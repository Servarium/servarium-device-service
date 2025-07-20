package app.servarium.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ConnectionKey {
    @EqualsAndHashCode.Include
    private final String value;

    private final UUID deviceId;

    public static ConnectionKey generateFor(UUID deviceId) {
        return new ConnectionKey(UUID.randomUUID().toString(), deviceId);
    }
}
