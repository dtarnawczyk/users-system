package org.customers.system.service;

import lombok.RequiredArgsConstructor;
import org.customers.system.domain.CustomerCreator;
import org.customers.system.domain.CustomersRepository;
import org.customers.system.domain.model.Customer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BaseCustomerCreatorService implements CustomerCreator {

    private final CustomersRepository repository;

    @Transactional
    @Override
    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    @Transactional
    @Override
    public boolean delete(Customer customer) {
        repository.delete(customer.getId());
        return repository.findOne(customer.getId()) != null;
    }
}
