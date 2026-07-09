package org.m3mpm.storeservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.m3mpm.storeservice.dto.ClientCreateDto;
import org.m3mpm.storeservice.dto.ClientResponseDto;
import org.m3mpm.storeservice.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @PostMapping
    public ResponseEntity<ClientResponseDto> create(@Valid @RequestBody ClientCreateDto clientCreateDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(clientCreateDto));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }
}
