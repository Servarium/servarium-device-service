package app.servarium.domain.port.input;

import app.servarium.domain.shared.result.UserDeviceItemData;

import java.util.List;

public interface GetUserDevicesPort {
    List<UserDeviceItemData> execute(long userId);
}