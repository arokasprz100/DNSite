package com.dnsite.comments.service;

        import com.dnsite.comments.model.Comments;

        import java.util.List;

public interface CommentsService {

    void saveInBatch(List<Comments> comments);

    void deleteInBatch(List<Comments> comments);

    List<Comments> findAll();

    Comments findById(Long id);
}
