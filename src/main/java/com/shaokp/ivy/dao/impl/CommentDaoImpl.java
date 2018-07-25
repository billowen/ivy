package com.shaokp.ivy.dao.impl;

import com.shaokp.ivy.dao.CommentDao;
import com.shaokp.ivy.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Comment> listByStory(Long storyId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                String sql = "select * from comments where story_id=" + storyId;
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getLong("id"));
                    comment.setStoryId(rs.getLong("story_id"));
                    comment.setResponse(rs.getString("response"));
                    comment.setDateUpdate(rs.getTimestamp("date_update").toLocalDateTime());
                    comment.setName(rs.getString("name"));
                    comment.setEmail(rs.getString("email"));
                    comments.add(comment);
                }
            }
        }
        return comments;
    }

    @Override
    public Comment findById(Long id) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                String sql = "select * from comments where id=" + id;
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getLong("id"));
                    comment.setStoryId(rs.getLong("story_id"));
                    comment.setResponse(rs.getString("response"));
                    comment.setDateUpdate(rs.getTimestamp("date_update").toLocalDateTime());
                    comment.setName(rs.getString("name"));
                    comment.setEmail(rs.getString("email"));
                    return comment;
                }
            }
        }
        return null;
    }

    @Override
    public void save(Comment comment) throws SQLException {
        String saveSql = "insert into comments (story_id, response, name, email, date_update) values (?, ?, ?, ?, now())";
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(saveSql)) {
                stmt.setLong(1, comment.getStoryId());
                stmt.setString(2, comment.getResponse());
                stmt.setString(3, comment.getName());
                stmt.setString(4, comment.getEmail());
                stmt.execute();
            }
        }
    }

    @Override
    public void delete(Comment comment) throws SQLException {
        String sql = "delete from comments where id=?";
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, comment.getId());
                stmt.execute();
            }
        }
    }
}
