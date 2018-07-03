package com.shaokp.ivy.dao;

import com.shaokp.ivy.model.Story;

import java.sql.SQLException;
import java.util.List;

public interface StoryDao {
    List<Story> findAll() throws Exception;
    Story findById(Long id) throws Exception;
    List<Story> findByTag(String tag) throws Exception;
    List<String> listAllTags() throws Exception;
    void save(Story story) throws Exception;
    void delete(Story story) throws Exception;
}
