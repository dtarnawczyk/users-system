package org.customers.system.web.controlers.update;

import org.apache.log4j.Logger;
import org.customers.system.web.controlers.create.CreateForm;
import org.customers.system.web.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Locale;

@Controller
public class UpdateCustomerController {

    private static final Logger LOG = Logger.getLogger(UpdateCustomerController.class);

    private final StorageService storageService;
    private final MessageSource messageSource;

    @Autowired
    public UpdateCustomerController(StorageService storageService, MessageSource messageSource) {
        this.storageService = storageService;
        this.messageSource = messageSource;
    }


    @PostMapping(value = "/updateCustomer")
    public String updateCustomer(CreateForm profileForm,
                                 @RequestPart(name = "photoSelector", required = false) MultipartFile photoFile,
                                 RedirectAttributes redirectAttributes)
            throws IOException {

        storageService.store(photoFile);

        Locale current = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage("customerSuccessfullyUpdated", null, current);
        redirectAttributes.addFlashAttribute("updateMessage", localizedErrorMessage);

        // TODO: update customer on the database
        redirectAttributes.addFlashAttribute("profileForm", profileForm);

        return "redirect:logged";
    }
}
