package com.ivaaan.seen.user.dto;

public class UserOtherDto {
    private Long id;
    private String name;
    private String photo;


    public UserOtherDto(Long id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }


}
