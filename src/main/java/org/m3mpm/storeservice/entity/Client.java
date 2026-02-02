package org.m3mpm.storeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;
import org.m3mpm.storeservice.type.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "clients")
@Getter @Setter
@NoArgsConstructor
@ToString(exclude = "address")
@Accessors(chain = true)
public class Client {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(name = "id")
    private UUID id;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_surname")
    private String clientSurname;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "registration_date", updatable = false, nullable = false)
    private LocalDateTime registrationDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Client client)) return false;
        return Objects.equals(clientName, client.getClientName()) && Objects.equals(clientSurname, client.getClientSurname()) && Objects.equals(birthday, client.getBirthday()) && gender == client.getGender() && Objects.equals(registrationDate, client.getRegistrationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientName, clientSurname, birthday, gender, registrationDate);
    }

}
