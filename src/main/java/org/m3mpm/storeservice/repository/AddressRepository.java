package org.m3mpm.storeservice.repository;

import org.m3mpm.storeservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
