package com.ivaaan.seen.post.dto;

public class PostUpdateDto {
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
