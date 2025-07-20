package app.servarium.domain.shared.params;

import lombok.Builder;

@Builder
public record LinkDeviceParams(
        long userId,
        String keyValue,
        String aliasName
) {
}