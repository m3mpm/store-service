package org.m3mpm.storeservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.m3mpm.storeservice.dto.*;
import org.m3mpm.storeservice.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@Tag(name = "Адреса", description = "Управление адресами клиентов и поставщиков") // Группа в Swagger
public class AddressController {

    private final AddressService service;

    @Operation(summary = "Создать новый адрес", description = "Позволяет сохранить адрес в базе данных")
    @ApiResponse(responseCode = "201", description = "Адрес успешно создан")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    @PostMapping
    public ResponseEntity<AddressResponseDto> create(@Valid @RequestBody AddressCreateDto addressCreateDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(addressCreateDTO));
    }

    @Operation(summary = "Получить все адреса")
    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @Operation(summary = "Найти адрес по ID")
    @ApiResponse(responseCode = "404", description = "Адрес с таким ID не найден")
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> findById(@PathVariable("id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @Operation(summary = "Полное обновление адреса (PUT)")
    @ApiResponse(responseCode = "404", description = "Адрес с таким ID не найден")
    @PutMapping("/{id}") // PUT - Полное обновление (клиент должен прислать все поля)
    public ResponseEntity<AddressResponseDto> update(@PathVariable("id")UUID id, @Valid @RequestBody AddressPutDto addressPutDto){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id,addressPutDto));
    }

    @Operation(summary = "Частичное обновление адреса (PATCH)")
    @ApiResponse(responseCode = "404", description = "Адрес с таким ID не найден")
    @PatchMapping("/{id}") // PATCH - Частичное обновление (клиент может прислать только одно поле)
    public ResponseEntity<AddressResponseDto> patch(@PathVariable UUID id, @Valid @RequestBody AddressPatchDto addressPatchDto){
        return ResponseEntity.status(HttpStatus.OK).body(service.patch(id, addressPatchDto));
    }

    @Operation(summary = "Удалить адрес")
    @ApiResponse(responseCode = "204", description = "Адрес успешно удален")
    @ApiResponse(responseCode = "404", description = "Адрес с таким ID не найден")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
