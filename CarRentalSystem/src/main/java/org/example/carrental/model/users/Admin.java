package org.example.carrental.model.users;

import org.example.carrental.model.common.Credential;
import org.example.carrental.model.enums.Role;

/**
 * Represents the system administrator
 * Only one admin account exists in the system
 */
public class Admin extends Account {
    private static final long serialVersionUID = 1L;

    private String adminLevel;

    public Admin() {
        super();
    }

    public Admin(String id, String firstName, String lastName, String email,
                 String phoneNumber, Credential credential, String adminLevel) {
        super(id, firstName, lastName, email, phoneNumber, credential, Role.ADMIN);
        this.adminLevel = adminLevel;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id='" + getId() + '\'' +
                ", name='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", adminLevel='" + adminLevel + '\'' +
                '}';
    }
}