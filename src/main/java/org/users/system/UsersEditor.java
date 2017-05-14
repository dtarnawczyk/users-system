package org.users.system;

public interface UsersEditor {

    void changeUsersFirstName(User user, String firstName);

    void changeUsersLastName(User user, String lastName);

    void changeUsersAddress(User user, String address);

    void changeUsersEmail(User user, String email);

    void changePassword(User user, String password);

    void changeGroup(User user, String group);
}
