package com.ivaaan.seen.comment.dto;

import java.time.LocalDateTime;

public class CommentGetDto {

    private Long id;
    private Long userId;
    private Long postId;
    private String text;
    private LocalDateTime createdAt;

    public CommentGetDto() {
    }

    

    public CommentGetDto(Long id, Long userId, Long postId, String text, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.text = text;
        this.createdAt = createdAt;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

}
