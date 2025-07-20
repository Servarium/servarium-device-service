package app.servarium.domain.model.value;

import lombok.Builder;

@Builder
public record SoftwareConfig(
        OSType osType,
        String osVersion
) {
}