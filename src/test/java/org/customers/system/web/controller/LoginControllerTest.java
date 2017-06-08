package org.customers.system.web.controller;

import org.customers.system.Application;
import org.customers.system.domain.Customer;
import org.customers.system.domain.CustomersService;
import org.customers.system.web.controlers.login.LoginController;
import org.customers.system.web.controlers.login.LoginForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class LoginControllerTest {

    private LoginController controller;

    @MockBean
    private CustomersService service;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        controller = new LoginController(service, messageSource);
    }

    @Test
    public void testLoginView(){
        // when
        String view = controller.homePage(new LoginForm(){});
        // then
        assertEquals("login", view);
    }

    @Test
    public void whenCustomerLoginAndPasswordProvidedThenRedirectToLoggedPage() throws Exception{
        // given
        String fakeCustomerLogin = "customer";
        String fakeCustomerPassword = "customerPassword123";
        when(service.getCustomer(fakeCustomerLogin, fakeCustomerPassword)).thenReturn(new Customer());

        // when
        mockMvc.perform(
                post("/login")
                .param("login", fakeCustomerLogin)
                .param("password", fakeCustomerPassword))
                .andDo(print())
                .andExpect(flash().attributeExists("profileForm"))
                .andExpect(redirectedUrl("logged"));

        // then
        verify(service, times(1)).getCustomer(fakeCustomerLogin, fakeCustomerPassword);
        verifyNoMoreInteractions(service);

    }

    @Test
    public void whenUserDoesntExistShowError () throws Exception {
        // given
        String fakeCustomerLogin = "customer";
        String fakeCustomerPassword = "customer1234";
        when(service.getCustomer(fakeCustomerLogin, fakeCustomerPassword)).thenReturn(null);

        // when
        mockMvc.perform(
                post("/login")
                        .param("login", fakeCustomerLogin)
                        .param("password", fakeCustomerPassword))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("error", is("Customer not found")))
                .andExpect(redirectedUrl(""));

        // then
        verify(service, times(1)).getCustomer(fakeCustomerLogin, fakeCustomerPassword);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void whenLoginEmptyThenShowError() throws Exception {
        // given
        String tooShortLogin = "";
        String fakeCustomerPassword = "test1234";
        when(service.getCustomer(tooShortLogin, fakeCustomerPassword)).thenReturn(new Customer());

        // when
        mockMvc.perform(
                post("/login")
                        .param("login", tooShortLogin)
                        .param("password", fakeCustomerPassword))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("loginForm", "login"))
                .andExpect(view().name("login"));

        // then
        verifyNoMoreInteractions(service);
    }

    @Test
    public void whenPasswordEmptyThenShowError() throws Exception {
        // given
        String fakeCustomerLogin = "testCustomer";
        String tooShortPassword = "";
        when(service.getCustomer(fakeCustomerLogin, tooShortPassword)).thenReturn(new Customer());

        // when
        mockMvc.perform(
                post("/login")
                        .param("login", fakeCustomerLogin)
                        .param("password", tooShortPassword))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("loginForm", "password"))
                .andExpect(view().name("login"));

        // then
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testLoggedView() {
        // when
        String view = controller.logged();
        // then
        assertEquals("logged", view);
    }

}
