// src/main/java/com/erpca/erpcaapp/model/LoginUser.java
package com.erpca.erpcaapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "login_users")
public class LoginUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", nullable = false, updatable = false)
    private Integer userID;

    @Column(name = "username", nullable = false, unique = true, length = 255)
    private String username;

    @Column(name = "password", length = 255)
    private String password;

    /**
     * e.g. "ADMIN", "USER", etc.
     */
    @Column(name = "role", length = 10)
    private String role;

    /**
     * e.g. "ACTIVE", "INACTIVE", etc.
     */
    @Column(name = "status", length = 10)
    private String status;

    public LoginUser() { }

    public LoginUser(Integer userID, String username, String password, String role, String status) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    // -------------------------------------
    // Getters & Setters
    // -------------------------------------

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Role might be "USER" or "ADMIN", etc.
     */
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Status might be "ACTIVE" or "INACTIVE"
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
