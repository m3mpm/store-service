package org.m3mpm.storeservice.service;

import lombok.RequiredArgsConstructor;
import org.m3mpm.storeservice.dto.AddressDto;
import org.m3mpm.storeservice.entity.Address;
import org.m3mpm.storeservice.exception.EntityNotFoundException;
import org.m3mpm.storeservice.mapper.AddressMapper;
import org.m3mpm.storeservice.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImp implements AddressService{

    private final AddressRepository repository;
    private final AddressMapper mapper;

    @Override
    @Transactional
    public AddressDto save(AddressDto addressDto) {
        Address address = mapper.toEntity(addressDto);

        Address savedAddress = repository.save(address);

        return mapper.toDto(savedAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressDto> getAll() {
        List<Address> listOfaddresses = repository.findAll();

        return mapper.toDtoList(listOfaddresses);
    }

    @Override
    @Transactional(readOnly = true)
    public AddressDto get(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Get: Address not found with id - " + id));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if(!repository.existsById(id)){
            throw new EntityNotFoundException("Delete: Address not found");
        }

        repository.deleteById(id);
    }

    @Override
    @Transactional
    public AddressDto update(UUID id, AddressDto addressDto) {
        Address address = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Update: Address not found with id - " + id));

        mapper.updateEntity(addressDto, address);

        Address updatedAddress = repository.save(address);

        return mapper.toDto(updatedAddress);
    }
}
