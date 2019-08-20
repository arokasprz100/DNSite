package com.dnsite.comments.service;

import com.dnsite.comments.model.Comment;

        import java.util.List;

public interface CommentsService {

    void saveInBatch(List<Comment> comments);

    void deleteInBatch(List<Comment> comments);

    List<Comment> findAll();

    List<Comment> findByDomain_Id(Long domainId);

}
