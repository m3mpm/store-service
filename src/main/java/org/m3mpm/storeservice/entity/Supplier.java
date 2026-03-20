package org.m3mpm.storeservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "suppliers")
@Getter @Setter
@NoArgsConstructor
@ToString(exclude = {"address","products"})
@Accessors(chain = true)
public class Supplier {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @JsonManagedReference
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products;

    // todo Понять для чего нужен этот метод
//    // Вспомогательный метод, чтобы можно было добавлять продукт
//    public void addProduct(Product product) {
//        this.products.add(product);   // Чтобы supplier.getProducts() сразу видел новый товар
//        product.setSupplier(this);    // Чтобы Hibernate понял, какой ID вставить в таблицу products
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier other)) return false;
        return Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
