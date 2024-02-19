package org.example;

import jakarta.persistence.*;

@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "permissionType", unique = true)
    private String permissionType;

    public Permission() {
    }

    public Permission(String permissionType) {
        this.permissionType = permissionType;
    }

    public Long getId() {
        return id;
    }

    public String getPermissionType() {
        return permissionType;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permissionType='" + permissionType + '\'' +
                '}';
    }
}
