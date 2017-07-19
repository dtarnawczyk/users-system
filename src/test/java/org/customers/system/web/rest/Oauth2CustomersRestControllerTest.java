package org.customers.system.web.rest;

import org.customers.system.Application;
import org.customers.system.domain.CustomersRepository;
import org.customers.system.domain.model.Customer;
import org.customers.system.domain.model.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class Oauth2CustomersRestControllerTest {

    private static final String TEST_USER_LOGIN = "tester";
    private static final String TEST_USER_RAW_PASSWORD = "password";
    private Customer testCustomer;

    @Value("${oauth2.client.id}")
    private String clientId;

    @Value("${oauth2.client.secret}")
    private String clientSecret;

    @Autowired
    private Jackson2ObjectMapperBuilder jsonBuilder;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();
        resetCustomerInRepository(createTestCustomer(TEST_USER_LOGIN));
    }

    @Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/customer").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenInvalidRole_whenGetSecureRequest_thenForbidden() throws Exception {
        String accessToken = obtainAccessToken(TEST_USER_LOGIN, TEST_USER_RAW_PASSWORD);

        mockMvc.perform(delete(String.format("/api/customer/%s", testCustomer.getId()))
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenToken_whenGetSecureRequest_thenOk() throws Exception {
        String accessToken = obtainAccessToken(TEST_USER_LOGIN, TEST_USER_RAW_PASSWORD);

        mockMvc.perform(get(String.format("/api/customer/%s", testCustomer.getId()))
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().intValue())));
    }

    private void resetCustomerInRepository(Customer customer){
        customersRepository.deleteAll();
        testCustomer = customersRepository.save(customer);
    }

    private Customer createTestCustomer(String login){
        Customer customer = new Customer();
        customer.setLogin(login);
        customer.setPassword(passwordEncoder.encode("password"));
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("email@test.nl");
        customer.setAddress("Some street");
        customer.setActive(true);
        customer.setCgroup("USER");
        customer.setCreated(LocalDate.now());
        customer.setModified(LocalDate.now());
        customer.setRole(Role.USER);
        return customer;
    }

    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("username", TEST_USER_LOGIN);
        params.add("password", TEST_USER_RAW_PASSWORD);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(clientId,clientSecret))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }
}
