package com.company.RentalService.dao;

import com.company.RentalService.dto.Customer;
import com.company.RentalService.dto.Invoice;
import com.company.RentalService.dto.InvoiceItem;
import com.company.RentalService.dto.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;


import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ItemDaoTest {
    @Autowired
    CustomerDao customerDao;
    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    InvoiceItemDao invoiceItemDao;
    @Autowired
    ItemDao itemDao;

    @Before
    public void setUp() throws Exception {
        // Clean up the test db
        // Note: Invoice Item and Invoice tables should be deleted first due to FK depencency.
        List<InvoiceItem> iaList = invoiceItemDao.getAllInvoiceItem();
        for (InvoiceItem ia : iaList) {
            invoiceItemDao.deleteInvoiceItem(ia.getInvoiceItemId());
        }

        List<Invoice> iList = invoiceDao.getAllInvoice();
        for (Invoice i : iList) {
            invoiceDao.deleteInvoice(i.getInvoiceId());
        }

        List<Item> aList = itemDao.getAllItem();
        for (Item a : aList) {
            itemDao.deleteItem(a.getItemId());
        }

        List<Customer> cList = customerDao.getAllCustomer();
        for (Customer c : cList) {
            customerDao.deleteCustomer(c.getCustomerId());
        }

    }

    @Test
    public void addGetDeleteItem() {
        Item item = new Item();
        item.setName("Bike");
        item.setDescription("2 wheels, basket in the front");
        item.setDailyRate(new BigDecimal("2.00"));
        item = itemDao.addItem(item);

        Item item1 = itemDao.getItem(item.getItemId());
        assertEquals(item, item1);

        itemDao.deleteItem(item.getItemId());
        Item item2 = itemDao.getItem(item.getItemId());
        assertNull(item2);
    }

    @Test
    public void getAllItem() {
        Item item = new Item();
        item.setName("Bike");
        item.setDescription("2 wheels, basket in the front");
        item.setDailyRate(new BigDecimal("2.00"));
        itemDao.addItem(item);
        Item item2 = new Item();
        item2.setName("Scooter");
        item2.setDescription("2 wheels, goes beep beep");
        item2.setDailyRate(new BigDecimal("5.00"));
        itemDao.addItem(item2);

        List<Item> itemList = itemDao.getAllItem();
        assertEquals(2, itemList.size());
    }

    @Test
    public void updateItem() {
        Item item = new Item();
        item.setName("Bike");
        item.setDescription("2 wheels, basket in the front");
        item.setDailyRate(new BigDecimal("2.00"));
        item = itemDao.addItem(item);
        item.setName("Scooter");
        item.setDescription("2 wheels, goes beep beep");
        item.setDailyRate(new BigDecimal("5.00"));
        itemDao.updateItem(item);

        Item item1 = itemDao.getItem(item.getItemId());
        assertEquals(item, item1);

    }

}