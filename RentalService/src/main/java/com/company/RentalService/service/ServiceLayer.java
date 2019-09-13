package com.company.RentalService.service;

import com.company.RentalService.dao.CustomerDao;
import com.company.RentalService.dao.InvoiceDao;
import com.company.RentalService.dao.InvoiceItemDao;
import com.company.RentalService.dao.ItemDao;
import com.company.RentalService.dto.Customer;
import com.company.RentalService.dto.Invoice;
import com.company.RentalService.dto.InvoiceItem;
import com.company.RentalService.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLayer {

    private ItemDao itemDao;
    private CustomerDao customerDao;
    private InvoiceDao invoiceDao;
    private InvoiceItemDao invoiceItemDao;

    public ServiceLayer(ItemDao itemDao, CustomerDao customerDao, InvoiceDao invoiceDao, InvoiceItemDao invoiceItemDao) {
        this.itemDao = itemDao;
        this.customerDao = customerDao;
        this.invoiceDao = invoiceDao;
        this.invoiceItemDao = invoiceItemDao;
    }

    public Item saveItem(Item item) {return itemDao.addItem(item);}
    public Item findItem(int id) {return itemDao.getItem(id);}
    public List<Item> findAllItems() {return itemDao.getAllItem();}
    public void updateItem(Item item) {itemDao.updateItem(item);}
    public void removeItem(int id) {itemDao.deleteItem(id);}

    public InvoiceItem saveInvoiceItem(InvoiceItem invoiceItem) {return invoiceItemDao.addInvoiceItem(invoiceItem);}
    public InvoiceItem findInvoiceItem(int id) {return invoiceItemDao.getInvoiceItem(id);}
    public List<InvoiceItem> findAllInvoiceItems() {return invoiceItemDao.getAllInvoiceItem();}
    public void updateInvoiceItem(InvoiceItem invoiceItem) {invoiceItemDao.updateInvoiceItem(invoiceItem);}
    public void removeInvoiceItem(int id) {invoiceItemDao.deleteInvoiceItem(id);}







}
