package com.shaokp.ivy.dao.impl;

import com.shaokp.ivy.dao.ItemDao;
import com.shaokp.ivy.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {
    private DataSource dataSource;

    private static final String QUERY_ALL = "select * from items";
    private static final String QUERY_BY_ID = "select * from items where id=?";
    private static final String INSERT_ITEM = "insert into items (bytes, description, dateuploaded) values(?, ?, now())";
    private static final String DELETE_ITEM = "delete from items where id=?";
    private static final String UPDATE_ITEM = "update items set bytes=?, description=? where id=?";
    //private static final Logger logger = LoggerFactory.getLogger(ItemDaoImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Item> findAll() throws SQLException {
        List<Item> list = new ArrayList<>();
        try(Connection conn = dataSource.getConnection()) {
            try(PreparedStatement stmt = conn.prepareStatement(QUERY_ALL)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Item i = new Item();
                    i.setId(rs.getLong("id"));
                    i.setBytes(rs.getBytes("bytes"));
                    i.setDateUploaded(rs.getTimestamp("dateUploaded").toLocalDateTime());
                    i.setDescription(rs.getString("description"));
                    list.add(i);
                }
            }
        }
        return list;
    }

    @Override
    public Item findById(Long id) throws SQLException {
        Item item = null;
        try(Connection conn = dataSource.getConnection()) {
            try(PreparedStatement stmt = conn.prepareStatement(QUERY_BY_ID)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    item = new Item();
                    item.setId(rs.getLong("id"));
                    item.setBytes(rs.getBytes("bytes"));
                    item.setDateUploaded(rs.getTimestamp("dateUploaded").toLocalDateTime());
                    item.setDescription(rs.getString("description"));
                }
            }
        }
        return item;
    }

    @Override
    public void save(Item item) throws SQLException {
        try(Connection conn = dataSource.getConnection()) {
            try(PreparedStatement stmt = conn.prepareStatement(INSERT_ITEM)) {
                stmt.setBytes(1, item.getBytes());
                stmt.setString(2, item.getDescription());
                stmt.execute();
            }
        }
    }

    @Override
    public void update(Item item) throws SQLException {
        try(Connection conn = dataSource.getConnection()) {
            try(PreparedStatement stmt = conn.prepareStatement(UPDATE_ITEM)) {
                stmt.setBytes(1, item.getBytes());
                stmt.setString(2, item.getDescription());
                stmt.setLong(3, item.getId());
                stmt.execute();
            }
        }
    }

    @Override
    public void delete(Item item) throws SQLException {
        try(Connection conn = dataSource.getConnection()) {
            try(PreparedStatement stmt = conn.prepareStatement(DELETE_ITEM)) {
                stmt.setLong(1, item.getId());
                stmt.execute();
            }
        }
    }
}
