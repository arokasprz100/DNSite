package com.dnsite.comments.DTOs;

import com.dnsite.comments.model.Comment;
import com.dnsite.domain.service.DomainService;

public class CommentDTOToCommentConverter {

    public static Comment convert(CommentDTO commentFromClient, DomainService domainService) {
        Comment comment = new Comment();

        comment.setId(commentFromClient.getId());
        comment.setDomain(domainService.findById(commentFromClient.getDomainId()));
        comment.setName(commentFromClient.getName());
        comment.setType(commentFromClient.getType());
        comment.setModifiedAt(commentFromClient.getModifiedAt());
        comment.setAccount(commentFromClient.getAccount());
        comment.setComment(commentFromClient.getComment());

        return comment;
    }
}
