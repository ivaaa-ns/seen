package com.ivaaan.seen.post.dto;

import jakarta.validation.constraints.Size;

public class PostUpdateDto {
    @Size(max = 50, message = "post.description.max_length")
    private String description;

    private Boolean hidden;

    public PostUpdateDto() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

}
