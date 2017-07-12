package org.customers.system.web.controller;

import org.customers.system.Application;
import org.customers.system.domain.CustomerCreator;
import org.customers.system.domain.model.Customer;
import org.customers.system.web.controllers.create.CreateCustomerController;
import org.customers.system.web.controllers.profileForm.ProfileFormDto;
import org.customers.system.web.utils.CustomerFormBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CreateCustomerControllerTest {

    private CreateCustomerController controller;

    @MockBean
    private CustomerCreator createService;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void whenCreateFormProvidedThenCreateConsumer() throws Exception {
        // given
        ProfileFormDto form = buildTestFormWithEmailAndFirstName("test@test.pl", "tester");
        Customer customer = CustomerFormBuilder.buildCustomer(form);
        when(createService.create(customer)).thenReturn(customer);

        // when
        mockMvc.perform(post("/createCustomer").accept(MediaType.TEXT_HTML)
                .param("login", form.getLogin())
                .param("password", form.getPassword())
                .param("passwordRepeated", form.getPasswordRepeated())
                .param("email", form.getEmail())
                .param("firstName", form.getFirstName())
                .param("lastName", form.getLastName())
                .param("address", form.getAddress()))
                .andExpect(status().isOk())
                .andExpect(view().name("created"));

        // then
        verify(createService, atLeastOnce()).create(customer);
        verifyNoMoreInteractions(createService);
    }

    @Test
    public void whenWrongEmailAndTooShortPasswordThenError() throws Exception {
        // when
        mockMvc.perform(post("/createCustomer").accept(MediaType.TEXT_HTML)
                .param("login", "tester")
                .param("password", "short")
                .param("passwordRepeated", "short")
                .param("email", "wrong.pl")
                .param("firstName", "")
                .param("lastName", "test")
                .param("address", "address"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("profileForm",
                        "email", "password"))
                .andExpect(view().name("createNewCustomer"));

        // then
        verifyNoMoreInteractions(createService);
    }

    @Test
    public void whenDifferentPasswordsThenError() throws Exception {
        // when
        mockMvc.perform(post("/createCustomer").accept(MediaType.TEXT_HTML)
                .param("login", "tester")
                .param("password", "password")
                .param("passwordRepeated", "differentPass")
                .param("email", "wrong.pl")
                .param("firstName", "")
                .param("lastName", "test")
                .param("address", "address"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("profileForm",
                        "passwordRepeated"))
                .andExpect(view().name("createNewCustomer"));

        // then
        verifyNoMoreInteractions(createService);
    }

    private ProfileFormDto buildTestFormWithEmailAndFirstName(String email, String firstName) {
        ProfileFormDto form = new ProfileFormDto();
        form.setLogin("testCustomer");
        String password = "customPass";
        form.setPassword(password);
        form.setPasswordRepeated(password);
        form.setEmail(email);
        form.setFirstName(firstName);
        form.setLastName("Smith");
        form.setAddress("Poland");
        return form;
    }


}
