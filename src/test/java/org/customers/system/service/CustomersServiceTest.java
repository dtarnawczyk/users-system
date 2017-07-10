package org.customers.system.service;

import org.customers.system.domain.CustomersService;
import org.customers.system.domain.model.Customer;
import org.customers.system.domain.model.CustomerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomersServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomersService service;

    private Customer testCustomer;

    @Before
    public void prepareUser(){

    }

    @Test
    public void shouldFindGivenUser(){
        // given
        String rawPassword = "test123";
        testCustomer = new CustomerFactory()
                .setLogin("test111")
                .setFirstName("John")
                .setLastName("Doe")
                .setActive(true)
                .setEmail("email@server.com")
                .setPassword(passwordEncoder.encode(rawPassword))
                .createCustomer();
        Customer createdCustomer = this.entityManager.persist(testCustomer);

        // when
        Optional<Customer> searchedCustomer = this.service.getCustomer(testCustomer.getLogin(), rawPassword);

        // then
        assertEquals(createdCustomer.getId(), searchedCustomer.get().getId());
    }
}
