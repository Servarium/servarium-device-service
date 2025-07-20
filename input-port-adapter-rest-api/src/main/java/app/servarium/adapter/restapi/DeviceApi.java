package app.servarium.adapter.restapi;

import app.servarium.adapter.restapi.dto.request.RegisterDeviceRequest;
import app.servarium.adapter.restapi.dto.request.UpdateDeviceConfigRequest;
import app.servarium.adapter.restapi.dto.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@Tag(name = "Устройства", description = "Операции для управления устройствами")
@RequestMapping(value = "/api/v1/devices", produces = "application/json")
public interface DeviceApi {

    @Operation(
            summary = "Регистрация нового устройства",
            description = "Создаёт новое устройство с указанными данными",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Устройство зарегистрировано в системе"),
                    @ApiResponse(responseCode = "400", description = "d001 - Неверные данные запроса",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "d010 - Устройство уже существует",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PostMapping
    ResponseEntity<Void> registerDevice(@RequestBody @Valid RegisterDeviceRequest body);

    @Operation(
            summary = "Генерация ключа подключения для устройства",
            description = "Генерирует новый ключ подключения для устройства по его идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Сгенерированный ключ подключения"),
                    @ApiResponse(responseCode = "400", description = "d001 - Неверные данные запроса",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "d011 - Устройство не найдено",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping(path = "/{deviceId}/key")
    ResponseEntity<String> generateKey(@PathVariable("deviceId") UUID deviceId);

    @Operation(
            summary = "Обновление конфигурации устройства",
            description = "Обновляет конфигурацию устройства по его идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Конфигурация устройства обновлена"),
                    @ApiResponse(responseCode = "400", description = "d001 - Неверные данные запроса",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "d011 - Устройство не найдено",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PatchMapping(path = "/{deviceId}/config")
    ResponseEntity<Void> updateDeviceConfig(
            @PathVariable("deviceId") UUID deviceId,
            @RequestBody @Valid UpdateDeviceConfigRequest body
    );
}
