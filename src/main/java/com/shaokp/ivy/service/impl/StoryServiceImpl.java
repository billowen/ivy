package com.shaokp.ivy.service.impl;

import com.shaokp.ivy.dao.StoryDao;
import com.shaokp.ivy.model.Story;
import com.shaokp.ivy.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class StoryServiceImpl implements StoryService {
    private StoryDao storyDao;

    @Autowired
    public void setStoryDao(StoryDao storyDao) {
        this.storyDao = storyDao;
    }


    @Override
    public List<Story> findAll() throws SQLException {
        return storyDao.findAll();
    }

    @Override
    public Story findById(Long id) throws SQLException {
        return storyDao.findById(id);
    }

    @Override
    public void save(Story story, MultipartFile file) throws SQLException, IOException {
        story.setBytes(file.getBytes());
        storyDao.save(story);
    }

    @Override
    public void delete(Story story) throws SQLException {
        storyDao.delete(story);
    }
}
