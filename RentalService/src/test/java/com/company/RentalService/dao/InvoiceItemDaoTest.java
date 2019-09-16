package com.company.RentalService.dao;

import com.company.RentalService.dto.Customer;
import com.company.RentalService.dto.Invoice;
import com.company.RentalService.dto.InvoiceItem;
import com.company.RentalService.dto.Item;
import org.apache.tomcat.jni.Local;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceItemDaoTest {
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
    public void addGetDeleteInvoiceItem() {

        Item item = new Item();
        item.setName("Bike");
        item.setDescription("Energy efficient vehicle");
        item.setDailyRate(new BigDecimal("12.35"));
        itemDao.addItem(item);

        Customer customer = new Customer();
        customer.setFirstName("Wyatt");
        customer.setLastName("Simpson");
        customer.setEmail("Wyatt@student.edu");
        customer.setCompany("Cognizant");
        customer.setPhone("678-404-3785");
        customerDao.addCustomer(customer);

        Invoice invoice = new Invoice();
        invoice.setCustomerId(customer.getCustomerId());
        invoice.setLateFee(new BigDecimal("35.00"));
        invoice.setPickupDate(LocalDate.of(2018,8,12));
        invoice.setOrderDate(LocalDate.of(2018,7,12));
        invoice.setReturnDate(LocalDate.of(2018,9,12));
        invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setDiscount(new BigDecimal("25.00"));
        invoiceItem.setQuantity(5);
        invoiceItem.setUnitRate(new BigDecimal("25.00"));
        invoiceItem.setItemId(item.getItemId());
        invoiceItemDao.addInvoiceItem(invoiceItem);

        InvoiceItem invoiceItem2 = invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId());

        assertEquals(invoiceItem,invoiceItem2);

        invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId());

        invoiceItem2 = invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId());

        assertNull(invoiceItem2);

    }


    @Test
    public void getAllInvoiceItem() {

        Item item = new Item();
        item.setName("Bike");
        item.setDescription("Energy efficient vehicle");
        item.setDailyRate(new BigDecimal("12.35"));
        itemDao.addItem(item);

        Customer customer = new Customer();
        customer.setFirstName("Wyatt");
        customer.setLastName("Simpson");
        customer.setEmail("Wyatt@student.edu");
        customer.setCompany("Cognizant");
        customer.setPhone("678-404-3785");
        customerDao.addCustomer(customer);

        Invoice invoice = new Invoice();
        invoice.setCustomerId(customer.getCustomerId());
        invoice.setLateFee(new BigDecimal("35.00"));
        invoice.setPickupDate(LocalDate.of(2018,8,12));
        invoice.setOrderDate(LocalDate.of(2018,7,12));
        invoice.setReturnDate(LocalDate.of(2018,9,12));
        invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setDiscount(new BigDecimal("25.00"));
        invoiceItem.setQuantity(5);
        invoiceItem.setUnitRate(new BigDecimal("25.00"));
        invoiceItem.setItemId(item.getItemId());
        invoiceItemDao.addInvoiceItem(invoiceItem);

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceId(invoice.getInvoiceId());
        invoiceItem2.setDiscount(new BigDecimal("35.00"));
        invoiceItem2.setQuantity(5);
        invoiceItem2.setUnitRate(new BigDecimal("35.00"));
        invoiceItem2.setItemId(item.getItemId());
        invoiceItemDao.addInvoiceItem(invoiceItem2);

        List<InvoiceItem> invoiceItemList = invoiceItemDao.getAllInvoiceItem();

        assertEquals(invoiceItemList.size(), 2);

    }

    @Test
    public void updateInvoiceItem() {

        Item item = new Item();
        item.setName("Bike");
        item.setDescription("Energy efficient vehicle");
        item.setDailyRate(new BigDecimal("12.35"));
        itemDao.addItem(item);

        Customer customer = new Customer();
        customer.setFirstName("Wyatt");
        customer.setLastName("Simpson");
        customer.setEmail("Wyatt@student.edu");
        customer.setCompany("Cognizant");
        customer.setPhone("678-404-3785");
        customerDao.addCustomer(customer);

        Invoice invoice = new Invoice();
        invoice.setCustomerId(customer.getCustomerId());
        invoice.setLateFee(new BigDecimal("35.00"));
        invoice.setPickupDate(LocalDate.of(2018,8,12));
        invoice.setOrderDate(LocalDate.of(2018,7,12));
        invoice.setReturnDate(LocalDate.of(2018,9,12));
        invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setDiscount(new BigDecimal("25.00"));
        invoiceItem.setQuantity(5);
        invoiceItem.setUnitRate(new BigDecimal("25.00"));
        invoiceItem.setItemId(item.getItemId());
        invoiceItemDao.addInvoiceItem(invoiceItem);

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceItemId(invoiceItem.getInvoiceItemId());
        invoiceItem2.setInvoiceId(invoice.getInvoiceId());
        invoiceItem2.setDiscount(new BigDecimal("35.00"));
        invoiceItem2.setQuantity(5);
        invoiceItem2.setUnitRate(new BigDecimal("35.00"));
        invoiceItem2.setItemId(item.getItemId());

        invoiceItemDao.updateInvoiceItem(invoiceItem2);

        assertEquals(invoiceItem2, invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId()));





    }


}