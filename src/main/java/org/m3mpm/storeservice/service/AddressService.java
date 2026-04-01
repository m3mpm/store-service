package org.m3mpm.storeservice.service;

import org.m3mpm.storeservice.dto.AddressDto;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    AddressDto create(AddressDto addressDto);

    List<AddressDto> findAll();

    AddressDto findById(UUID id);

    AddressDto update(UUID id, AddressDto addressDto);

    void delete(UUID id);

}
