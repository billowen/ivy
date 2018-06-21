package com.shaokp.ivy.dao.impl;

import com.shaokp.ivy.model.Item;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ItemDaoImplTest {

    private static ItemDaoImpl itemDao;

    @BeforeClass
    public static void setup() {
        DataSource ds = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("test-data.sql")
                .build();
        itemDao = new ItemDaoImpl();
        itemDao.setDataSource(ds);
    }

    @Test
    public void test_findAll() throws SQLException {
        List<Item> items = itemDao.findAll();
        System.out.println(items);
        assertThat(items, hasSize(1));
    }

    @Test
    public void test_findById() throws SQLException {
        Item item = itemDao.findById(1L);
        assertThat(item.getId(), is(1L));
    }

    @Test
    public void test_save() throws SQLException {
        Item item = new Item();
        item.setDescription("test2");
        itemDao.save(item);
        List<Item> items = itemDao.findAll();
        System.out.println(items);
    }

    @Test
    public void test_update() throws SQLException {
        Item item = new Item();
        item.setId(3L);
        item.setDescription("desc");
        itemDao.save(item);
        System.out.println(itemDao.findAll());
        item.setDescription("update desc");
        itemDao.update(item);
        List<Item> items = itemDao.findAll();
        System.out.println(items);
    }

    @Test
    public void test_delete() throws SQLException {
        Item item = new Item();
        item.setId(1L);
        itemDao.delete(item);
        List<Item> items = itemDao.findAll();
        System.out.println(items);
    }



}