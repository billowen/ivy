package com.shaokp.ivy.dao.impl;

import com.shaokp.ivy.dao.StoryDao;
import com.shaokp.ivy.model.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StoryDaoImp implements StoryDao {
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Story> findAll() throws SQLException {
        List<Story> stories = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                String sql = "select * from stories";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Story story = new Story();
                    story.setId(rs.getLong("id"));
                    story.setTitle(rs.getString("title"));
                    story.setBytes(rs.getBytes("bytes"));
                    story.setContent(rs.getString("content"));
                    story.setDateUploaded(rs.getTimestamp("dateuploaded").toLocalDateTime());
                    stories.add(story);
                }
            }
        }
        return stories;
    }

    @Override
    public Story findById(Long id) throws SQLException {
        Story story = null;
        try (Connection conn = dataSource.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                String sql = "select * from stories where id=" + id;
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    story = new Story();
                    story.setId(rs.getLong("id"));
                    story.setTitle(rs.getString("title"));
                    story.setBytes(rs.getBytes("bytes"));
                    story.setContent(rs.getString("content"));
                    story.setDateUploaded(rs.getTimestamp("dateuploaded").toLocalDateTime());
                }
            }
        }
        return story;
    }

    @Override
    public void save(Story story) throws SQLException {
        String saveSql = "insert into stories (title, content, bytes, dateuploaded) values (?, ?, ?, now())";
        String updateSql = "update stories set title=?, content=?, bytes=?, dateuploaded=now() where id=?";
        try (Connection conn = dataSource.getConnection()) {
            if (story.getId() == null || story.getId() <= 0) {
                try (PreparedStatement stmt = conn.prepareStatement(saveSql)) {
                    stmt.setString(1, story.getTitle());
                    stmt.setString(2, story.getContent());
                    stmt.setBytes(3, story.getBytes());
                    stmt.execute();
                }
            } else {
                try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                    stmt.setString(1, story.getTitle());
                    stmt.setString(2, story.getContent());
                    stmt.setBytes(3, story.getBytes());
                    stmt.setLong(4, story.getId());
                    stmt.execute();
                }
            }
        }
    }

    @Override
    public void delete(Story story) throws SQLException {
        String sql = "delete from stories where id=?";
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, story.getId());
                stmt.execute();
            }
        }
    }
}