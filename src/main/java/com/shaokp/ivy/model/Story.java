package com.shaokp.ivy.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class Story {
    private Long id;
    private byte[] bytes;
    private LocalDateTime dateUploaded;
    private String tag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story story = (Story) o;
        return Objects.equals(id, story.id) &&
                Arrays.equals(bytes, story.bytes) &&
                Objects.equals(tag, story.tag);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, tag);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", bytes=" + Arrays.toString(bytes) +
                ", dateUploaded=" + dateUploaded +
                ", tag='" + tag +
                '}';
    }
}
