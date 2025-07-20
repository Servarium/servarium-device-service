package app.servarium.adapter.restapi;

import app.servarium.adapter.restapi.dto.mapper.DeviceMapper;
import app.servarium.adapter.restapi.dto.request.RegisterDeviceRequest;
import app.servarium.adapter.restapi.dto.request.UpdateDeviceConfigRequest;
import app.servarium.domain.port.input.GenerateConnectionKeyPort;
import app.servarium.domain.port.input.RegisterDevicePort;
import app.servarium.domain.port.input.UpdateDeviceConfigPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DeviceRestController implements DeviceApi {
    private final RegisterDevicePort registerDevicePort;
    private final GenerateConnectionKeyPort generateConnectionKeyPort;
    private final UpdateDeviceConfigPort updateDeviceConfigPort;

    private final DeviceMapper requestMapper;

    @Override
    public ResponseEntity<Void> registerDevice(RegisterDeviceRequest request) {
        registerDevicePort.execute(requestMapper.toDomainPayload(request));

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<String> generateKey(UUID deviceId) {
        return ResponseEntity.ok(
                generateConnectionKeyPort.execute(deviceId)
        );
    }

    @Override
    public ResponseEntity<Void> updateDeviceConfig(UUID deviceId, UpdateDeviceConfigRequest request) {
        updateDeviceConfigPort.execute(requestMapper.toDomainPayload(deviceId, request));

        return ResponseEntity.ok().build();
    }
}
