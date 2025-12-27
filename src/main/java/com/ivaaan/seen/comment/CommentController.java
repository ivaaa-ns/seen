package com.ivaaan.seen.comment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ivaaan.seen.comment.dto.CommentEditDto;
import com.ivaaan.seen.comment.dto.CommentGetDto;
import com.ivaaan.seen.comment.dto.CommentUploadDto;

import jakarta.validation.Valid;

@RestController
public class CommentController {

    private final CommentService commentService;

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/users/{userId}/comments")
    public List<CommentGetDto> getCommentsByUserId(
            Authentication authentication,
            @PathVariable Long userId) {

        Long userIdRequest = (Long) authentication.getPrincipal();
        log.info("GET /users/{}/comments by user {}", userId, userIdRequest);

        return commentService.getCommentsByUserId(userId, userIdRequest);
    }

    @GetMapping("/users/me/comments")
    public List<CommentGetDto> getCommentsMe(Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();
        log.info("GET /users/me/comments by user {}", userId);

        return commentService.getCommentsMe(userId);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentGetDto> getCommentsByPostId(
            Authentication authentication,
            @PathVariable Long postId) {

        Long userId = (Long) authentication.getPrincipal();
        log.info("GET /posts/{}/comments by user {}", postId, userId);

        return commentService.getCommentsByPostId(postId, userId);
    }

    @PostMapping("/posts/{postId}/comments")
    public List<CommentGetDto> uploadComment(
            Authentication authentication,
            @PathVariable Long postId,
            @Valid @RequestBody CommentUploadDto dto) {

        Long userId = (Long) authentication.getPrincipal();
        log.info("POST /posts/{}/comments by user {}", postId, userId);

        return commentService.uploadComment(postId, userId, dto);
    }

    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public List<CommentGetDto> editComment(
            Authentication authentication,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentEditDto dto) {

        Long userId = (Long) authentication.getPrincipal();
        log.info(
                "PATCH /posts/{}/comments/{} by user {}",
                postId, commentId, userId);

        return commentService.editComment(postId, commentId, userId, dto);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public List<CommentGetDto> deleteComment(
            Authentication authentication,
            @PathVariable Long postId,
            @PathVariable Long commentId) {

        Long userId = (Long) authentication.getPrincipal();
        log.info(
                "DELETE /posts/{}/comments/{} by user {}",
                postId, commentId, userId);

        return commentService.deleteComment(postId, commentId, userId);
    }
}
