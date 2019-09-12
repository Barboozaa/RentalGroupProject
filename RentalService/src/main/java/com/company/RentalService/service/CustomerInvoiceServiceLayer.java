package com.company.RentalService.service;

import com.company.RentalService.dao.CustomerDao;
import com.company.RentalService.dao.InvoiceDao;
import com.company.RentalService.dao.InvoiceItemDao;
import com.company.RentalService.dao.ItemDao;
import com.company.RentalService.dto.Customer;
import com.company.RentalService.dto.Invoice;
import com.company.RentalService.dto.InvoiceItem;
import com.company.RentalService.dto.Item;
import com.company.RentalService.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static java.util.stream.Collectors.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;

@Component
public class CustomerInvoiceServiceLayer {
    private CustomerDao customerDao;
    private InvoiceDao invoiceDao;
    private InvoiceItemDao invoiceItemDao;
    private ItemDao itemDao;

    @Autowired
    public CustomerInvoiceServiceLayer(CustomerDao customerDao, InvoiceDao invoiceDao, ItemDao itemDao, InvoiceItemDao invoiceItemDao){
        this.customerDao = customerDao;
        this.invoiceDao = invoiceDao;
        this.invoiceItemDao = invoiceItemDao;
        this.itemDao = itemDao;
    }

    //CRUD Customer
    public Customer saveCustomer(Customer customer){
        return customerDao.addCustomer(customer);
    }
    public Customer findCustomer(int id){
        return customerDao.getCustomer(id);
    }
    public List<Customer> findAllCustomers(){
        return customerDao.getAllCustomer();
    }
    public void updateCustomer(Customer customer){
        customerDao.updateCustomer(customer);
    }
    public void removeCustomer(int id){
        customerDao.deleteCustomer(id);
    }

    //CRUD Invoice
    public Invoice saveInvoice(Invoice invoice){
        return invoiceDao.addInvoice(invoice);
    }
    public Invoice findInvoice(int id){
        return invoiceDao.getInvoice(id);
    }
    public List<Invoice> findAllInvoices(){
        return invoiceDao.getAllInvoice();
    }
    public void updateInvoice(Invoice invoice){
        invoiceDao.updateInvoice(invoice);
    }
    public void removeInvoice(int id){
        invoiceDao.deleteInvoice(id);
    }


    private InvoiceViewModel buildViewModel(Invoice invoice){
        Customer customer = customerDao.getCustomer(invoice.getCustomerId());
        List<InvoiceItem> invoiceItemList = invoiceDao.getInvoiceItemsByInvoice(invoice.getInvoiceId());

        //get list of items
        List<Item> itemList= invoiceItemList.stream()
                .map(invoiceItem -> itemDao.getItem(invoiceItem.getItemId()))
                .collect(Collectors.toList());

        InvoiceViewModel avm = new InvoiceViewModel();
        avm.setInvoiceId(invoice.getInvoiceId());
        avm.setCustomer(customer);
        avm.setOrderDate(invoice.getOrderDate());
        avm.setPickupDate(invoice.getPickupDate());
        avm.setReturnDate(invoice.getReturnDate());
        avm.setLateFee(invoice.getLateFee());
        avm.setInvoiceItemList(invoiceItemList);
        avm.setItemList(itemList);
        return avm;
    }


}
