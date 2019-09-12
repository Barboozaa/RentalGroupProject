package com.company.RentalService.controller;

import com.company.RentalService.dao.ItemDaoJdbcTemplateImpl;
import com.company.RentalService.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ItemController {
    @Autowired
    private ItemDaoJdbcTemplateImpl itemDaoJdbcTemplate;

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public Item createItem(@RequestBody @Valid Item item) {
        return itemDaoJdbcTemplate.addItem(item);
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public Item findItem(@PathVariable(name="id") int id) {
        return itemDaoJdbcTemplate.getItem(id);
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public List<Item> findAllItems() {
        return itemDaoJdbcTemplate.getAllItem();
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.DELETE)
    public void deleteItem(@PathVariable(name="id") int id) {
        itemDaoJdbcTemplate.deleteItem(id);
    }

    @RequestMapping(value = "/item", method = RequestMethod.PUT)
    public void updateItem(@RequestBody Item item) {
        itemDaoJdbcTemplate.updateItem(item);
    }
}
