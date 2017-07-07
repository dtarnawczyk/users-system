package org.customers.system.repository;

import org.customers.system.domain.CustomersRepository;
import org.customers.system.domain.model.Customer;
import org.customers.system.domain.model.CustomerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomersRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomersRepository repository;

    private Customer testCustomer;
    private static final String TEST_USER = "testUser";
    private static final String TEST_PASSWORD = "password4321";

    @Before
    public void createTestUser(){
        testCustomer = new CustomerFactory()
                .setLogin(TEST_USER)
                .setFirstName("John")
                .setLastName("Doe")
                .setActive(true)
                .setEmail("email@server.com")
                .setPassword(TEST_PASSWORD)
                .createCustomer();
    }

    @After
    public void destroyTestUser(){
        repository.delete(testCustomer);
    }

    @Test
    public void whenUserInRepositoryThenShouldFindUser() {
        // given
        this.entityManager.persist(testCustomer);

        // when
        Optional<Customer> searchedCustomer = this.repository.findByLoginAndPassword(TEST_USER, TEST_PASSWORD);

        // then
        assertThat(searchedCustomer.get().getLogin()).isEqualTo(testCustomer.getLogin());
        assertThat(searchedCustomer.get().getEmail()).isEqualTo(testCustomer.getEmail());
        assertThat(searchedCustomer.get().getPassword()).isEqualTo(testCustomer.getPassword());

    }

    @Test
    public void shouldNotReturnAnythingWHenUserDoesntExist() {
        // given
        this.entityManager.persist(testCustomer);

        // when
        Optional<Customer> searchedCustomer = this.repository.findByLoginAndPassword("notExist", TEST_PASSWORD);

        // then
        assertFalse(searchedCustomer.isPresent());

    }
}
