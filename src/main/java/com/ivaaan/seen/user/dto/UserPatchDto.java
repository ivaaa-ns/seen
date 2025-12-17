package com.ivaaan.seen.user.dto;

public class UserPatchDto {

    private Long id;
    private String name;

    public UserPatchDto() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
