package com.everything.bigapp.repo;

import com.everything.bigapp.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepo extends CrudRepository<Customer, Long> {
}
