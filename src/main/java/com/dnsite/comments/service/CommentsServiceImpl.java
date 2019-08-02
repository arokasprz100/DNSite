package com.dnsite.comments.service;

import com.dnsite.comments.model.Comments;
import com.dnsite.comments.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CommentsServiceImpl implements  CommentsService{

    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public void saveInBatch(List<Comments> comments) { commentsRepository.saveAll(comments); }

    @Override
    public void deleteInBatch(List<Comments> comments) { commentsRepository.deleteInBatch(comments); }

    @Override
    public List<Comments> findAll() {
        return commentsRepository.findAll();
    }

    @Override
    public Comments findById(Long id){
        Optional<Comments> res = commentsRepository.findById(id);
        return res.isPresent()?res.get():null;}

}
