package com.shaokp.ivy.service;

import com.shaokp.ivy.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listByStory(Long storyId) throws Exception;
    void save(Comment comment) throws Exception;
    void delete(Comment comment) throws Exception;
}
