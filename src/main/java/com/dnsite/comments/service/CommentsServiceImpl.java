package com.dnsite.comments.service;

import com.dnsite.comments.model.Comment;
import com.dnsite.comments.repository.CommentsRepository;
import com.dnsite.record.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentsServiceImpl implements  CommentsService{

    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public void saveInBatch(List<Comment> comments) { commentsRepository.saveAll(comments); }

    @Override
    public void deleteInBatch(List<Comment> comments) { commentsRepository.deleteInBatch(comments); }

    @Override
    public List<Comment> findAll() {
        return commentsRepository.findAll();
    }

    @Override
    public List<Comment> findByDomain_Id(Long domainId) {
        return commentsRepository.findByDomain_Id(domainId);
    }

}
