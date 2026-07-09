package org.m3mpm.storeservice.service;

import org.jspecify.annotations.Nullable;
import org.m3mpm.storeservice.dto.ClientCreateDto;
import org.m3mpm.storeservice.dto.ClientPatchDto;
import org.m3mpm.storeservice.dto.ClientPutDto;
import org.m3mpm.storeservice.dto.ClientResponseDto;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    ClientResponseDto create(ClientCreateDto createDto);

    List<ClientResponseDto> findAll();
//    ClientResponseDto findById(UUID id);
//    List<ClientResponseDto> findByNameAndSurname(String name, String surname);
//    List<ClientResponseDto> findAll(Integer limit, Integer offset);
//    ClientResponseDto update(UUID id, ClientPutDto putDto);
//    ClientResponseDto patch(UUID id, ClientPatchDto patchDto);
//    void delete(UUID id);
}
