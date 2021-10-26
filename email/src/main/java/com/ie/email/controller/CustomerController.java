package com.ie.email.controller;


import com.ie.email.model.Customer;
import com.ie.email.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
       return customerRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomersById(@PathVariable("id") int id){
        return customerRepository.findById(id);
    }

    @PostMapping("/customers")
    public List<Customer> createTutorial(@RequestBody Customer customer) {
        customerRepository.save(customer);
        
        return customerRepository.findAll();
    }

}
