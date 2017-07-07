package org.customers.system.service;

import org.customers.system.domain.CustomerEditor;
import org.customers.system.domain.CustomersRepository;
import org.customers.system.domain.model.Customer;
import org.customers.system.service.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class BaseCustomerEditor implements CustomerEditor {

    private final CustomersRepository repository;

    BaseCustomerEditor(CustomersRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateUsersFirstName(Customer customer, String firstName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateUsersLastName(Customer customer, String lastName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateUsersAddress(Customer customer, String address) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateUsersEmail(Customer customer, String email) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updatePassword(Customer customer, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateActive(Customer customer, String group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateCgroup(Customer customer, String group) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    @Override
    public Customer updateCustomer(Customer customer) throws CustomerNotFoundException {
        Optional<Customer> dbCustomer = repository.findByLoginAndPassword(
                customer.getLogin(), customer.getPassword());
        if(dbCustomer.isPresent()) {
            Customer foundCustomer = dbCustomer.get();
            if (customer.getFirstName() != null && !customer.getFirstName().isEmpty()) foundCustomer.setFirstName(customer.getFirstName());
            foundCustomer.setModified(LocalDate.now());
            if(customer.getAddress() != null && !customer.getAddress().isEmpty()) foundCustomer.setAddress(customer.getAddress());
            if(customer.getEmail() != null && !customer.getEmail().isEmpty()) foundCustomer.setEmail(customer.getEmail());
            if(customer.getLastName() != null && !customer.getLastName().isEmpty()) foundCustomer.setLastName(customer.getLastName());
            if(customer.getProfileImage() != null && !customer.getProfileImage().isEmpty()) foundCustomer.setProfileImage(customer.getProfileImage());
            if(customer.getCgroup() != null && !customer.getCgroup().isEmpty()) foundCustomer.setCgroup(customer.getCgroup());
            if(customer.isActive() && !foundCustomer.isActive()) foundCustomer.setActive(true);
            if(!customer.isActive() && foundCustomer.isActive()) foundCustomer.setActive(false);
            return repository.save(foundCustomer);
        } else throw new CustomerNotFoundException("User not found");

    }
}
