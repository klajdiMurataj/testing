package org.example.carrental.model.users;

import org.example.carrental.model.common.Credential;
import org.example.carrental.model.enums.Role;

/**
 * Represents a worker (employee) who can manage cars
 */
public class Worker extends Account {
    private static final long serialVersionUID = 1L;

    private String employeeId;
    private String department;

    public Worker() {
        super();
    }

    public Worker(String id, String firstName, String lastName, String email,
                  String phoneNumber, Credential credential,
                  String employeeId, String department) {
        super(id, firstName, lastName, email, phoneNumber, credential, Role.WORKER);
        this.employeeId = employeeId;
        this.department = department;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id='" + getId() + '\'' +
                ", name='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}