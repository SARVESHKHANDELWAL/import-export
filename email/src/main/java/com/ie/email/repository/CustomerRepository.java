package com.ie.email.repository;

import com.ie.email.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    ResponseEntity<Customer> findById(int id);
}
