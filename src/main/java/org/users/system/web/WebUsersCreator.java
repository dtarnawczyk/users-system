package org.users.system.web;

import org.users.system.User;
import org.users.system.UsersCreator;

public class WebUsersCreator implements UsersCreator{
    @Override
    public boolean createUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }
}
