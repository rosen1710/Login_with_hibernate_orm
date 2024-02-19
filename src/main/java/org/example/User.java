package org.example;

import jakarta.persistence.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "userName", unique = true)
    private String userName;

    @Column(name = "passwordHash")
    private String passwordHash;

    @ManyToMany
    @JoinTable(
            name = "user_permissions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions;

    @ManyToMany
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;

    public User() {
    }

    public User(String userName, String password, List<Permission> permissions, List<Group> groups) {
        this.userName = userName;
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        this.permissions = permissions;
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", permissions=" + permissions +
                ", groups=" + groups +
                '}';
    }
}
