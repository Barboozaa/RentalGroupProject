package com.company.RentalService.service;

import com.company.RentalService.dao.*;
import com.company.RentalService.dto.Customer;
import com.company.RentalService.dto.Invoice;
import com.company.RentalService.dto.InvoiceItem;
import com.company.RentalService.dto.Item;
import com.company.RentalService.viewmodel.InvoiceViewModel;
import junit.framework.TestCase;
import org.junit.Before;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    ServiceLayer service;
    ItemDao itemDao;
    CustomerDao customerDao;
    InvoiceDao invoiceDao;
    InvoiceItemDao invoiceItemDao;

    @Before
    public void setUp() throws Exception {
        setUpInvoiceDaoMock();
        setUpCustomerDaoMock();
        setUpInvoiceItemDaoMock();
        setUpItemDaoMock();

        service = new ServiceLayer(itemDao, customerDao, invoiceDao, invoiceItemDao);
    }

    // Helper Methods ==============================================================
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

    private void setUpInvoiceItemDaoMock() {
        invoiceItemDao = mock(InvoiceItemDaoJdbcTemplateImpl.class);
        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(6);
        invItem.setInvoiceId(1);
        invItem.setItemId(1);
        invItem.setDiscount(new BigDecimal(.12));
        invItem.setQuantity(12);
        invItem.setUnitRate(new BigDecimal(12.35));

        InvoiceItem invItem2 = new InvoiceItem();
        invItem.setInvoiceId(1);
        invItem2.setItemId(1);
        invItem2.setDiscount(new BigDecimal(.12));
        invItem2.setQuantity(12);
        invItem2.setUnitRate(new BigDecimal(12.35));

        List<InvoiceItem> iList = new ArrayList<>();
        iList.add(invItem);

        doReturn(invItem).when(invoiceItemDao).addInvoiceItem(invItem2);
        doReturn(invItem).when(invoiceItemDao).getInvoiceItem(6);
        doReturn(iList).when(invoiceItemDao).getInvoiceItemsByInvoice(1);
        doReturn(iList).when(invoiceItemDao).getAllInvoiceItem();
    }

    private void setUpItemDaoMock() {
        itemDao = mock(ItemDaoJdbcTemplateImpl.class);
        Item item = new Item();
        item.setItemId(1);
        item.setName("Rem");
        item.setDailyRate(new BigDecimal(12.50));
        item.setDescription("This is Remy");

        Item item2 = new Item();
        item2.setName("Rem");
        item2.setDailyRate(new BigDecimal(12.50));
        item2.setDescription("This is Remy");

        List<Item> iList = new ArrayList<>();
        iList.add(item);
        doReturn(item).when(itemDao).addItem(item2);
        doReturn(item).when(itemDao).getItem(1);
        doReturn(iList).when(itemDao).getAllItem();
    }

    // Item Methods ==============================================================
    @Test
    public void saveFindFindItem() {

        Item item = new Item();
        item.setName("Rem");
        item.setDailyRate(new BigDecimal(12.50));
        item.setDescription("This is Remy");
        item = service.saveItem(item);

        Item fromService = service.findItem(item.getItemId());
        TestCase.assertEquals(item, fromService);
        List<Item> iList = service.findAllItems();
        TestCase.assertEquals(1, iList.size());
        TestCase.assertEquals(item, iList.get(0));

    }

    @Test
    public void updateItem() {

        Item item = new Item();
        item.setItemId(1);
        item.setName("Rem");
        item.setDailyRate(new BigDecimal(12.50));
        item.setDescription("This is Remy");

        service.saveItem(item);

        ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
        doNothing().when(itemDao).updateItem(itemCaptor.capture());
        service.updateItem(item);
        verify(itemDao,times(1)).updateItem(itemCaptor.getValue());
        assertEquals(item,itemCaptor.getValue());
    }

    @Test
    public void deleteItem() {

        Item item = new Item();
        item.setItemId(1);
        ArgumentCaptor<Integer> integerCaptor = ArgumentCaptor.forClass(Integer.class);


        doNothing().when(itemDao).deleteItem(integerCaptor.capture());
        service.removeItem(1);
        verify(itemDao,times(1)).deleteItem(integerCaptor.getValue());
        assertEquals(1,integerCaptor.getValue().intValue());
    }


    //Invoice Item methods ==============================================================
    @Test
    public void saveFindFindInvoiceItem() {

        InvoiceItem invItem = new InvoiceItem();
        invItem.setItemId(1);
        invItem.setDiscount(new BigDecimal(.12));
        invItem.setQuantity(12);
        invItem.setUnitRate(new BigDecimal(12.35));
        invItem = service.saveInvoiceItem(invItem);

        InvoiceItem fromService = service.findInvoiceItem(invItem.getInvoiceItemId());
        TestCase.assertEquals(invItem, fromService);
        List<InvoiceItem> iList = service.findAllInvoiceItems();
        TestCase.assertEquals(1, iList.size());
        TestCase.assertEquals(invItem, iList.get(0));

    }

    @Test
    public void updateInvoiceItem() {

        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(6);
        invItem.setItemId(1);
        invItem.setDiscount(new BigDecimal(.12));
        invItem.setQuantity(12);
        invItem.setUnitRate(new BigDecimal(12.35));

        service.saveInvoiceItem(invItem);

        ArgumentCaptor<InvoiceItem> invoiceItemCaptor = ArgumentCaptor.forClass(InvoiceItem.class);

        doNothing().when(invoiceItemDao).updateInvoiceItem(invoiceItemCaptor.capture());
        service.updateInvoiceItem(invItem);
        verify(invoiceItemDao,times(1)).updateInvoiceItem(invoiceItemCaptor.getValue());
        assertEquals(invItem,invoiceItemCaptor.getValue());

    }

    @Test
    public void deleteInvoiceItem() {

        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(6);
        ArgumentCaptor<Integer> integerCaptor = ArgumentCaptor.forClass(Integer.class);

        doNothing().when(invoiceItemDao).deleteInvoiceItem(integerCaptor.capture());
        service.removeInvoiceItem(6);
        verify(invoiceItemDao,times(1)).deleteInvoiceItem(integerCaptor.getValue());
        assertEquals(6,integerCaptor.getValue().intValue());

    }

    // Customer Methods ==============================================================
    @Test
    public void saveFindCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Remington");
        customer.setLastName("Wyatt");
        customer.setEmail("RW@gmail.com");
        customer.setCompany("Cognizant");
        customer.setPhone("6781110202");

        customer = service.saveCustomer(customer);
        Customer fromService = service.findCustomer(customer.getCustomerId());

        TestCase.assertEquals(customer,fromService);

    }

    @Test
    public void findAllCustomers() {
        Customer customer = new Customer();
        customer.setFirstName("Remington");
        customer.setLastName("Wyatt");
        customer.setEmail("RW@gmail.com");
        customer.setCompany("Cognizant");
        customer.setPhone("6781110202");

        customer = service.saveCustomer(customer);
        Customer fromService = service.findCustomer(customer.getCustomerId());
        TestCase.assertEquals(customer, fromService);

        List<Customer> cList = service.findAllCustomers();
        TestCase.assertEquals(1, cList.size());
        TestCase.assertEquals(customer, cList.get(0));
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

        TestCase.assertEquals(2, integerArgumentCaptor.getValue().intValue());
    }

    // Invoice Items ==============================================================
    @Test
    public void saveFindInvoice() {
        InvoiceViewModel ivm = new InvoiceViewModel();

        ivm.setOrderDate(LocalDate.of(2019,9,9));
        ivm.setPickupDate(LocalDate.of(2019,9,10));
        ivm.setReturnDate(LocalDate.of(2019,9,11));
        ivm.setLateFee(new BigDecimal(1.00));

        Customer customer = new Customer();
        customer.setFirstName("Remington");
        customer.setLastName("Wyatt");
        customer.setEmail("RW@gmail.com");
        customer.setCompany("Cognizant");
        customer.setPhone("6781110202");
        customer = service.saveCustomer(customer);

        ivm.setCustomer(customer);

        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceId(1);
        invItem.setItemId(1);
        invItem.setDiscount(new BigDecimal(.12));
        invItem.setQuantity(12);
        invItem.setUnitRate(new BigDecimal(12.35));

        List<InvoiceItem> invoiceItems = new ArrayList<>();
        invoiceItems.add(invItem);
        ivm.setInvoiceItemList(invoiceItems);

        ivm = service.saveInvoice(ivm);

        InvoiceViewModel fromService = service.findInvoice(ivm.getInvoiceId());
        assertEquals(ivm, fromService);
    }

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

        TestCase.assertEquals(1, integerArgumentCaptor.getValue().intValue());
    }
}
