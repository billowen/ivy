package com.shaokp.ivy.dao.impl;

import com.shaokp.ivy.model.Story;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class StoryDaoImpTest {
    private StoryDaoImp storyDao;

    @Before
    public void setup() {
        DataSource ds = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("test-data.sql")
                .build();
        storyDao = new StoryDaoImp();
        storyDao.setDataSource(ds);
    }

    @Test
    public void test_findAll() throws SQLException {
        List<Story> stories = storyDao.findAll();
        assertThat(stories, hasSize(2));
    }

    @Test
    public void test_findById() throws SQLException {
        Story story = storyDao.findById(1L);
        assertEquals(story.getTitle(), "test");
    }

    @Test
    public void test_saveNewStory() throws SQLException {
        Story story = new Story();
        story.setTitle("test_save");
        storyDao.save(story);
        List<Story> stories = storyDao.findAll();
        assertThat(stories, hasSize(3));
    }

    @Test
    public void test_updateExistedStory() throws SQLException {
        Story story = new Story();
        story.setId(1L);
        story.setTitle("test_update");
        storyDao.save(story);
        Story actual = storyDao.findById(1L);
        assertThat(actual, is(story));
    }

    @Test
    public void test_delete() throws SQLException {
        Story story = new Story();
        story.setId(1L);
        storyDao.delete(story);
        List<Story> stories = storyDao.findAll();
        assertThat(stories, hasSize(1));
    }

    @Test
    public void test_listAllTags() throws Exception {
        List<String> tags = storyDao.listAllTags();
        assertThat(tags, hasSize(2));
        assertThat(tags, hasItem("A"));
        assertThat(tags, hasItem("B"));
    }

    @Test
    public void test_findByTag() throws Exception {
        List<Story> stories = storyDao.findByTag("A");
        assertThat(stories, hasSize(1));
    }
}