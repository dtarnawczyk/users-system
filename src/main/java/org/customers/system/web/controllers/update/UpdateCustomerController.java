package org.customers.system.web.controllers.update;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.customers.system.domain.CustomerEditor;
import org.customers.system.domain.model.Customer;
import org.customers.system.service.StorageService;
import org.customers.system.service.exception.CustomerNotFoundException;
import org.customers.system.web.config.PictureProperties;
import org.customers.system.web.controllers.profileForm.ProfileFormDto;
import org.customers.system.web.controllers.profileForm.ProfileSession;
import org.customers.system.web.utils.CustomerFormBuilder;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Optional;

@Controller
@SessionAttributes("picturePath")
@Slf4j
public class UpdateCustomerController {

    private final StorageService storageService;
    private final MessageSource messageSource;
    private final CustomerEditor customerEditor;
    private final Resource defaultImage;
    private final ProfileSession profileSession;
    private PictureProperties pictureProperties;
    public static final String PICTURE_PATH_ATTRIBUTE = "picturePath";

    public UpdateCustomerController(StorageService storageService,
                                    MessageSource messageSource,
                                    CustomerEditor customerEditor,
                                    PictureProperties pictureProperties,
                                    ProfileSession profileSession) {
        this.storageService = storageService;
        this.messageSource = messageSource;
        this.customerEditor = customerEditor;
        this.defaultImage = pictureProperties.getDefaultPicture();
        this.profileSession = profileSession;
    }

    @ModelAttribute("profileForm")
    public ProfileFormDto getLoginForm(){
        return getProfileSession().restoreProfile();
    }

    @ModelAttribute(PICTURE_PATH_ATTRIBUTE)
    public Resource picturePath(){
        return this.defaultImage;
    }

    @GetMapping("/profileImage")
    public void getProfilePicture(@ModelAttribute(PICTURE_PATH_ATTRIBUTE) Resource picturePath,
                                  HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type",
                URLConnection.guessContentTypeFromName(picturePath.getFilename()));
        IOUtils.copy(picturePath.getInputStream(), response.getOutputStream());
    }

    @GetMapping("/logged")
    public String logged(Model model, RedirectAttributes redirectAttributes, Locale locale) {
        if(getProfileSession().isProfileAvailable()) {
            ProfileFormDto profileForm = getProfileSession().restoreProfile();
            try {
                if(profileForm.getProfileImage() != null) {
                    Optional<Resource> resourceOptional = this.storageService.load(profileForm.getProfileImage());
                    resourceOptional.ifPresent(resource -> model.addAttribute(PICTURE_PATH_ATTRIBUTE, resource));
                } else
                    model.addAttribute(PICTURE_PATH_ATTRIBUTE, this.defaultImage);
            } catch (Exception e) {
                String errorMessage = messageSource.getMessage("pictureUploadError", null, locale);
                redirectAttributes.addFlashAttribute("error", errorMessage);
                return "redirect:logged";
            }
        }
        return "logged";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @PostMapping(value = "/update")
    public String updateCustomer(@Valid @ModelAttribute("profileForm") ProfileFormDto profileForm,
                          @RequestPart(name = "photoSelector", required = false) MultipartFile photoFile,
                          RedirectAttributes redirectAttributes,
                          Locale locale) throws IOException {
        if(isImage(photoFile)){
            try {
                String profileFilename = storeProfilePhoto(photoFile, profileForm);
                profileForm.setProfileImage(profileFilename);
            } catch (Exception e) {
                String errorStoringMessage = messageSource.getMessage("pictureStoreError", null, locale);
                redirectAttributes.addFlashAttribute("error", errorStoringMessage);
            }
        }
        try {
            updateCustomer(profileForm);
            String successMessage = messageSource.getMessage("customerSuccessfullyUpdated", null, locale);
            redirectAttributes.addFlashAttribute("updateMessage", successMessage);
        } catch (CustomerNotFoundException e) {
            String customerNotFound = messageSource.getMessage("customerNotFound", null, locale);
            redirectAttributes.addFlashAttribute("error", customerNotFound);
        }
        return "redirect:logged";
    }

    private ProfileSession getProfileSession() {
        return profileSession;
    }

    private boolean isImage(MultipartFile file) {
        return file.getContentType().startsWith("image");
    }

    private void updateCustomer(ProfileFormDto profileForm) throws CustomerNotFoundException {
        Customer customer = CustomerFormBuilder.buildCustomer(profileForm);
        customerEditor.updateCustomer(customer);
        getProfileSession().saveProfile(CustomerFormBuilder.buildForm(customer));
    }

    private String storeProfilePhoto(MultipartFile photoFile, ProfileFormDto profileForm) throws Exception {
        return storageService.store(photoFile, profileForm.getLogin());
    }
}
