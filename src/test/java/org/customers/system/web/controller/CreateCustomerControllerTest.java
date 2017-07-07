package org.customers.system.web.controller;

import org.customers.system.Application;
import org.customers.system.domain.CustomerCreator;
import org.customers.system.domain.model.Customer;
import org.customers.system.web.controllers.create.CreateCustomerController;
import org.customers.system.web.controllers.profileForm.ProfileForm;
import org.customers.system.web.utils.CustomerFormBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CreateCustomerControllerTest {

    private CreateCustomerController controller;

    @MockBean
    private CustomerCreator service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenCreateFormProvidedThenCreateConsumer() throws Exception {
        // given
        ProfileForm form = buildTestFormWithEmailAndFirstName("test@test.pl", "tester");
        Customer customer = CustomerFormBuilder.buildCustomer(form);
        when(service.createCustomer(customer)).thenReturn(customer);

        // when
        mockMvc.perform(post("/createCustomer").accept(MediaType.TEXT_HTML)
                .param("login", form.getLogin())
                .param("password", form.getPassword())
                .param("email", form.getEmail())
                .param("firstName", form.getFirstName())
                .param("lastName", form.getLastName())
                .param("address", form.getAddress()))
                .andExpect(status().isOk())
                .andExpect(view().name("created"));

        // then
        verify(service, times(1)).createCustomer(customer);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void whenWrongEmailAndAndTooShortPasswordThenError() throws Exception {
        // when
        mockMvc.perform(post("/createCustomer").accept(MediaType.TEXT_HTML)
                .param("login", "tester")
                .param("password", "short")
                .param("email", "wrong.pl")
                .param("firstName", "")
                .param("lastName", "test")
                .param("address", "address"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("profileForm", "email", "password"))
                .andExpect(view().name("createNewCustomer"));

        // then
        verifyNoMoreInteractions(service);
    }

    private ProfileForm buildTestFormWithEmailAndFirstName(String email, String firstName) {
        ProfileForm form = new ProfileForm();
        form.setLogin("testCustomer");
        form.setPassword("customerPass");
        form.setEmail(email);
        form.setFirstName(firstName);
        form.setLastName("Smith");
        form.setAddress("Poland");
        return form;
    }


}
