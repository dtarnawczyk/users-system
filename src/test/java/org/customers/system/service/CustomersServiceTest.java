package org.customers.system.service;

import org.customers.system.domain.Customer;
import org.customers.system.domain.CustomerFactory;
import org.customers.system.domain.CustomersService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomersServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomersService service;

    private Customer testCustomer;

    @Before
    public void prepareUser(){
        testCustomer = new CustomerFactory()
                .setLogin("test111")
                .setFirstName("John")
                .setLastName("Doe")
                .setActive(true)
                .setEmail("email@server.com")
                .setPassword("test123")
                .createCustomer();
    }

    @Test
    public void findGivenUser(){
        // given
        Customer createdCustomer = this.entityManager.persist(testCustomer);


        // when
        Customer searchedCustomer = this.service.getCustomer(testCustomer.getLogin(), testCustomer.getPassword());

        // then
        assertEquals(createdCustomer.getId(), searchedCustomer.getId());
    }
}
