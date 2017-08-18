package org.customers.system.service;

import org.customers.system.domain.CustomerCreator;
import org.customers.system.domain.model.Customer;
import org.customers.system.domain.model.CustomerBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class CustomerCreatorTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerCreator creator;

    private Customer testCustomer;

    @Before
    public void prepareUser(){
        testCustomer = new CustomerBuilder()
                .setLogin("test111")
                .setFirstName("John")
                .setLastName("Doe")
                .setActive(true)
                .setEmail("email@server.com")
                .setPassword("test123")
                .createCustomer();
    }

    @Test
    public void whenUserCreatedThenExistsInRepository(){
        // given

        // when
        Customer createdCustomer = this.creator.create(testCustomer);

        // then
        assertNotNull(this.entityManager.find(Customer.class, createdCustomer.getId()));
    }

    @Test
    public void whenUserDeletedThenDoesNotExistInRepository() {
        // given
        Customer createdCustomer = this.entityManager.persist(testCustomer);

        // when
        this.creator.delete(createdCustomer);

        // then
        assertThat(this.entityManager.find(Customer.class, createdCustomer.getId())).isNull();
    }

}
