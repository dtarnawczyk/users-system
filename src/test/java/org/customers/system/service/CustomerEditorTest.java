package org.customers.system.service;

import org.customers.system.domain.CustomerCreator;
import org.customers.system.domain.CustomerEditor;
import org.customers.system.domain.CustomersService;
import org.customers.system.domain.model.Customer;
import org.customers.system.domain.model.CustomerFactory;
import org.customers.system.domain.model.Role;
import org.customers.system.service.exception.CustomerNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class CustomerEditorTest {

    @Autowired
    private CustomerEditor editorServive;

    @Autowired
    private CustomersService customersService;

    @Autowired
    private CustomerCreator customerCreatorService;

    @Autowired
    private TestEntityManager entityManager;

    private static final String TEST_USER = "testUser";
    private static final String TEST_PASSWORD = "password4321";
    private Customer testCustomer;

    @Before
    public void createTestUser() {
        testCustomer = new CustomerFactory()
                .setLogin(TEST_USER)
                .setFirstName("John")
                .setLastName("Doe")
                .setActive(true)
                .setEmail("email@server.com")
                .setPassword(TEST_PASSWORD)
                .setProfileImage("image")
                .setCgroup("admins")
                .setRole(Role.ADMIN)
                .createCustomer();
        customerCreatorService.create(testCustomer);
    }

    @After
    public void removeTestUser() {
        customerCreatorService.delete(testCustomer);
    }

    @Test
    public void shouldUpdateCustomer() throws CustomerNotFoundException {

        // given
        Customer editedCustomer = new Customer();
        editedCustomer.setLogin(TEST_USER);
        editedCustomer.setPassword(TEST_PASSWORD);
        editedCustomer.setFirstName("Tom");
        editedCustomer.setLastName("Underwood");
        editedCustomer.setActive(false);
        editedCustomer.setEmail("newmail@test.com");
        editedCustomer.setProfileImage("someImage2");
        editedCustomer.setCgroup("commons");
        editedCustomer.setRole(Role.USER);

        //when
        Customer afterEditCustomer = editorServive.updateCustomer(editedCustomer);

        // then
        assertThat(afterEditCustomer.getFirstName()).isEqualTo(editedCustomer.getFirstName());
        assertThat(afterEditCustomer.getLastName()).isEqualTo(editedCustomer.getLastName());
        assertThat(afterEditCustomer.isActive()).isEqualTo(editedCustomer.isActive());
        assertThat(afterEditCustomer.getEmail()).isEqualTo(editedCustomer.getEmail());
        assertThat(afterEditCustomer.getProfileImage()).isEqualTo(editedCustomer.getProfileImage());
        assertThat(afterEditCustomer.getCgroup()).isEqualTo(editedCustomer.getCgroup());
        assertThat(afterEditCustomer.getRole()).isEqualTo(editedCustomer.getRole());
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
