package org.m3mpm.storeservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.m3mpm.storeservice.dto.AddressResponseDto;
import org.m3mpm.storeservice.dto.ClientCreateDto;
import org.m3mpm.storeservice.dto.ClientResponseDto;
import org.m3mpm.storeservice.entity.Client;
import org.m3mpm.storeservice.mapper.AddressMapper;
import org.m3mpm.storeservice.mapper.ClientMapper;
import org.m3mpm.storeservice.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImp implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final AddressMapper addressMapper;
    private final AddressService addressService;

    @Override
    @Transactional
    public ClientResponseDto create(ClientCreateDto createDto) {

        AddressResponseDto addressResponseDto = addressService.findById(createDto.addressId());

        Client client = clientMapper.toEntity(createDto);

        client.setAddress(addressMapper.toEntity(addressResponseDto));

        Client savedClient = clientRepository.saveAndFlush(client);

        return clientMapper.toDto(savedClient);
    }

    @Override
    public List<ClientResponseDto> findAll() {
        return clientMapper.toDtoList(clientRepository.findAll());
    }
}
