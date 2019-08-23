package com.dnsite.comments.model;

import com.dnsite.domain.model.Domain;
import com.dnsite.utils.custom_constraints.letter_case.LetterCase;
import com.dnsite.utils.custom_constraints.letter_case.LetterCaseMode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "domain")
    @NotNull
    @JsonIgnore
    private Domain domain = null;

    @Column(name = "name")
    @NotNull
    @LetterCase(LetterCaseMode.LOWER)
    private String name;

    @Column(name = "type")
    @NotNull
    private String type;

    @Column(name = "modified_at")
    @NotNull
    private int modifiedAt;

    @Column(name = "account")
    private String account = null;

    @Column(name = "comment")
    @NotNull
    private String comment = "";

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(int modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
