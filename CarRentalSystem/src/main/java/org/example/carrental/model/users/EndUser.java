package org.example.carrental.model.users;

import org.example.carrental.model.common.Credential;
import org.example.carrental.model.enums.Role;

/**
 * Represents a customer (end user) who can rent cars
 */
public class EndUser extends Account {
    private static final long serialVersionUID = 1L;

    private String driverLicenseNumber;
    private String address;

    public EndUser() {
        super();
    }

    public EndUser(String id, String firstName, String lastName, String email,
                   String phoneNumber, Credential credential,
                   String driverLicenseNumber, String address) {
        super(id, firstName, lastName, email, phoneNumber, credential, Role.END_USER);
        this.driverLicenseNumber = driverLicenseNumber;
        this.address = address;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }

    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "EndUser{" +
                "id='" + getId() + '\'' +
                ", name='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", license='" + driverLicenseNumber + '\'' +
                '}';
    }
}