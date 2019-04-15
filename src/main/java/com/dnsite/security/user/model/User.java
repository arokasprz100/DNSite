package com.dnsite.security.user.model;

import com.dnsite.security.role.model.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "\"user\"")
public class User {

    private Long id;

    private String username;

    private String password;

    private String passwordConfirm;

    private Set<Role> roles;

    private String firstName;

    private String lastName;

    private Date registrationDate;

    private Date lastLoginDate;

    private String email;

    private String hostname;

    private Long dbPort;

    private String database;

    private String powerAdminPassword;

    private String powerAdminUsername;

    private String hostmaster;

    private String primaryNameServer;

    private String secondNameServer;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Long getDbPort() {
        return dbPort;
    }

    public void setDbPort(Long dbPort) {
        this.dbPort = dbPort;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getPowerAdminPassword() {
        return powerAdminPassword;
    }

    public void setPowerAdminPassword(String powerAdminPassword) {
        this.powerAdminPassword = powerAdminPassword;
    }

    public String getPowerAdminUsername() {
        return powerAdminUsername;
    }

    public void setPowerAdminUsername(String powerAdminUsername) {
        this.powerAdminUsername = powerAdminUsername;
    }

    public String getHostmaster() {
        return hostmaster;
    }

    public void setHostmaster(String hostmaster) {
        this.hostmaster = hostmaster;
    }

    public String getPrimaryNameServer() {
        return primaryNameServer;
    }

    public void setPrimaryNameServer(String primaryNameServer) {
        this.primaryNameServer = primaryNameServer;
    }

    public String getSecondNameServer() {
        return secondNameServer;
    }

    public void setSecondNameServer(String secondNameServer) {
        this.secondNameServer = secondNameServer;
    }
}