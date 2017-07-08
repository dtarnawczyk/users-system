package org.customers.system.web.controllers.profileForm;

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
        this.address = profileForm.getAddress();
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
        profileForm.setAddress(this.address);
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
