package com.dnsite.comments.repository;

import com.dnsite.comments.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
