package com.ivaaan.seen.comment.dto;

import jakarta.validation.constraints.Max;

public class CommentUploadDto {

    @Max(value = 50)
    private String text;

    public CommentUploadDto() {
    }

    public String getText() {
        return text;
    }

}
