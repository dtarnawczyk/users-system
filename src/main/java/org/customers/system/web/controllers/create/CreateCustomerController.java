package org.customers.system.web.controllers.create;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.customers.system.domain.CustomerCreator;
import org.customers.system.domain.model.Customer;
import org.customers.system.web.controllers.profileForm.ProfileFormDto;
import org.customers.system.web.controllers.profileForm.ProfileFormValidator;
import org.customers.system.web.utils.CustomerFormBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CreateCustomerController {

    private final CustomerCreator service;
    private final ProfileFormValidator validator;

    @InitBinder("profileForm")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @GetMapping("/createForm")
    String loadCreatePage(@ModelAttribute("profileForm") ProfileFormDto profileForm){
        return "createNewCustomer";
    }

    @PostMapping("/createCustomer")
    String login(@Validated @ModelAttribute("profileForm") ProfileFormDto profileForm,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {

        if(formHasErrors(result)) {
            return "createNewCustomer";
        } else {
            Customer customer = CustomerFormBuilder.buildCustomer(profileForm);
            if(saveCustomer(customer)) {
                return "created";
            } else {
                setErrorMessage(redirectAttributes);
                return "redirect:createForm";
            }
        }
    }

    private boolean formHasErrors(BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }

    private void setErrorMessage(RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", "Error creating Customer!");
    }

    private boolean saveCustomer(Customer customer) {
        return service.create(customer) != null;
    }
}
