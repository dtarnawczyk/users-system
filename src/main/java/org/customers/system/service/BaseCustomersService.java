package org.customers.system.service;

import org.customers.system.domain.Customer;
import org.customers.system.domain.CustomersRepository;
import org.customers.system.domain.CustomersService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BaseCustomersService implements CustomersService {

    private CustomersRepository repository;

    public BaseCustomersService(CustomersRepository repository){
        this.repository = repository;
    }

    @Override
    public Customer getCustomer(String login, String password) {
        return repository.findByLoginAndPassword(login, password);
    }

    @Override
    public List<Customer> getCustomersByCreationDate(LocalDate date) {
        return null;
    }

    @Override
    public List<Customer> getCustomersByModificationDate(LocalDate date) {
        return null;
    }
}
