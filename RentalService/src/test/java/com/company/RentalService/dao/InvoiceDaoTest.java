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

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceDaoTest {
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
    public void addInvoice() {
    }

    @Test
    public void getInvoice() {
    }

    @Test
    public void getAllInvoice() {
    }

    @Test
    public void updateInvoice() {
    }

    @Test
    public void deleteInvoice() {
    }
}