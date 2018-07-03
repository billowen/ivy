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
    public List<Story> findAll() throws Exception {
        return storyDao.findAll();
    }

    @Override
    public Story findById(Long id) throws Exception {
        return storyDao.findById(id);
    }

    @Override
    public List<Story> findByTag(String tag) throws Exception {
        return storyDao.findByTag(tag);
    }

    @Override
    public List<String> listAllTags() throws Exception {
        return storyDao.listAllTags();
    }

    @Override
    public void save(Story story) throws Exception {
        storyDao.save(story);
    }

    @Override
    public void delete(Story story) throws Exception {
        storyDao.delete(story);
    }
}
