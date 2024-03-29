import org.example.AccountsManager;
import org.example.Group;
import org.example.Permission;
import org.example.User;
import org.junit.jupiter.api.Test;

import javax.naming.AuthenticationException;
import java.util.Arrays;
import java.util.List;

public class AccountsManagerTest {
    @Test
    public void testAddUser() {
        // Create permissions
        Permission readPaymentsPermission = new Permission("payments-read");
        Permission writeDBPermission = new Permission("db-write");

        // Create a group and associate permissions
        Group group = new Group(Arrays.asList(readPaymentsPermission, writeDBPermission));

        // Create a user and associate the group
        User user = new User("userName", "MyPassword", Arrays.asList(readPaymentsPermission, writeDBPermission), List.of(group));

        AccountsManager.createPermission(readPaymentsPermission);
        AccountsManager.createPermission(writeDBPermission);
        AccountsManager.createGroup(group);
        AccountsManager.registerUser(user);

        System.out.println("Entities saved successfully!");
    }

    @Test
    public void testGetUser() {
        User user;
        try {
            user = AccountsManager.getUser("userName", "MyPassword");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(user);
    }
}
