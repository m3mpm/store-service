package org.m3mpm.storeservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder(toBuilder = true)
public record AddressCreateDto(
        @Schema(example = "Россия")
        @NotBlank(message = "Country cannot be empty")
        @Size(max = 100, message = "Country name is too long")
        String country,

        @Schema(example = "Москва")
        @NotBlank(message = "City cannot be empty")
        @Size(max = 100, message = "City name is too long")
        String city,

        @Schema(example = "Ленина, 10")
        @NotBlank(message = "Street cannot be empty")
        @Size(max = 255, message = "Street name is too long")
        String street
) { }
