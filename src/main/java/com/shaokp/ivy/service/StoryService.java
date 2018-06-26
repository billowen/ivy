package com.shaokp.ivy.service;

import com.shaokp.ivy.model.Story;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface StoryService {
    List<Story> findAll() throws SQLException;
    Story findById(Long id) throws SQLException;
    void save(Story story, MultipartFile file) throws SQLException, IOException;
    void delete(Story story) throws SQLException;
}
