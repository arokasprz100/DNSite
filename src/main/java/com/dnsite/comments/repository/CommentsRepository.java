package com.dnsite.comments.repository;

import com.dnsite.comments.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByDomain_Id(Long domainId);
}
