package org.m3mpm.storeservice.service;

import org.m3mpm.storeservice.dto.*;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    AddressResponseDto create(AddressCreateDto addressCreateDTO);

    List<AddressResponseDto> findAll();

    AddressResponseDto findById(UUID id);

    AddressResponseDto update(UUID id, AddressPutDto addressPutDto);

    AddressResponseDto patch(UUID id, AddressPatchDto addressPatchDto);

    void delete(UUID id);

}
