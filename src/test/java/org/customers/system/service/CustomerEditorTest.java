package org.customers.system.service;

import org.customers.system.domain.CustomerEditor;
import org.customers.system.domain.CustomersService;
import org.customers.system.domain.model.Customer;
import org.customers.system.domain.model.CustomerFactory;
import org.customers.system.service.exception.CustomerNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerEditorTest {

    @Autowired
    private CustomerEditor editorServive;

    @Autowired
    private CustomersService customersService;

    @Autowired
    private TestEntityManager entityManager;

    private Customer testCustomer;

    @Before
    public void createTestUser() {
        testCustomer = new CustomerFactory()
                .setLogin("test111")
                .setFirstName("John")
                .setLastName("Doe")
                .setActive(true)
                .setEmail("email@server.com")
                .setPassword("test123")
                .setProfileImage("image")
                .setCgroup("admins")
                .createCustomer();
        entityManager.persist(testCustomer);
    }

    @After
    public void removeTestUser() {
        entityManager.remove(testCustomer);
    }

    @Test
    public void shouldUpdateCustomer() throws CustomerNotFoundException {

        // given
        Customer editedCustomer = new Customer();
        editedCustomer.setLogin(testCustomer.getLogin());
        editedCustomer.setPassword(testCustomer.getPassword());
        editedCustomer.setFirstName("Tom");
        editedCustomer.setLastName("Underwood");
        editedCustomer.setActive(false);
        editedCustomer.setEmail("newmail@test.com");
        editedCustomer.setProfileImage("someImage2");
        editedCustomer.setCgroup("commons");

        //when
        Customer afterEditCustomer = editorServive.updateCustomer(editedCustomer);

        // then
        assertThat(afterEditCustomer.getFirstName()).isEqualTo(editedCustomer.getFirstName());
        assertThat(afterEditCustomer.getLastName()).isEqualTo(editedCustomer.getLastName());
        assertThat(afterEditCustomer.isActive()).isEqualTo(editedCustomer.isActive());
        assertThat(afterEditCustomer.getEmail()).isEqualTo(editedCustomer.getEmail());
        assertThat(afterEditCustomer.getProfileImage()).isEqualTo(editedCustomer.getProfileImage());
        assertThat(afterEditCustomer.getCgroup()).isEqualTo(editedCustomer.getCgroup());
    }

    @Test(expected = CustomerNotFoundException.class)
    public void shouldThrowExceptionWhenUserNotFound() throws CustomerNotFoundException{
        // given
        Customer editedCustomer = new Customer();
        editedCustomer.setLogin("notexistins");
        editedCustomer.setPassword("notexistinpasswd");

        //when
        editorServive.updateCustomer(editedCustomer);
    }
}
