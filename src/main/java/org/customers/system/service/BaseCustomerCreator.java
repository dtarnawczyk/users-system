package org.customers.system.service;

import org.customers.system.domain.Customer;
import org.customers.system.domain.CustomerCreator;
import org.customers.system.domain.CustomersRepository;
import org.springframework.stereotype.Service;

@Service
public class BaseCustomerCreator implements CustomerCreator {

    private CustomersRepository repository;

    public BaseCustomerCreator(CustomersRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        repository.delete(customer.getId());
        return repository.findByLoginAndPassword(customer.getLogin(), customer.getPassword()) != null ? false : true;
    }
}
