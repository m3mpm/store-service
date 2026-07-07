package org.m3mpm.storeservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder(toBuilder = true)
public record AddressPutDto(
        @NotBlank(message = "Country cannot be empty")
        @Size(max = 100, message = "Country name is too long")
        String country,

        @NotBlank(message = "City cannot be empty")
        @Size(max = 100, message = "City name is too long")
        String city,

        @NotBlank(message = "Street cannot be empty")
        @Size(max = 255, message = "Street name is too long")
        String street
) { }
