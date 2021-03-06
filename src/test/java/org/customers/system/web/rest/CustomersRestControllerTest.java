package org.customers.system.web.rest;

import org.customers.system.Application;
import org.customers.system.domain.CustomersRepository;
import org.customers.system.domain.model.Address;
import org.customers.system.domain.model.Customer;
import org.customers.system.domain.model.Role;
import org.customers.system.web.controllers.api.ApiEndpoints;
import org.customers.system.web.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.TestCase.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomersRestControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    private static final String TEST_USER_LOGIN = "tester";
    private Customer testCustomer;

//    @Autowired
//    private Jackson2ObjectMapperBuilder jsonBuilder;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        resetCustomerInRepository(createTestCustomer(TEST_USER_LOGIN, "sample@test.org"));
    }

    @Test
    public void shoudlListCustomers() throws Exception {
        this.mockMvc.perform(get(ApiEndpoints.CUSTOMERS).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].login", is(TEST_USER_LOGIN)));
    }

    @Test
    public void shoudGetCustomerById() throws Exception {
        this.mockMvc.perform(get(String.format(ApiEndpoints.CUSTOMERS+"/%s", testCustomer.getId())).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().intValue())));
    }

    @Test
    public void shouldCreateCustomer() throws Exception {
        String newCustomersLogin = "newTester";
        String newCustomersEmail = "john@email.org";
        MvcResult result = this.mockMvc.perform(post(ApiEndpoints.CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.toJson(createTestCustomer(newCustomersLogin, newCustomersEmail))))
                .andExpect(status().isCreated()).andReturn();
        assertThat(result.getResponse().getHeader("Location")).contains(ApiEndpoints.CUSTOMERS);
        assertTrue(customersRepository.findByLogin(newCustomersLogin).isPresent());
    }

    @Test
    public void shouldUpdateCustomer() throws Exception {
        String newEmail = "newEmail@box.net";
        testCustomer.setEmail(newEmail);
        this.mockMvc.perform(put(String.format(ApiEndpoints.CUSTOMERS+"/%s", testCustomer.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.toJson(testCustomer)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", is(newEmail)));
    }

    @Test
    public void shouldDeleteCustomer() throws Exception {
        this.mockMvc.perform(delete(String.format(ApiEndpoints.CUSTOMERS+"/%s", testCustomer.getId()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertFalse(customersRepository.findById(testCustomer.getId()).isPresent());
    }

    private void resetCustomerInRepository(Customer customer){
        customersRepository.deleteAll();
        testCustomer = customersRepository.save(customer);
    }

    private Customer createTestCustomer(String login, String email){
        Customer customer = new Customer();
        customer.setLogin(login);
        customer.setPassword(passwordEncoder.encode("password"));
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail(email);
        Address address = new Address("Zamkowa", "12-345", "Sosnowiec");
        customer.setAddress(address);
        customer.setActive(true);
        customer.setCgroup("USER");
        customer.setRole(Role.USER);
        return customer;
    }


}
