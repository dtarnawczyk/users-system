package org.customers.system.web.controllers.profileForm;

import lombok.RequiredArgsConstructor;
import org.customers.system.domain.CustomersService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ProfileFormValidator implements Validator {

    private final CustomersService customersService;
    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ProfileFormDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProfileFormDto profileForm = (ProfileFormDto) target;
        validatePasswords(errors, profileForm);
        validateLogin(errors, profileForm);
        validateEmail(errors, profileForm);
    }

    private void validatePasswords(Errors errors, ProfileFormDto profileForm) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty",
                "Password is empty");
        if(profileForm.getPassword().length() < 6){
            errors.rejectValue("password","password.too_short", "Password is too short (Min 6)");
        }
        if (!profileForm.getPassword().equals(profileForm.getPasswordRepeated())) {
            errors.rejectValue("passwordRepeated","password.no_match", "Passwords do not match");
        }
    }

    private void validateLogin(Errors errors, ProfileFormDto profileForm) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "login.empty",
                "Login is empty");
        if(profileForm.getLogin().length() < 6){
            errors.rejectValue("login","login.too_short", "Login is too short (Min 6)");
        }
        if (customersService.getCustomerByLogin(profileForm.getLogin()).isPresent()) {
            errors.rejectValue("login","login.exists", "User with this login already exists");
        }
    }

    private void validateEmail(Errors errors, ProfileFormDto profileForm) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty",
                "Email is empty");

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(profileForm.getEmail());
        if(!matcher.matches()){
            errors.rejectValue("email","email.no_match", "Wrong email");
        }

        if (customersService.getCustomerByEmail(profileForm.getEmail()).isPresent()) {
            errors.rejectValue("email","email.exists", "User with this email already exists");
        }
    }
}
