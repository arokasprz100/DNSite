package com.dnsite.comments.DTOs;

import com.dnsite.comments.model.Comment;

public class CommentDTO {

    private Long id;

    private String domainInfo;

    private String name = "";

    private String type = "";

    private int modifiedAt;

    private String account = "";

    private String comment = "";

    private Long tableIndex;

    public CommentDTO() {};

    public CommentDTO(Comment originalComment) {
        id = originalComment.getId();
        domainInfo = originalComment.getDomain().getId().toString();
        name = originalComment.getName();
        type = originalComment.getType();
        modifiedAt = originalComment.getModifiedAt();
        account = originalComment.getAccount();
        comment = originalComment.getComment();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomainInfo() {
        return domainInfo;
    }

    public void setDomainInfo(String domainInfo) {
        this.domainInfo = domainInfo;
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

    public Long getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(Long tableIndex) {
        this.tableIndex = tableIndex;
    }

}
