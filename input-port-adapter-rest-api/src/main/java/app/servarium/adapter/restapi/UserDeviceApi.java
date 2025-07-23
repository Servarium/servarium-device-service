package app.servarium.adapter.restapi;

import app.servarium.adapter.restapi.dto.request.LinkDeviceRequest;
import app.servarium.adapter.restapi.dto.request.UpdateUserDeviceRequest;
import app.servarium.adapter.restapi.dto.response.DeviceConfigResponse;
import app.servarium.adapter.restapi.dto.response.ErrorResponse;
import app.servarium.adapter.restapi.dto.response.UserDeviceItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@Tag(
        name = "Подключенные устройства",
        description = "Операции для управления подключенными устройствами текущего пользователя"
)
@RequestMapping(value = "/api/v1/users/devices", produces = "application/json")
public interface UserDeviceApi {

    @Operation(
            summary = "Получение списка подключенных устройств",
            description = "Возвращает список всех подключенных устройств пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список устройств пользователя"),
                    @ApiResponse(responseCode = "204", description = "У пользователя нет устройств",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "d001 - Неверные данные запроса",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping
    ResponseEntity<List<UserDeviceItemResponse>> getUserDevices(
            @AuthenticationPrincipal long userId
    );

    @Operation(
            summary = "Получение конфигурации подключенного устройства",
            description = "Возвращает текущую конфигурацию устройства пользователя по их идентификаторам",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Конфигурация устройства",
                            content = @Content(schema = @Schema(implementation = DeviceConfigResponse.class))),
                    @ApiResponse(responseCode = "400", description = "d001 - Неверные данные запроса",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "d011 - Устройство не найдено \n\n" +
                                                                     "d020 - Устройство не привязано к пользователю",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{deviceId}/config")
    ResponseEntity<DeviceConfigResponse> getUserDeviceConfig(
            @AuthenticationPrincipal long userId,
            @PathVariable("deviceId") UUID deviceId
    );

    @Operation(
            summary = "Привязка устройства к пользователю",
            description = "Привязывает устройство к пользователю через ключ подключения",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Устройство успешно привязано"),
                    @ApiResponse(responseCode = "400", description = "d001 - Неверные данные запроса \n\n" +
                                                                     "d021 - Ключ подключения невалиден",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "d011 - Устройство не найдено",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "d022 - Устройство уже привязано к пользователю",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/link")
    ResponseEntity<Void> linkDeviceToUser(
            @AuthenticationPrincipal long userId,
            @RequestBody LinkDeviceRequest body
    );

    @Operation(
            summary = "Отвязка устройства от пользователя",
            description = "Отвязывает устройство от пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Устройство успешно отвязано"),
                    @ApiResponse(responseCode = "400", description = "d001 - Неверные данные запроса",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "d020 - Устройство не привязано к пользователю",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{deviceId}")
    ResponseEntity<Void> unlinkDeviceFromUser(
            @AuthenticationPrincipal long userId,
            @PathVariable("deviceId") UUID deviceId
    );

    @Operation(
            summary = "Обновление данных подключенного устройства пользователя",
            description = "Обновляет данные подключенное устройства пользователя по их идентификаторам",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные устройства успешно обновлёны"),
                    @ApiResponse(responseCode = "400", description = "d001 - Неверные данные запроса",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "d020 - Устройство не привязано к пользователю",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{deviceId}")
    ResponseEntity<Void> updateUserDevice(
            @AuthenticationPrincipal long userId,
            @PathVariable("deviceId") UUID deviceId,
            @RequestBody UpdateUserDeviceRequest body
    );
}