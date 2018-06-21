package com.shaokp.ivy.model;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Item {
    private Long id;
    private byte[] bytes;   // for pictures;
    private String description;
    private LocalDateTime dateUploaded = LocalDateTime.now();

    public Item() {}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(LocalDateTime dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", bytes=" + Arrays.toString(bytes) +
                ", description='" + description + '\'' +
                ", dateUploaded=" + dateUploaded +
                '}';
    }
}
