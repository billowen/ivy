package com.shaokp.ivy.dao;

import com.shaokp.ivy.model.Story;

import java.sql.SQLException;
import java.util.List;

public interface StoryDao {
    List<Story> findAll() throws SQLException;
    Story findById(Long id) throws SQLException;
    void save(Story story) throws SQLException;
    void delete(Story story) throws SQLException;
}
