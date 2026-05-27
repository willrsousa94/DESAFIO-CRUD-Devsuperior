package com.willrsousa.crudchallenge.repositories;

import com.willrsousa.crudchallenge.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
