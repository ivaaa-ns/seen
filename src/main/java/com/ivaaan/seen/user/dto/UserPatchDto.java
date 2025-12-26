package com.ivaaan.seen.user.dto;

import jakarta.validation.constraints.Size;

public class UserPatchDto {

    @Size(min = 1, max = 50, message = "user.name.size")

    private String name;

    public UserPatchDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
