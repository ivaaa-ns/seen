package com.ivaaan.seen.user.dto;

public class UserMeDto {
    private Long id;
    private String name;
    private String email;

    private String photo;

    public UserMeDto(Long id, String name, String email, String photo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }
}
