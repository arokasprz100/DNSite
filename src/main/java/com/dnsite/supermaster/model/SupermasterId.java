package com.dnsite.supermaster.model;

import com.dnsite.utils.custom_constraints.ip_address.IpAddress;
import com.vladmihalcea.hibernate.type.basic.Inet;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
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
    @NotNull
    @IpAddress
    private Inet ip;

    @Column(name = "nameserver")
    @NotNull
    private String nameserver;

    public Inet getIp() {
        return ip;
    }

    public void setIp(String address) {
        this.ip = new Inet(address);
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
