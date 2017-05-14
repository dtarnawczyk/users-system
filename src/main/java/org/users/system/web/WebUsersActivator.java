package org.users.system.web;

import org.users.system.User;
import org.users.system.UsersActivator;

public class WebUsersActivator implements UsersActivator {

    @Override
    public void activate(User user) {

    }

    @Override
    public void deactivate(User user) {

    }

    @Override
    public boolean isActivated(User user) {
        return false;
    }

    @Override
    public boolean isDeactivate(User user) {
        return false;
    }
}
