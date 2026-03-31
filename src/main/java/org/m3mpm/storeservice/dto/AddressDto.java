package org.m3mpm.storeservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@NoArgsConstructor
@Setter @Getter
@ToString
@Accessors(chain = true)
public class AddressDto {

    private UUID id;
    @NotBlank(message = "City cannot be empty")
    private String city;
    @NotBlank(message = "Country cannot be empty")
    private String country;
    @NotBlank(message = "Street cannot be empty")
    private String street;

}
