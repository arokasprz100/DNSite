package com.dnsite.supermaster.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SupermasterId implements Serializable {

    @Column(name = "ip")
    private String ip; // TODO: figure out how to map to INET Psql Type

    @Column(name = "nameserver")
    private String nameserver;

    public SupermasterId() {}

    public SupermasterId(String ip, String nameserver) {
        this.ip = ip;
        this.nameserver = nameserver;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNameserver() {
        return nameserver;
    }

    public void setNameserver(String nameserver) {
        this.nameserver = nameserver;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupermasterId)) return false;
        SupermasterId that = (SupermasterId) o;
        return Objects.equals(getIp(), that.getIp()) &&
                Objects.equals(getNameserver(), that.getNameserver());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIp(), getNameserver());
    }


}
