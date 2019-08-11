package com.dnsite.utils.hibernate;

public class DbConfig {
    private String username;

    private String password;

    private String hostname;

    private String dbPort;

    private String dbName;

    private String pg_dumpLocalization;

    private String backupLocalization;

    public String getBackupLocalization() { return backupLocalization; }

    public void setBackupLocalization(String backupLocalization) { this.backupLocalization = backupLocalization; }

    public String getPg_dumpLocalization() { return pg_dumpLocalization; }

    public void setPg_dumpLocalization(String pg_dumpLocalization) { this.pg_dumpLocalization = pg_dumpLocalization; }

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

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
