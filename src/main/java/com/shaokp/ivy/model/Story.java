package com.shaokp.ivy.model;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class Story {
    private Long id;
    private String title;
    private String content;
    private byte[] bytes;
    private LocalDateTime dateUploaded;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public LocalDateTime getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(LocalDateTime dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story story = (Story) o;
        return Objects.equals(id, story.id) &&
                Objects.equals(title, story.title) &&
                Objects.equals(content, story.content) &&
                Arrays.equals(bytes, story.bytes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title, content);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }
}
