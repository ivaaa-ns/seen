package com.ivaaan.seen.comment.dto;

import jakarta.validation.constraints.Max;

public class CommentEditDto {
    @Max(value = 50)
    private String text;

    public CommentEditDto() {
    }

    public String getText() {
        return text;
    }

        
}
