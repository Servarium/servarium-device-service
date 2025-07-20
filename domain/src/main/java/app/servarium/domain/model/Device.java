package app.servarium.domain.model;

import app.servarium.domain.model.value.HardwareConfig;
import app.servarium.domain.model.value.SoftwareConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Device {
    @EqualsAndHashCode.Include
    private UUID id;

    private SoftwareConfig softwareConfig;

    private HardwareConfig hardwareConfig;

    public Device updateHardwareConfig(HardwareConfig newConfig) {
        this.hardwareConfig = newConfig;
        return this;
    }

    public Device updateSoftwareConfig(SoftwareConfig newConfig) {
        this.softwareConfig = newConfig;
        return this;
    }
}
