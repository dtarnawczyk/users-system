package org.customers.system.web.controller;

import org.customers.system.Application;
import org.customers.system.domain.CustomerEditor;
import org.customers.system.domain.CustomersService;
import org.customers.system.domain.model.Customer;
import org.customers.system.service.StorageService;
import org.customers.system.web.config.resource.PictureProperties;
import org.customers.system.web.controllers.ui.profileForm.ProfileSession;
import org.customers.system.web.controllers.ui.update.UpdateCustomerController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class UpdateCustomerControllerTest {

    private static final String TEST_USER = "testUser";
    private static final String TEST_PASSWORD = "password4321";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @MockBean
    private StorageService storageService;

    @MockBean
    private CustomerEditor customerEditor;

    @MockBean
    private CustomersService customersService;

    @Autowired
    private ProfileSession profileSession;

    @Autowired
    private MockHttpSession session;

    @Autowired
    private PictureProperties pictureProperties;

    @Before
    public void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void shouldReturnLoggedView() throws Exception {
        // given
        ProfileSession profileSession = new ProfileSession();
        profileSession.setLogin(TEST_USER);
        profileSession.setPassword(TEST_PASSWORD);
        session.setAttribute("scopedTarget.profileSession", profileSession);
        // when
        this.mockMvc.perform(get("/logged").session(session)
                .accept(MediaType.TEXT_HTML))
        // then
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(UpdateCustomerController.PICTURE_PATH_ATTRIBUTE))
                .andExpect(view().name("logged"));
    }

    @Test
    public void profileShouldHasCustomersImage() throws Exception {
        // given
        ProfileSession profileSession = new ProfileSession();
        profileSession.setLogin(TEST_USER);
        profileSession.setPassword(TEST_PASSWORD);
        String fileName = "profile_image";
        String profileImageName = String.join("_", TEST_USER,
                String.join(".", fileName, "jpg"));
        profileSession.setProfileImage(profileImageName);
        session.setAttribute("scopedTarget.profileSession", profileSession);

        File tempFile = File.createTempFile(String.join("_", TEST_USER, fileName), "jpg");
        Resource imageResource = new FileSystemResource(tempFile);
        when(this.storageService.load(profileImageName)).thenReturn(Optional.of(imageResource));
        // when
        this.mockMvc.perform(get("/logged").session(session)
                .accept(MediaType.TEXT_HTML))
        // then
                .andExpect(status().isOk())
                .andExpect(model().attribute(UpdateCustomerController.PICTURE_PATH_ATTRIBUTE, imageResource))
                .andExpect(view().name("logged"));

        verify(this.storageService, times(1)).load(profileImageName);
    }

    @Test
    public void shouldSaveUploadedFile() throws Exception {
        //given
        MockMultipartFile multipartFile =
                new MockMultipartFile("photoSelector", "image.png",
                        "image/png", "Test file content".getBytes());

        ProfileSession profileSession = new ProfileSession();
        profileSession.setLogin(TEST_USER);
        profileSession.setPassword(TEST_PASSWORD);
        profileSession.setEmail("email@test.org");
        session.setAttribute("scopedTarget.profileSession", profileSession);

        //when
        this.mockMvc.perform(fileUpload("/update").file(multipartFile).session(session))
        // then
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("logged"));


        verify(this.storageService, times(1)).store(multipartFile, TEST_USER);
    }

    @Test
    public void shouldUpdateCustomerData() throws Exception {

        //given
        MockMultipartFile multipartFile =
                new MockMultipartFile("photoSelector", "image.png",
                        "image/png", "Test file content".getBytes());

        Customer customer = new Customer();
        customer.setLogin(TEST_USER);
        customer.setPassword(TEST_PASSWORD);
        customer.setFirstName("Tom");
        customer.setLastName("Johnson");
        customer.setEmail("tj@email.com");
        customer.setAddress("somestreet");
        when(this.customersService.getCustomer(TEST_USER, TEST_PASSWORD)).thenReturn(Optional.of(customer));

        //when
        Customer updatedCustomer = new Customer();
        updatedCustomer.setEmail("newtj@email.com");
        updatedCustomer.setAddress("newaddress");
        updatedCustomer.setFirstName("mark");
        updatedCustomer.setLastName("lastname");
        this.mockMvc.perform(fileUpload("/update")
                .file(multipartFile)
                .param("login", TEST_USER)
                .param("password", TEST_PASSWORD)
                .param("email", updatedCustomer.getEmail())
                .param("firstName", updatedCustomer.getFirstName())
                .param("lastName", updatedCustomer.getLastName())
                .param("address", updatedCustomer.getAddress()))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "logged"));

        // then
        verify(this.customerEditor, times(1)).updateCustomer(customer);
    }
}
