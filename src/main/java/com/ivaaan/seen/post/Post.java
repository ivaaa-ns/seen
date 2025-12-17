package com.ivaaan.seen.post;

import com.ivaaan.seen.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User userOwner;

    // ! NOW ONLY ONE PHOTO PER POST
    @Column(nullable = false)
    private String photo;

    private String description;

    @Column(nullable = false)
    private Boolean hidden = false;

    public Post() {
    }

    public Post(Long id, User userOwner, String photo, String description, Boolean hidden) {
        this.id = id;
        this.userOwner = userOwner;
        this.photo = photo;
        this.description = description;
        this.hidden = hidden;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
