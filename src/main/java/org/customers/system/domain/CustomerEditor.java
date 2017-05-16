package org.customers.system.domain;

public interface CustomerEditor {

    void changeUsersFirstName(Customer customer, String firstName);

    void changeUsersLastName(Customer customer, String lastName);

    void changeUsersAddress(Customer customer, String address);

    void changeUsersEmail(Customer customer, String email);

    void changePassword(Customer customer, String password);

    void changeGroup(Customer customer, String group);
}
