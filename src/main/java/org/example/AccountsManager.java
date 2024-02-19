package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mindrot.jbcrypt.BCrypt;

import javax.naming.AuthenticationException;
import java.util.LinkedList;
import java.util.List;

public class AccountsManager {
    public static void registerUser(User user) {
        // Create Hibernate SessionFactory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        // Create Hibernate Session
        Session session = sessionFactory.openSession();

        // Begin Transaction
        Transaction transaction = session.beginTransaction();

        // Persist entities
        session.persist(user);

        // Commit Transaction
        transaction.commit();

        // Close Session
        session.close();

        // Close SessionFactory
        sessionFactory.close();
    }

    public static User getUser(String userName, String password) throws AuthenticationException {
        // Create Hibernate SessionFactory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        // Create Hibernate Session
        Session session = sessionFactory.openSession();

        List<User> users = session.createQuery("FROM User WHERE userName = :username", User.class)
                .setParameter("username", userName)
                .list();

        if (users.isEmpty()) {
            // Close Session and SessionFactory
            session.close();
            sessionFactory.close();

            return null;
        }
        else if (users.size() == 1) {
            if (BCrypt.checkpw(password, users.getFirst().getPasswordHash())) {
                List<Permission> permissions = new LinkedList<>(users.getFirst().getPermissions());
                List<Group> groups = new LinkedList<>();
                for (Group group : users.getFirst().getGroups()) {
                    List<Permission> groupPermissions = new LinkedList<>(group.getPermissions());
                    groups.add(new Group(groupPermissions));
                }
                User myUser = new User(users.getFirst().getUserName(), password, permissions, groups);

                // Close Session and SessionFactory
                session.close();
                sessionFactory.close();

                return myUser;
            }
            else {
                // Close Session and SessionFactory
                session.close();
                sessionFactory.close();

                throw new AuthenticationException("Wrong password provided!");
            }
        }
        else {
            // Close Session and SessionFactory
            session.close();
            sessionFactory.close();

            throw new RuntimeException("An error occurred and more than one accounts have same username!");
        }
    }

    public static void addPermission(Permission permission) {
        // Create Hibernate SessionFactory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        // Create Hibernate Session
        Session session = sessionFactory.openSession();

        // Begin Transaction
        Transaction transaction = session.beginTransaction();

        // Persist entities
        session.persist(permission);

        // Commit Transaction
        transaction.commit();

        // Close Session
        session.close();

        // Close SessionFactory
        sessionFactory.close();
    }

    public static void addGroup(Group group) {
        // Create Hibernate SessionFactory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        // Create Hibernate Session
        Session session = sessionFactory.openSession();

        // Begin Transaction
        Transaction transaction = session.beginTransaction();

        // Persist entities
        session.persist(group);

        // Commit Transaction
        transaction.commit();

        // Close Session
        session.close();

        // Close SessionFactory
        sessionFactory.close();
    }
}
