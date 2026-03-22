package org.m3mpm.storeservice.service;

import org.m3mpm.storeservice.dto.AddressDto;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    AddressDto save(AddressDto addressDto);

    List<AddressDto> getAll();

    AddressDto get(UUID id);

    void delete(UUID id);

    AddressDto update(UUID id, AddressDto addressDto);

}
