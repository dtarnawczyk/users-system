package org.customers.system.web.controller;

import org.customers.system.Application;
import org.customers.system.domain.CustomersService;
import org.customers.system.domain.model.Customer;
import org.customers.system.web.controllers.profileForm.ProfileSession;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Ignore
@Deprecated
public class LoginControllerTest {

    @MockBean
    private CustomersService service;

    @Autowired
    private ProfileSession profileSession;

    @Autowired
    private MockHttpSession session;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private static final String TEST_USER = "testUser";
    private static final String TEST_PASSWORD = "password4321";

    @Before
    public void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void shouldReturnHomePageWhenProfileSessionIsUnavailable() throws Exception {
        session.setAttribute("scopedTarget.profileSession", new ProfileSession());
        this.mockMvc.perform(get("/").session(session)
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void shouldRedirectWhenProfileSessionExists() throws Exception {
        ProfileSession profileSession = new ProfileSession();
        profileSession.setLogin(TEST_USER);
        profileSession.setPassword(TEST_PASSWORD);
        session.setAttribute("scopedTarget.profileSession", profileSession);

        this.mockMvc.perform(get("/").session(session)
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/logged"));
    }

    @Test
    public void whenCustomerLoginAndPasswordProvidedThenRedirectToLoggedPage() throws Exception{
        // given
        when(service.getCustomer(TEST_USER, TEST_PASSWORD)).thenReturn(Optional.of(new Customer()));

        // when
        mockMvc.perform(
                post("/login")
                .param("login", TEST_USER)
                .param("password", TEST_PASSWORD).accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(redirectedUrl("/logged"));

        // then
        verify(service, times(1)).getCustomer(TEST_USER, TEST_PASSWORD);
        verifyNoMoreInteractions(service);

    }

    @Test
    public void whenUserDoesntExistShowError () throws Exception {
        // given
        when(service.getCustomer(TEST_USER, TEST_PASSWORD)).thenReturn(Optional.empty());

        // when
        mockMvc.perform(
                post("/login")
                        .param("login", TEST_USER)
                        .param("password", TEST_PASSWORD).accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
         // then
                .andExpect(flash().attribute("error", is("Customer not found")))
                .andExpect(redirectedUrl(""));
    }

    @Test
    public void whenLoginEmptyThenShowError() throws Exception {
        String tooShortLogin = "";
        when(service.getCustomer("", TEST_PASSWORD)).thenReturn(Optional.empty());
        mockMvc.perform(
                post("/login")
                        .param("login", tooShortLogin)
                        .param("password", TEST_PASSWORD).accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("error", is("Customer not found")))
                .andExpect(redirectedUrl(""));
    }

    @Test
    public void whenPasswordEmptyThenShowError() throws Exception {
        String tooShortPassword = "";
        when(service.getCustomer(TEST_USER, tooShortPassword)).thenReturn(Optional.empty());

        mockMvc.perform(
                post("/login")
                        .param("login", TEST_USER)
                        .param("password", tooShortPassword).accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("error", is("Customer not found")))
                .andExpect(redirectedUrl(""));
    }


}
