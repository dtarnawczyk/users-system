package org.customers.system.web.controllers.ui.profileForm;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProfileSession extends ProfileFormDto implements Serializable {

    public void saveProfile(ProfileFormDto profileForm){
        this.login = profileForm.getLogin();
        this.password = profileForm.getPassword();
        this.street = profileForm.getStreet();
        this.zipcode = profileForm.getZipcode();
        this.city = profileForm.getCity();
        this.email = profileForm.getEmail();
        this.firstName = profileForm.getFirstName();
        this.lastName = profileForm.getLastName();
        this.profileImage = profileForm.getProfileImage();
    }

    public ProfileFormDto restoreProfile() {
        ProfileFormDto profileForm = new ProfileFormDto();
        profileForm.setLogin(this.login);
        profileForm.setPassword(this.password);
        profileForm.setEmail(this.email);
        profileForm.setStreet(this.street);
        profileForm.setZipcode(this.zipcode);
        profileForm.setCity(this.city);
        profileForm.setFirstName(this.firstName);
        profileForm.setLastName(this.lastName);
        profileForm.setProfileImage(this.profileImage);
        return profileForm;
    }

    public boolean isProfileAvailable() {
         return ((this.login != null && this.password != null) &&
                 !(this.login.isEmpty() && this.password.isEmpty()));
    }
}
