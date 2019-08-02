package com.dnsite.comments.controller;

import com.dnsite.comments.model.Comments;
import com.dnsite.comments.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public class CommentsController {

    @Autowired
    CommentsService commentsService;

    @ResponseBody
    public List<Comments> findAll(){return commentsService.findAll();}

    @ResponseBody
    public String deleteInBatch(@RequestBody List<Comments> comments){
        commentsService.deleteInBatch(comments);
        return "Comments deleted";
    }

    @ResponseBody
    public String saveInBatch(@RequestBody List<Comments> comments){
        commentsService.saveInBatch(comments);
        return "Comments saved";
    }

}
