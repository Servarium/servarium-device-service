package app.servarium.domain.shared.params;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UpdateUserDeviceParams(
        long userId,
        UUID deviceId,
        String newAliasName
) {
}
