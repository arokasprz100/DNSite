package com.dnsite.comments.DTOs;

import com.dnsite.comments.model.Comment;
import com.dnsite.domain.service.DomainService;
import com.dnsite.utils.DTOs.DomainIdExtractor;

public class CommentDTOToCommentConverter {

    public static Comment convert(CommentDTO commentFromClient, DomainService domainService) {
        Comment comment = new Comment();

        comment.setId(commentFromClient.getId());
        comment.setDomain(domainService.findById(DomainIdExtractor.extract(commentFromClient.getDomainInfo())));
        comment.setName(commentFromClient.getName());
        comment.setType(commentFromClient.getType());
        comment.setModifiedAt(commentFromClient.getModifiedAt());
        comment.setAccount(commentFromClient.getAccount());
        comment.setComment(commentFromClient.getComment());

        return comment;
    }
}
