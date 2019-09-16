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
    public void addInvoiceItem() {

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
        invoice.setLateFee(new BigDecimal("3.5"));
        invoice.setPickupDate(LocalDate.of(2018,8,12));
        invoice.setOrderDate(LocalDate.of(2018,7,12));
        invoice.setReturnDate(LocalDate.of(2018,9,12));
        invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setDiscount(new BigDecimal("25"));
        invoiceItem.setQuantity(5);
        invoiceItem.setUnitRate(new BigDecimal(".25"));
        invoiceItem.setItemId(item.getItemId());
        invoiceItemDao.addInvoiceItem(invoiceItem);

        InvoiceItem invoiceitem2 = invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId());

        //assertEquals(invoiceItem,invoiceitem2);


//        System.out.println(item.getItemId());
//        System.out.println(item.getDailyRate());
//        System.out.println(item.getDescription());
//        System.out.println(item.getName());
//
//        System.out.println(itemDao.getItem(invoiceItem.getItemId()).getName());
//        System.out.println(itemDao.getItem(invoiceItem.getItemId()).getDescription());
//        System.out.println(itemDao.getItem(invoiceItem.getItemId()).getDailyRate());
//        System.out.println(itemDao.getItem(invoiceItem.getItemId()).getItemId());


    }

    @Test
    public void getInvoiceItem() {
    }

    @Test
    public void getAllInvoiceItem() {
    }

    @Test
    public void updateInvoiceItem() {
    }

    @Test
    public void deleteInvoiceItem() {
    }
}