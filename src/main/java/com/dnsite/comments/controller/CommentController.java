package com.dnsite.comments.controller;

import com.dnsite.comments.DTOs.CommentDTO;
import com.dnsite.comments.DTOs.CommentDTOToCommentConverter;
import com.dnsite.comments.model.Comment;
import com.dnsite.comments.service.CommentsService;
import com.dnsite.domain.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private DomainService domainService;

    @GetMapping
    @RequestMapping("/all")
    @ResponseBody
    public List<CommentDTO> findAll(){

        List<Comment> comments = commentsService.findAll();
        List<CommentDTO> commentsToClient = new ArrayList<>();
        for(Comment comment : comments) {
            commentsToClient.add(new CommentDTO(comment));
        }
        return commentsToClient;
    }

    @GetMapping
    @RequestMapping("/{domainId}")
    @ResponseBody
    public List<CommentDTO> getRecordsFromGivenDomain(@PathVariable("domainId") Long domainId) {

        List<Comment> comments = commentsService.findByDomain_Id(domainId);
        List<CommentDTO> commentsToClient = new ArrayList<>();
        for(Comment comment : comments) {
            commentsToClient.add(new CommentDTO(comment));
        }
        return commentsToClient;
    }


    @PostMapping
    @RequestMapping("/commit")
    @ResponseBody
    public String commitChanges(@RequestBody List<CommentDTO> commentsFromClient){
        List<Comment> comments = new ArrayList<>();
        for (CommentDTO commentFromClient : commentsFromClient) {
            comments.add(CommentDTOToCommentConverter.convert(commentFromClient, domainService));
        }
        commentsService.saveInBatch(comments);
        return "Comment saved";
    }


    @PostMapping
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteComments(@RequestBody List<Comment> comments){
        commentsService.deleteInBatch(comments);
        return "Comment deleted";
    }

}
