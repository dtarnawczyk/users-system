package org.users.system.web;

import org.users.system.User;

public class WebUser extends User {

    public static class Builder {
        private String login;
        private String firstName;
        private String lastName;
        private String address;
        private String email;
        private String password;
        private boolean active;
        private String group;

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setActive(boolean active) {
            this.active = active;
            return this;
        }

        public Builder setGroup(String group) {
            this.group = group;
            return this;
        }

        public User build(){
            return new User(login,
                    firstName,
                    lastName,
                    address,
                    email,
                    password,
                    active,
                    group);
        }
    }

    private WebUser(String login,
                String firstName,
                String lastName,
                String address,
                String email,
                String password,
                boolean active,
                String group) {
        super(login,
                firstName,
                lastName,
                address,
                email,
                password,
                active,
                group);
    }
}
