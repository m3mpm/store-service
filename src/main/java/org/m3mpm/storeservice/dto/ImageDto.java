package org.m3mpm.storeservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.UUID;


@NoArgsConstructor
@Setter @Getter
@ToString
@Accessors(chain = true)
public class ImageDto {

    private UUID id;
    private byte[] image;
}
