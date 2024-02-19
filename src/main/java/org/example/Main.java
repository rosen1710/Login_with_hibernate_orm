package org.example;

import javax.naming.AuthenticationException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user;
        try {
            user = AccountsManager.getUser("userName", "MyPassword");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(user);

        // Create permissions
        Permission readPaymentsPermission = new Permission("payments-read");
        Permission writeDBPermission = new Permission("db-write");

        // Create a group and associate permissions
        Group group = new Group(Arrays.asList(readPaymentsPermission, writeDBPermission));

        // Create a user and associate the group
        user = new User("userName", "MyPassword", Arrays.asList(readPaymentsPermission, writeDBPermission), List.of(group));

        AccountsManager.addPermission(readPaymentsPermission);
        AccountsManager.addPermission(writeDBPermission);
        AccountsManager.addGroup(group);
        AccountsManager.registerUser(user);

        System.out.println("Entities saved successfully!");
    }
}
