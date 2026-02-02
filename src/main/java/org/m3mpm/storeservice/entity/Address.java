package org.m3mpm.storeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter @Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class Address {

    @Id
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Address address)) return false;
        return Objects.equals(country, address.getCountry()) && Objects.equals(city, address.getCity()) && Objects.equals(street, address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street);
    }
}


