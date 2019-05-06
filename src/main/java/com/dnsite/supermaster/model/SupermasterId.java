package com.dnsite.supermaster.model;

import com.vladmihalcea.hibernate.type.basic.Inet;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@TypeDef(
        name = "ipv4",
        typeClass = PostgreSQLInetType.class,
        defaultForType = Inet.class
)
public class SupermasterId implements Serializable {

    @Column(
            name = "ip",
            columnDefinition = "inet"
    )
    private Inet ip;

    @Column(name = "nameserver")
    private String nameserver;

    public SupermasterId() {}

    public SupermasterId(Inet ip, String nameserver) {
        this.ip = ip;
        this.nameserver = nameserver;
    }

    public Inet getIp() {
        return ip;
    }

    public void setIp(Inet ip) {
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
