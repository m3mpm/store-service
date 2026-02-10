package org.m3mpm.storeservice.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@NoArgsConstructor
@Setter @Getter
@ToString
@Accessors(chain = true)
public class AddressDto {

    private UUID id;
    private String country;
    private String city;
    private String street;

}
