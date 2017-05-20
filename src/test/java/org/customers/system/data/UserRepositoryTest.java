package org.customers.system.data;

import org.customers.system.domain.Customer;
import org.customers.system.domain.CustomersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomersRepository repository;

    @Test
    public void whenUserExistsThenShouldFind() {
        // given
        Customer customer = new Customer();
        customer.setLogin("test");
        customer.setEmail("test@test.com");
        customer.setPassword("123test");
        this.entityManager.persist(customer);

        // when
        Customer searchedCustomer = this.repository.findByLogin("test");

        // then
        assertThat(searchedCustomer.getLogin()).isEqualTo(customer.getLogin());
        assertThat(searchedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(searchedCustomer.getPassword()).isEqualTo(customer.getPassword());

    }
}
