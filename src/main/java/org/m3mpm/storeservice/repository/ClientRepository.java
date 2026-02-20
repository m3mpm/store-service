package org.m3mpm.storeservice.repository;

import org.m3mpm.storeservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
