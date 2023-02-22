package com.service.customer.repositories;

import com.service.customer.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    List<CustomerEntity> findByName(String name);
}
