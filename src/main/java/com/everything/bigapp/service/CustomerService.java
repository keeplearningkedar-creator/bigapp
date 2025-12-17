package com.everything.bigapp.service;

import com.everything.bigapp.model.Customer;
import com.everything.bigapp.repo.CustomersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomersRepo customersRepo;

    public List<Customer> getAllCustomers() {
        return (List<Customer>) customersRepo.findAll();
    }

    public Customer addCustomer(Customer customer) {
        return customersRepo.save(customer);
    }
}
