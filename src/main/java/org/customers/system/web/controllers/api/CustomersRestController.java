package org.customers.system.web.controllers.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.customers.system.domain.CustomerCreator;
import org.customers.system.domain.CustomerEditor;
import org.customers.system.domain.CustomersService;
import org.customers.system.domain.model.Customer;
import org.customers.system.service.exception.CustomerNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomersRestController {

    private final CustomersService customersService;
    private final CustomerEditor customerEditor;
    private final CustomerCreator customerCreator;

    @GetMapping(ApiEndpoints.CUSTOMERS_ID)
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        Optional<Customer> customerOptional = customersService.getCustomerById(id);
        return customerOptional.map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(ApiEndpoints.CUSTOMERS)
    public ResponseEntity<?> getAllCustomers(){
        List<Customer> users = customersService.getAll();
        if(users.isEmpty()){
            return new ResponseEntity<List<Customer>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Customer>>(users, HttpStatus.OK);
    }

    @PostMapping(ApiEndpoints.CUSTOMERS)
    public ResponseEntity<Void> createCustomer(@RequestBody Customer customer, UriComponentsBuilder ucBuilder) {
        if (customersService.isCustomerExist(customer)) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        customerCreator.create(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(ApiEndpoints.CUSTOMERS_ID).buildAndExpand(customer.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping(ApiEndpoints.CUSTOMERS_ID)
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
        try {
            Customer updatedCustomer = customerEditor.updateCustomer(customer);
            return new ResponseEntity<Customer>(updatedCustomer, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(ApiEndpoints.CUSTOMERS_ID)
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") long id) {
        Optional<Customer> customerOptional = customersService.getCustomerById(id);
        if(customerOptional.isPresent()){
            customerCreator.delete(customerOptional.get());
            return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
