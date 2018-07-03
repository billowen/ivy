package com.shaokp.ivy.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {
    private Long id;
    private Long storyId;
    private String content = "";
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(storyId, comment.storyId) &&
                Objects.equals(content, comment.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, storyId, content);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", storyId=" + storyId +
                ", content='" + content + '\'' +
                '}';
    }
}
