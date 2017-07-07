package org.customers.system.service;

import org.customers.system.domain.CustomerCreator;
import org.customers.system.domain.CustomersRepository;
import org.customers.system.domain.model.Customer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BaseCustomerCreator implements CustomerCreator {

    private CustomersRepository repository;

    public BaseCustomerCreator(CustomersRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Customer createCustomer(Customer customer) {
        return repository.save(customer);
    }

    @Transactional
    @Override
    public boolean deleteCustomer(Customer customer) {
        repository.delete(customer.getId());
        return repository.findByLoginAndPassword(customer.getLogin(), customer.getPassword()) != null;
    }
}
