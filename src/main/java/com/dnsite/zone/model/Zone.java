package com.dnsite.zone.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "\"zones\"")
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@ManyToOne
    @Column(name="domain_id")
    private Long domain; //TODO add Domain instead of Long, @NotNull

    //TODO add owner

    @Column(name="comment")
    private String comment;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDomain() {
        return domain;
    }

    public void setDomain(Long domain) {
        this.domain = domain;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
