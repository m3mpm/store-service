package org.m3mpm.storeservice.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record SupplierDto(
        UUID id,
        String name,
        String phoneNumber,
        AddressDto address,
        List<ProductDto> products
) {}
