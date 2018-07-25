package com.shaokp.ivy.service.impl;

import com.shaokp.ivy.dao.CommentDao;
import com.shaokp.ivy.model.Comment;
import com.shaokp.ivy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentDao commentDao;

    @Autowired
    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public List<Comment> listByStory(Long storyId) throws Exception {
        return commentDao.listByStory(storyId);
    }

    @Override
    public Comment findById(Long id) throws Exception {
        return commentDao.findById(id);
    }

    @Override
    public void save(Comment comment) throws Exception {
        commentDao.save(comment);
    }

    @Override
    public void delete(Comment comment) throws Exception {
        commentDao.delete(comment);
    }
}
