package com.shaokp.ivy.dao.impl;

import com.shaokp.ivy.dao.CommentDao;
import com.shaokp.ivy.model.Comment;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class CommentDaoImplTest {

    private CommentDaoImpl commentDao;

    @Before
    public void setup() {
        DataSource ds = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("test-data.sql")
                .build();
        commentDao = new CommentDaoImpl();
        commentDao.setDataSource(ds);
    }

    @Test
    public void test_listByStory() throws Exception {
        List<Comment> comments = commentDao.listByStory(1L);
        assertThat(comments, hasSize(2));
    }

    @Test
    public void test_save() throws Exception {
        Comment comment = new Comment();
        comment.setStoryId(1L);
        commentDao.save(comment);
    }

    @Test
    public void test_update() throws Exception {
        Comment comment = new Comment();
        comment.setId(1L);
        commentDao.save(comment);
    }

    @Test
    public void test_delete() throws Exception {
        Comment comment = new Comment();
        comment.setId(1L);
        commentDao.delete(comment);
    }

}