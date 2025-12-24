package com.ivaaan.seen.user.dto;

public class UserNewDto {

    private String name;
    private String email;
    private String password;


    public UserNewDto() {}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
