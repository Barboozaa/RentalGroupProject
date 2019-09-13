package com.company.RentalService.service;

import com.company.RentalService.dao.*;
import com.company.RentalService.dto.InvoiceItem;
import com.company.RentalService.dto.Item;
import junit.framework.TestCase;
import org.junit.Before;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
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
    public void setUp() throws  Exception {

        setUpItemDaoMock();
        setUpInvoiceItemMock();

        service = new ServiceLayer(itemDao,customerDao,invoiceDao,invoiceItemDao);

    }


    //Item Methods
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


    //InvoiceItem methods
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


    private void setUpInvoiceItemMock() {

        invoiceItemDao = mock(InvoiceItemDaoJdbcTemplateImpl.class);
        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(6);
        invItem.setItemId(1);
        invItem.setDiscount(new BigDecimal(.12));
        invItem.setQuantity(12);
        invItem.setUnitRate(new BigDecimal(12.35));

        InvoiceItem invItem2 = new InvoiceItem();
        invItem2.setItemId(1);
        invItem2.setDiscount(new BigDecimal(.12));
        invItem2.setQuantity(12);
        invItem2.setUnitRate(new BigDecimal(12.35));

        List<InvoiceItem> iList = new ArrayList<>();
        iList.add(invItem);

        doReturn(invItem).when(invoiceItemDao).addInvoiceItem(invItem2);
        doReturn(invItem).when(invoiceItemDao).getInvoiceItem(6);
        doReturn(iList).when(invoiceItemDao).getAllInvoiceItem();


    }


//Mock SetUps

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


}
