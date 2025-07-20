package app.servarium.domain.shared.result;

import app.servarium.domain.model.UserDevice;
import app.servarium.domain.model.value.SoftwareConfig;

import java.util.UUID;

public record UserDeviceItemData(
        UUID deviceId,
        String aliasName,
        SoftwareConfig softwareConfig,
        boolean isActive
) {
    public static UserDeviceItemData from(UserDevice userDevice, SoftwareConfig software, boolean isActive) {
        return new UserDeviceItemData(
                userDevice.getDeviceId(),
                userDevice.getAliasName(),
                software,
                isActive
        );
    }
}