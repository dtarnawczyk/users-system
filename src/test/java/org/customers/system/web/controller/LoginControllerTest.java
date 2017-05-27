package org.customers.system.web.controller;

import org.customers.system.Application;
import org.customers.system.domain.Customer;
import org.customers.system.domain.CustomersRepository;
import org.customers.system.domain.CustomersService;
import org.customers.system.service.BaseCustomersService;
import org.customers.system.web.LoginController;
import org.customers.system.web.LoginForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    private MockMvc mockMvc;

    @Autowired
    private CustomersRepository repository;

    @Before
    public void setup() {
        CustomersService service = new BaseCustomersService(repository);
        controller = new LoginController(service);
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
                .andExpect(flash().attribute("error", is("User not found")))
                .andExpect(redirectedUrl(""));

        // then
        verify(service, times(1)).getCustomer(fakeCustomerLogin, fakeCustomerPassword);
        verifyNoMoreInteractions(service);
//
    }

    @Test
    public void whenTooShortLoginThenShowError() throws Exception {
        // given
        String tooShortLogin = "test";
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
    public void whenTooShortPasswordThenShowError() throws Exception {
        // given
        String fakeCustomerLogin = "testCustomer";
        String tooShortPassword = "test";
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
