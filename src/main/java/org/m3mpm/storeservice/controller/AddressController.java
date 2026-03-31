package org.m3mpm.storeservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.m3mpm.storeservice.dto.AddressDto;
import org.m3mpm.storeservice.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<AddressDto> save(@Valid @RequestBody AddressDto addressDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.save(addressDto));
    }

    @GetMapping("")
    public ResponseEntity<List<AddressDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> findById(@PathVariable("id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findById(id));
    }
}
