package com.company.RentalService.service;

import com.company.RentalService.dao.*;
import com.company.RentalService.dto.Customer;
import com.company.RentalService.dto.Invoice;
import com.company.RentalService.dto.InvoiceItem;
import com.company.RentalService.dto.Item;
import com.company.RentalService.viewmodel.InvoiceViewModel;
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
//        invoice1.setInvoiceId(1);
        invoice1.setCustomerId(2);
        invoice1.setOrderDate(LocalDate.of(2019,9,9));
        invoice1.setPickupDate(LocalDate.of(2019,9,10));
        invoice1.setReturnDate(LocalDate.of(2019,9,11));
        invoice1.setLateFee(new BigDecimal(1.00));

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);
        doReturn(invoice).when(invoiceDao).addInvoice(invoice1);
        doReturn(invoice).when(invoiceDao).getInvoice(1);
        doReturn(invoiceList).when(invoiceDao).getAllInvoice();
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
//        customer1.setCustomerId(2);
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
//        invoiceItem1.setInvoiceItemId(3);
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

        Item item1 = new Item();
//        item1.setItemId(4);
        item1.setName("bike");
        item1.setDescription("Schwin");
        item1.setDailyRate(new BigDecimal(10.00));

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        doReturn(item).when(itemDao).addItem(item1);
        doReturn(item).when(itemDao).getItem(4);
        doReturn(itemList).when(itemDao).getAllItem();
    }

    @Test
    public void saveFindCustomer() {
        Customer customer = new Customer();
//        customer.setCustomerId(2);
        customer.setFirstName("Remington");
        customer.setLastName("Wyatt");
        customer.setEmail("RW@gmail.com");
        customer.setCompany("Cognizant");
        customer.setPhone("6781110202");

        customer = service.saveCustomer(customer);
        Customer fromService = service.findCustomer(customer.getCustomerId());

        assertEquals(customer,fromService);

    }

    @Test
    public void findAllCustomers() {
        Customer customer = new Customer();
//        customer.setCustomerId(2);
        customer.setFirstName("Remington");
        customer.setLastName("Wyatt");
        customer.setEmail("RW@gmail.com");
        customer.setCompany("Cognizant");
        customer.setPhone("6781110202");

        customer = service.saveCustomer(customer);
        Customer fromService = service.findCustomer(customer.getCustomerId());
        assertEquals(customer, fromService);

        List<Customer> cList = service.findAllCustomers();
        assertEquals(1, cList.size());
        assertEquals(customer, cList.get(0));
    }

    @Test
    public void updateCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(2);
        customer.setFirstName("Remington");
        customer.setLastName("Wyatt");
        customer.setEmail("RW@gmail.com");
        customer.setCompany("Cognizant");
        customer.setPhone("6781110202");

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        doNothing().when(customerDao).updateCustomer(customerArgumentCaptor.capture());
        service.updateCustomer(customer);
        verify(customerDao, times(1)).updateCustomer(customerArgumentCaptor.getValue());
    }

    @Test
    public void removeCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(2);
        customer.setFirstName("Remington");
        customer.setLastName("Wyatt");
        customer.setEmail("RW@gmail.com");
        customer.setCompany("Cognizant");
        customer.setPhone("6781110202");

        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        doNothing().when(customerDao).deleteCustomer(integerArgumentCaptor.capture());
        service.removeCustomer(2);
        verify(customerDao, times(1)).deleteCustomer(integerArgumentCaptor.getValue());

        assertEquals(2, integerArgumentCaptor.getValue().intValue());
    }

    @Test
    public void saveFindInvoice() {
        InvoiceViewModel ivm = new InvoiceViewModel();

//        ivm.setInvoiceId(1);
        ivm.setOrderDate(LocalDate.of(2019,9,9));
        ivm.setPickupDate(LocalDate.of(2019,9,10));
        ivm.setReturnDate(LocalDate.of(2019,9,11));
        ivm.setLateFee(new BigDecimal(1.00));

        Customer customer = new Customer();
//        customer.setCustomerId(2);
        customer.setFirstName("Remington");
        customer.setLastName("Wyatt");
        customer.setEmail("RW@gmail.com");
        customer.setCompany("Cognizant");
        customer.setPhone("6781110202");
        customer = service.saveCustomer(customer);

        ivm.setCustomer(customer);

        InvoiceItem invoiceItem = new InvoiceItem();
//        invoiceItem.setInvoiceItemId(3);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setItemId(4);
        invoiceItem.setQuantity(100);
        invoiceItem.setUnitRate(new BigDecimal(2.00));
        invoiceItem.setDiscount(new BigDecimal(1.00));

        List<InvoiceItem> invoiceItems = new ArrayList<>();
        invoiceItems.add(invoiceItem);
        ivm.setInvoiceItemList(invoiceItems);


        ivm = service.saveInvoice(ivm);

        InvoiceViewModel fromService = service.findInvoice(ivm.getInvoiceId());
        assertEquals(ivm,fromService);

    }

//    @Test dont need
//    public void findAllInvoices() {
//        Invoice invoice = new Invoice();
//        invoice.setInvoiceId(1);
//        invoice.setCustomerId(2);
//        invoice.setOrderDate(LocalDate.of(2019,9,9));
//        invoice.setPickupDate(LocalDate.of(2019,9,10));
//        invoice.setReturnDate(LocalDate.of(2019,9,11));
//        invoice.setLateFee(new BigDecimal(1.00));
//
//        List<Invoice> iList = service.findAllInvoices();
//        assertEquals(1, iList.size());
//        assertEquals(invoice, iList.get(0));
//    }

//    @Test DONT NEED
//    public void updateInvoice() {
//        Invoice invoice = new Invoice();
//        invoice.setInvoiceId(1);
//        invoice.setCustomerId(2);
//        invoice.setOrderDate(LocalDate.of(2019,9,9));
//        invoice.setPickupDate(LocalDate.of(2019,9,10));
//        invoice.setReturnDate(LocalDate.of(2019,9,11));
//        invoice.setLateFee(new BigDecimal(1.00));
//
//        ArgumentCaptor<Invoice> invoiceArgumentCaptor = ArgumentCaptor.forClass(Invoice.class);
//        doNothing().when(invoiceDao).updateInvoice(invoiceArgumentCaptor.capture());
//        service.updateInvoice(invoice);
//        verify(invoiceDao, times(1)).updateInvoice(invoiceArgumentCaptor.getValue());
//    }

    @Test
    public void removeInvoice() {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setCustomerId(2);
        invoice.setOrderDate(LocalDate.of(2019,9,9));
        invoice.setPickupDate(LocalDate.of(2019,9,10));
        invoice.setReturnDate(LocalDate.of(2019,9,11));
        invoice.setLateFee(new BigDecimal(1.00));

        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        doNothing().when(invoiceDao).deleteInvoice(integerArgumentCaptor.capture());
        service.removeInvoice(1);
        verify(invoiceDao, times(1)).deleteInvoice(integerArgumentCaptor.getValue());

        assertEquals(1, integerArgumentCaptor.getValue().intValue());
    }
}