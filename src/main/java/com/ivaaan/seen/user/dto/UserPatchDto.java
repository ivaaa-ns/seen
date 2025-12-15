package com.ivaaan.seen.user.dto;

public class UserPatchDto {

    private Long id;
    private String name;
    private String photo;

    public UserPatchDto() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPhoto() { return photo; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhoto(String photo) { this.photo = photo; }
}
