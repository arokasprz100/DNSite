package com.dnsite.utils.hibernate;

public class DbConfig {
    private String username;

    private String password;

    private String hostname;

    private Long dbPort;

    private String database;


    private String hostmaster;

    private String primaryNameServer;

    private String secondNameServer;

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
