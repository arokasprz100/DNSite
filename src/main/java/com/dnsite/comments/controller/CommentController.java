package com.dnsite.comments.controller;

import com.dnsite.comments.DTOs.CommentDTO;
import com.dnsite.comments.DTOs.CommentDTOToCommentConverter;
import com.dnsite.comments.model.Comment;
import com.dnsite.comments.service.CommentsService;
import com.dnsite.domain.service.DomainService;
import com.dnsite.utils.DTOs.ConstraintViolationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Set<ConstraintViolationDTO> commitChanges(@RequestBody List<CommentDTO> commentsFromClient){
        List<Comment> comments = new ArrayList<>();
        Set<ConstraintViolationDTO> violations = new HashSet<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        for (CommentDTO commentFromClient : commentsFromClient) {
            Comment toAdd = CommentDTOToCommentConverter.convert(commentFromClient, domainService);
            violations.addAll(ConstraintViolationDTO.ofSet(validator.validate(toAdd), commentFromClient.getTableIndex()));
            comments.add(toAdd);
        }

        if(violations.isEmpty()) {
            commentsService.saveInBatch(comments);
        }
        return violations;
    }


    @PostMapping
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteComments(@RequestBody List<Comment> comments){
        commentsService.deleteInBatch(comments);
        return "Comment deleted";
    }

}
