package com.company.RentalService.dao;

import com.company.RentalService.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDaoJdbcTemplateImpl implements ItemDao {
    private JdbcTemplate jdbcTemplate;


    @Autowired
    public ItemDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Item addItem(Item item) {
        return null;
    }

    @Override
    public Item getItem(int id) {
        return null;
    }

    @Override
    public List<Item> getAllItem() {
        return null;
    }

    @Override
    public void updateItem(Item item) {

    }

    @Override
    public void deleteItem(int id) {

    }
}
