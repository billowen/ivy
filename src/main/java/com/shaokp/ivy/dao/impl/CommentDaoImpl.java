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
                    comment.setContent(rs.getString("content"));
                    comment.setDateUpdate(rs.getTimestamp("date_update").toLocalDateTime());
                    comments.add(comment);
                }
            }
        }
        return comments;
    }

    @Override
    public void save(Comment comment) throws SQLException {
        String saveSql = "insert into comments (story_id, content, date_update) values (?, ?, now())";
        String updateSql = "update comments set content=?, date_update=now() where id=?";
        try (Connection conn = dataSource.getConnection()) {
            if (comment.getId() == null || comment.getId() <= 0) {
                try (PreparedStatement stmt = conn.prepareStatement(saveSql)) {
                    stmt.setLong(1, comment.getStoryId());
                    stmt.setString(2, comment.getContent());
                    stmt.execute();
                }
            } else {
                try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                    stmt.setString(1, comment.getContent());
                    stmt.setLong(2, comment.getId());
                    stmt.execute();
                }
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
