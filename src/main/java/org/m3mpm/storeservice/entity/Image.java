package org.m3mpm.storeservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "images")
@Getter @Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Image {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(name = "id")
    private UUID id;

    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image other)) return false;
        return getId() != null && Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
