package com.company.RentalService.dao;

import com.company.RentalService.dto.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerDaoTest {

    @Autowired
    CustomerDao customerDao;
//    @Autowired
//    InvoiceDao invoiceDao;
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
//
//        List<Invoice> iList = invoiceDao.getAllInvoice();
//        for (Invoice i : iList) {
//            invoiceDao.deleteInvoice(i.getInvoiceId());
//        }
//
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
    public void addGetDeleteCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Rich");
        customer.setLastName("Fen");
        customer.setEmail("rich@gmail.com");
        customer.setCompany("Rich Company");
        customer.setPhone("123-456-7890");

        // object added to the database
        customerDao.addCustomer(customer);

        // Now, object should have the database-generated customer_id (must be programmed in CustomerDaoJdbcTemplateImpl)
        // Run the getCustomer() method here as well
        Customer customer1 = customerDao.getCustomer(customer.getCustomerId());

        // addCustomer() test
        assertEquals(customer1, customer);

        customerDao.deleteCustomer(customer.getCustomerId());

        // Get the object reference
        customer1 = customerDao.getCustomer(customer.getCustomerId());

        // deleteCustomer test
        assertNull(customer1);
    }

    @Test
    public void getAllCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Rich");
        customer.setLastName("Fen");
        customer.setEmail("rich@gmail.com");
        customer.setCompany("Rich Company");
        customer.setPhone("123-456-7890");

        // Object added to the database
        customer = customerDao.addCustomer(customer);

        customer = new Customer();
        customer.setFirstName("Jet");
        customer.setLastName("Li");
        customer.setEmail("jet@gmail.com");
        customer.setCompany("Jet Company");
        customer.setPhone("404-456-7890");

        // Object added to the database
        customer = customerDao.addCustomer(customer);

        List<Customer> cList = customerDao.getAllCustomer();
        assertEquals(cList.size(), 2);

    }

    @Test
    public void updateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Rich");
        customer.setLastName("Fen");
        customer.setEmail("rich@gmail.com");
        customer.setCompany("Rich Company");
        customer.setPhone("123-456-7890");

        // Object added to the database
        customer = customerDao.addCustomer(customer);

        customer.setFirstName("Richard");
        customer.setEmail("richard@gmail.com");

        customerDao.updateCustomer(customer);

        // Get the reference of the updated customer
        Customer customer1 = customerDao.getCustomer(customer.getCustomerId());

        assertEquals("Richard", customer1.getFirstName());
        assertEquals("richard@gmail.com", customer1.getEmail());

    }

}