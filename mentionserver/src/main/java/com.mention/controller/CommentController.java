package com.mention.controller;

import com.mention.dto.CommentIdRq;
import com.mention.dto.CommentRq;
import com.mention.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

  private CommentServiceImpl userCommentsService;

  @Autowired
  public CommentController(CommentServiceImpl userCommentsService) {
    this.userCommentsService = userCommentsService;
  }

  @PostMapping("/add")
  public ResponseEntity<?> addComment(@Valid @RequestBody CommentRq comment) {
    return userCommentsService.addComment(comment);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<?> deleteComment(@RequestBody CommentIdRq comment) {
    return userCommentsService.deleteComment(comment);
  }
}
