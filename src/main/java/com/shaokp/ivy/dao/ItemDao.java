package com.shaokp.ivy.dao;

import com.shaokp.ivy.model.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao {
    List<Item> findAll() throws SQLException;
    Item findById(Long id) throws SQLException;
    void save(Item item) throws SQLException;
    void delete(Item item) throws SQLException;
    void update(Item item) throws SQLException;

}
