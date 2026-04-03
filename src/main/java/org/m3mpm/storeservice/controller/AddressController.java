package org.m3mpm.storeservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.m3mpm.storeservice.dto.AddressDto;
import org.m3mpm.storeservice.service.AddressService;
import org.m3mpm.storeservice.validation.OnCreate;
import org.m3mpm.storeservice.validation.OnUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@Tag(name = "Адреса", description = "Управление адресами клиентов и поставщиков") // Группа в Swagger
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Создать новый адрес", description = "Позволяет сохранить адрес в базе данных")
    @ApiResponse(responseCode = "201", description = "Адрес успешно создан")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    @PostMapping
    public ResponseEntity<AddressDto> create(@Validated(OnCreate.class) @RequestBody AddressDto addressDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.create(addressDto));
    }

    @Operation(summary = "Получить все адреса")
    @GetMapping
    public ResponseEntity<List<AddressDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAll());
    }

    @Operation(summary = "Найти адрес по ID")
    @ApiResponse(responseCode = "404", description = "Адрес с таким ID не найден")
    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> findById(@PathVariable("id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findById(id));
    }

    @Operation(summary = "Полное обновление адреса (PUT)")
    @ApiResponse(responseCode = "404", description = "Адрес с таким ID не найден")
    @PutMapping("/{id}") // PUT - Полное обновление (клиент должен прислать все поля)
    public ResponseEntity<AddressDto> update(@PathVariable("id")UUID id, @Validated(OnCreate.class) @RequestBody AddressDto addressDto){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.update(id,addressDto));
    }

    @Operation(summary = "Частичное обновление адреса (PATCH)")
    @ApiResponse(responseCode = "404", description = "Адрес с таким ID не найден")
    @PatchMapping("/{id}") // PATCH - Частичное обновление (клиент может прислать только одно поле)
    public ResponseEntity<AddressDto> patch(@PathVariable UUID id, @Validated(OnUpdate.class) @RequestBody AddressDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.patch(id, dto));
    }

    @Operation(summary = "Удалить адрес")
    @ApiResponse(responseCode = "204", description = "Адрес успешно удален")
    @ApiResponse(responseCode = "404", description = "Адрес с таким ID не найден")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        addressService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
