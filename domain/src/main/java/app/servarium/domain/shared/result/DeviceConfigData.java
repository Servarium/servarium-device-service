package app.servarium.domain.shared.result;

import app.servarium.domain.model.value.HardwareConfig;
import app.servarium.domain.model.value.SoftwareConfig;

public record DeviceConfigData(
        SoftwareConfig software,
        HardwareConfig hardware
) {
}