package org.users.system;

public interface UsersActivator {

    void activate(User user);

    void deactivate(User user);

    boolean isActivated(User user);

    boolean isDeactivate(User user);
}
