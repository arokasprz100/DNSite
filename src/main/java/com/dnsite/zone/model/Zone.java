package com.dnsite.zone.model;

import com.dnsite.domain.model.Domain;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "zones")
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Domain domain = null;

    @Column(name="owner")
    private Integer owner = 0;

    @Column(name="comment")
    private String comment;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "{id: " + this.id + ", owner: " + this.owner +", comment: " + this.comment + "domain: " + domain.toString() + "}";
    }
}
