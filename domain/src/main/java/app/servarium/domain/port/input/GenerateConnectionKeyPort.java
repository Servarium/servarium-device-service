package app.servarium.domain.port.input;

import java.util.UUID;

public interface GenerateConnectionKeyPort {
    String execute(UUID deviceId);
}