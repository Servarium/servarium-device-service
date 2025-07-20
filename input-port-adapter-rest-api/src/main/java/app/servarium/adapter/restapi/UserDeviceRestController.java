package app.servarium.adapter.restapi;

import app.servarium.adapter.restapi.dto.mapper.UserDeviceMapper;
import app.servarium.adapter.restapi.dto.request.LinkDeviceRequest;
import app.servarium.adapter.restapi.dto.request.UpdateUserDeviceRequest;
import app.servarium.adapter.restapi.dto.response.DeviceConfigResponse;
import app.servarium.adapter.restapi.dto.response.UserDeviceItemResponse;
import app.servarium.domain.port.input.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserDeviceRestController implements UserDeviceApi {
    private final GetUserDevicesPort getUserDevicesPort;
    private final GetUserDeviceConfigPort getUserDeviceConfigPort;
    private final LinkDeviceToUserPort linkDeviceToUserPort;
    private final UnlinkDeviceFromUserPort unlinkDeviceFromUserPort;
    private final UpdateUserDevicePort updateUserDevicePort;

    private final UserDeviceMapper mapper;

    @Override
    public ResponseEntity<List<UserDeviceItemResponse>> getUserDevices(long userId) {
        var userDevices = getUserDevicesPort.execute(userId);
        return userDevices.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(mapper.toItemList(userDevices));
    }

    @Override
    public ResponseEntity<DeviceConfigResponse> getUserDeviceConfig(long userId, UUID deviceId) {
        var userDeviceConfig = getUserDeviceConfigPort.execute(deviceId, userId);
        return ResponseEntity.ok(mapper.toConfig(userDeviceConfig));
    }

    @Override
    public ResponseEntity<Void> linkDeviceToUser(long userId, LinkDeviceRequest body) {
        linkDeviceToUserPort.execute(mapper.toDomain(userId, body));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> unlinkDeviceFromUser(long userId, UUID deviceId) {
        unlinkDeviceFromUserPort.execute(deviceId, userId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateUserDevice(long userId, UUID deviceId, UpdateUserDeviceRequest body) {
        updateUserDevicePort.execute(mapper.toDomain(userId, deviceId, body));
        return ResponseEntity.ok().build();
    }
}
