package com.company.RentalService.dao;

import com.company.RentalService.dto.Customer;
import com.company.RentalService.dto.Invoice;
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
public class InvoiceDaoTest {
    @Autowired
    CustomerDao customerDao;
    @Autowired
    InvoiceDao invoiceDao;
//    @Autowired
//    InvoiceItemDao invoiceItemDao;
//    @Autowired
//    ItemDao itemDao;

    @Before
    public void setUp() throws Exception {
        // Clean up the test db
        // Note: Invoice Item and Invoice tables should be deleted first due to FK depencency.
//        List<InvoiceItem> iaList = invoiceItemDao.getAllInvoiceItem();
//        for (InvoiceItem ia : iaList) {
//            invoiceItemDao.deleteInvoiceItem(ia.getInvoiceItemId());
//        }

        List<Invoice> iList = invoiceDao.getAllInvoice();
        for (Invoice i : iList) {
            invoiceDao.deleteInvoice(i.getInvoiceId());
        }

//        List<Item> aList = itemDao.getAllItem();
//        for (Item a : aList) {
//            itemDao.deleteItem(a.getItemId());
//        }

        List<Customer> cList = customerDao.getAllCustomer();
        for (Customer c : cList) {
            customerDao.deleteCustomer(c.getCustomerId());
        }

    }

    @Test
    public void addGetDeleteInvoice() {
        // Need to create a Customer first
        Customer customer = new Customer();
        customer.setFirstName("Rich");
        customer.setLastName("Fen");
        customer.setEmail("rich@gmail.com");
        customer.setCompany("Rich Company");
        customer.setPhone("123-456-7890");

        // object added to the database
        customerDao.addCustomer(customer);

        Invoice invoice = new Invoice();
        invoice.setCustomerId(customer.getCustomerId());
        invoice.setOrderDate(LocalDate.of(2019, 6, 13));
        invoice.setPickupDate(LocalDate.of(2019, 6, 15));
        invoice.setReturnDate(LocalDate.of(2019, 7, 14));
        invoice.setLateFee(new BigDecimal("15.99"));
        invoice = invoiceDao.addInvoice(invoice);

        Invoice invoice1 = invoiceDao.getInvoice(invoice.getInvoiceId());

        assertEquals(invoice1, invoice);

        invoiceDao.deleteInvoice(invoice.getInvoiceId());

        invoice1 = invoiceDao.getInvoice(invoice.getInvoiceId());

        assertNull(invoice1);

    }

    @Test
    public void getAllInvoice() {
        Customer customer = new Customer();
        customer.setFirstName("Rich");
        customer.setLastName("Fen");
        customer.setEmail("rich@gmail.com");
        customer.setCompany("Rich Company");
        customer.setPhone("123-456-7890");

        // Object added to the database
        customer = customerDao.addCustomer(customer);

        Invoice invoice = new Invoice();
        invoice.setCustomerId(customer.getCustomerId());
        invoice.setOrderDate(LocalDate.of(2019, 6, 13));
        invoice.setPickupDate(LocalDate.of(2019, 6, 15));
        invoice.setReturnDate(LocalDate.of(2019, 7, 14));
        invoice.setLateFee(new BigDecimal("15.99"));
        invoice = invoiceDao.addInvoice(invoice);

        Customer customer2 = new Customer();
        customer2.setFirstName("Jet");
        customer2.setLastName("Li");
        customer2.setEmail("jet@gmail.com");
        customer2.setCompany("Jet Company");
        customer2.setPhone("404-456-7890");

        // Object added to the database
        customer = customerDao.addCustomer(customer2);

        // Adding another invoice object with customer2.
        invoice = new Invoice();
        invoice.setCustomerId(customer2.getCustomerId());
        invoice.setOrderDate(LocalDate.of(2018, 1, 13));
        invoice.setPickupDate(LocalDate.of(2018, 1, 15));
        invoice.setReturnDate(LocalDate.of(2018, 2, 14));
        invoice.setLateFee(new BigDecimal("15.99"));
        invoice = invoiceDao.addInvoice(invoice);

        List<Invoice> iList = invoiceDao.getAllInvoice();

        assertEquals(2, iList.size());

    }

    @Test
    public void updateInvoice() {
        // Need to create a Customer first
        Customer customer = new Customer();
        customer.setFirstName("Rich");
        customer.setLastName("Fen");
        customer.setEmail("rich@gmail.com");
        customer.setCompany("Rich Company");
        customer.setPhone("123-456-7890");

        // object added to the database
        customerDao.addCustomer(customer);

        Invoice invoice = new Invoice();
        invoice.setCustomerId(customer.getCustomerId());
        invoice.setOrderDate(LocalDate.of(2019, 6, 13));
        invoice.setPickupDate(LocalDate.of(2019, 6, 15));
        invoice.setReturnDate(LocalDate.of(2019, 7, 14));
        invoice.setLateFee(new BigDecimal("15.99"));
        invoice = invoiceDao.addInvoice(invoice);

        invoice.setReturnDate(LocalDate.of(2019, 8, 15));
        invoice.setLateFee(new BigDecimal("20.99"));

        invoiceDao.updateInvoice(invoice);

        Invoice invoice1 = invoiceDao.getInvoice(invoice.getInvoiceId());

        assertEquals(invoice1, invoice);

    }

}