package app.servarium.domain.shared.params;

import app.servarium.domain.model.value.HardwareConfig;
import app.servarium.domain.model.value.SoftwareConfig;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DevicePayload(
        UUID deviceId,
        SoftwareConfig softwareConfig,
        HardwareConfig hardwareConfig
) {}