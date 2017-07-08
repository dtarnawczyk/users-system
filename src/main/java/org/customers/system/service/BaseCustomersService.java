package org.customers.system.service;

import lombok.RequiredArgsConstructor;
import org.customers.system.domain.CustomersRepository;
import org.customers.system.domain.CustomersService;
import org.customers.system.domain.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BaseCustomersService implements CustomersService {

    private final CustomersRepository repository;

    @Override
    public Optional<Customer> getCustomer(String login, String password) {
        return repository.findByLoginAndPassword(login, password);
    }

    @Override
    @Transactional(readOnly = false)
    public List<Customer> getAll() {
        List<Customer> customers = StreamSupport.stream(
                repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
         return customers == null ? Collections.emptyList() : customers;
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
