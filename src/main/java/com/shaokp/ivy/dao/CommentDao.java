package com.shaokp.ivy.dao;

import com.shaokp.ivy.model.Comment;

import java.util.List;

public interface CommentDao {
    List<Comment> listByStory(Long storyId) throws Exception;
    Comment findById(Long id) throws Exception;
    void save(Comment comment) throws Exception;
    void delete(Comment comment) throws Exception;
}
