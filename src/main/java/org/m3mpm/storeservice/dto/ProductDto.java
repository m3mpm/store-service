package org.m3mpm.storeservice.dto;

import lombok.Builder;
import org.m3mpm.storeservice.type.ProductCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record ProductDto(
        UUID id,
        String name,
        ProductCategory category,
        BigDecimal price,
        Integer availableStock,
        LocalDateTime lastUpdateDate,
        UUID supplierId,
        UUID imageId
) { }
