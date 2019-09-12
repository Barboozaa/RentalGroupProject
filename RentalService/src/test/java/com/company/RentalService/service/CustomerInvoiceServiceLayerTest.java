package com.company.RentalService.service;

import com.company.RentalService.dao.*;
import com.company.RentalService.dto.Customer;
import com.company.RentalService.dto.Invoice;
import com.company.RentalService.dto.InvoiceItem;
import com.company.RentalService.dto.Item;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class CustomerInvoiceServiceLayerTest {

    private CustomerInvoiceServiceLayer service;
    private CustomerDao customerDao;
    private InvoiceDao invoiceDao;
    private InvoiceItemDao invoiceItemDao;
    private ItemDao itemDao;

    @Before
    public void setUp() throws Exception {
        setUpInvoiceDaoMock();
        setUpCustomerDaoMock();
        setUpItemInvoiceDaoMock();
        setUpItemDaoMock();

        service = new CustomerInvoiceServiceLayer(customerDao, invoiceDao, itemDao, invoiceItemDao);
    }

    private void setUpInvoiceDaoMock(){
        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setCustomerId(2);
        invoice.setOrderDate(LocalDate.of(2019,9,9));
        invoice.setPickupDate(LocalDate.of(2019,9,10));
        invoice.setReturnDate(LocalDate.of(2019,9,11));
        invoice.setLateFee(new BigDecimal(1.00));

        Invoice invoice1 = new Invoice();
        invoice1.setInvoiceId(1);
        invoice1.setCustomerId(2);
        invoice1.setOrderDate(LocalDate.of(2019,9,9));
        invoice1.setPickupDate(LocalDate.of(2019,9,10));
        invoice1.setReturnDate(LocalDate.of(2019,9,11));
        invoice1.setLateFee(new BigDecimal(1.00));

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);
        doReturn(invoice).when(invoiceDao).addInvoice(invoice1);
        doReturn(invoice).when(invoiceDao).getInvoice(1);
        doReturn(invoice).when(invoiceDao).getAllInvoice();
    }

    private void setUpCustomerDaoMock(){
        customerDao = mock(CustomerDaoJdbcTemplateImpl.class);
        Customer customer = new Customer();
        customer.setCustomerId(2);
        customer.setFirstName("Remington");
        customer.setLastName("Wyatt");
        customer.setEmail("RW@gmail.com");
        customer.setCompany("Cognizant");
        customer.setPhone("6781110202");
        Customer customer1 = new Customer();
        customer1.setCustomerId(2);
        customer1.setFirstName("Remington");
        customer1.setLastName("Wyatt");
        customer1.setEmail("RW@gmail.com");
        customer1.setCompany("Cognizant");
        customer1.setPhone("6781110202");

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        doReturn(customer).when(customerDao).addCustomer(customer1);
        doReturn(customer).when(customerDao).getCustomer(2);
        doReturn(customerList).when(customerDao).getAllCustomer();
    }

    private void setUpItemInvoiceDaoMock(){
        invoiceItemDao = mock(InvoiceItemDaoJdbcTemplateImpl.class);
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(3);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setItemId(4);
        invoiceItem.setQuantity(100);
        invoiceItem.setUnitRate(new BigDecimal(2.00));
        invoiceItem.setDiscount(new BigDecimal(1.00));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceItemId(3);
        invoiceItem1.setInvoiceId(1);
        invoiceItem1.setItemId(4);
        invoiceItem1.setQuantity(100);
        invoiceItem1.setUnitRate(new BigDecimal(2.00));
        invoiceItem1.setDiscount(new BigDecimal(1.00));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem);

        doReturn(invoiceItem).when(invoiceItemDao).addInvoiceItem(invoiceItem1);
        doReturn(invoiceItem).when(invoiceItemDao).getInvoiceItem(3);
    }

    private void setUpItemDaoMock(){
        itemDao = mock(ItemDaoJdbcTemplateImpl.class);
        Item item = new Item();
        item.setItemId(4);
        item.setName("bike");
        item.setDescription("Schwin");
        item.setDailyRate(new BigDecimal(10.00));
    }

    @Test
    public void saveCustomer() {
    }

    @Test
    public void findCustomer() {
    }

    @Test
    public void findAllCustomers() {
    }

    @Test
    public void updateCustomer() {
    }

    @Test
    public void removeCustomer() {
    }

    @Test
    public void saveInvoice() {
    }

    @Test
    public void findInvoice() {
    }

    @Test
    public void findAllInvoices() {
    }

    @Test
    public void updateInvoice() {
    }

    @Test
    public void removeInvoice() {
    }
}