package org.m3mpm.storeservice.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder(toBuilder = true)
public record AddressPatchDto(
        @Pattern(regexp = "^$|\\s*\\S.*", message = "Country cannot be blank if provided")
        @Size(max = 100, message = "Country name is too long")
        String country,

        @Pattern(regexp = "^$|\\s*\\S.*", message = "City cannot be blank if provided")
        @Size(max = 100, message = "City name is too long")
        String city,

        @Pattern(regexp = "^$|\\s*\\S.*", message = "Street cannot be blank if provided")
        @Size(max = 255, message = "Street name is too long")
        String street
) { }
