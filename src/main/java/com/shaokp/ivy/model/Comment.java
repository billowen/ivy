package com.shaokp.ivy.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {
    private Long id;
    private Long storyId;
    private String response = "";
    private String name = "";
    private String email = "";
    private LocalDateTime dateUpdate = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(storyId, comment.storyId) &&
                Objects.equals(response, comment.response) &&
                Objects.equals(name, comment.name) &&
                Objects.equals(email, comment.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, storyId, response, name, email);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", storyId=" + storyId +
                ", content='" + response + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
