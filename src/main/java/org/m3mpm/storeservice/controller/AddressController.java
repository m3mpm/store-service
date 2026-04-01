package org.m3mpm.storeservice.controller;

import jakarta.validation.Valid;
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
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> create(@Validated(OnCreate.class) @RequestBody AddressDto addressDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.create(addressDto));
    }

    @GetMapping("")
    public ResponseEntity<List<AddressDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> findById(@PathVariable("id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findById(id));
    }

    @PutMapping("/{id}") // PUT - Полное обновление (клиент должен прислать все поля)
    public ResponseEntity<AddressDto> update(@PathVariable("id")UUID id, @Valid @RequestBody AddressDto addressDto){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.update(id,addressDto));
    }

    @PatchMapping("/{id}") // PATCH - Частичное обновление (клиент может прислать только одно поле)
    public ResponseEntity<AddressDto> patch(@PathVariable UUID id, @Validated(OnUpdate.class) @RequestBody AddressDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.patch(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        addressService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
