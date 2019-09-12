package com.company.RentalService.dao;

import com.company.RentalService.dto.Item;

import java.util.List;

public interface ItemDao {
    Item addItem(Item item);

    Item getItem(int id);

    List<Item> getAllItem();

    void updateItem(Item item);

    void deleteItem(int id);

}
