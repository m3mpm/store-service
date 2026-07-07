package org.m3mpm.storeservice.dto;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record AddressResponseDto(
        UUID id,
        String country,
        String city,
        String street
) { }
