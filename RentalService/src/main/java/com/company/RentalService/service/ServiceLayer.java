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

    // CRUD Item
    public Item saveItem(Item item) {return itemDao.addItem(item);}
    public Item findItem(int id) {return itemDao.getItem(id);}
    public List<Item> findAllItems() {return itemDao.getAllItem();}
    public void updateItem(Item item) {itemDao.updateItem(item);}
    public void removeItem(int id) {itemDao.deleteItem(id);}

    // CRUD Invoice Item
    public InvoiceItem saveInvoiceItem(InvoiceItem invoiceItem) {return invoiceItemDao.addInvoiceItem(invoiceItem);}
    public InvoiceItem findInvoiceItem(int id) {return invoiceItemDao.getInvoiceItem(id);}
    public List<InvoiceItem> findAllInvoiceItems() {return invoiceItemDao.getAllInvoiceItem();}
    public void updateInvoiceItem(InvoiceItem invoiceItem) {invoiceItemDao.updateInvoiceItem(invoiceItem);}
    public void removeInvoiceItem(int id) {invoiceItemDao.deleteInvoiceItem(id);}

    // ==============================================================================================================================

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
    public InvoiceViewModel saveInvoice(InvoiceViewModel viewModel){
        Invoice i = new Invoice();
        i.setCustomerId(viewModel.getCustomer().getCustomerId());
        i.setOrderDate(viewModel.getOrderDate());
        i.setPickupDate(viewModel.getPickupDate());
        i.setReturnDate(viewModel.getReturnDate());
        i.setLateFee(viewModel.getLateFee());

        i = invoiceDao.addInvoice(i);

        viewModel.setInvoiceId(i.getInvoiceId());

        List<InvoiceItem> invoiceItems = viewModel.getInvoiceItemList();

        invoiceItems.stream().forEach(iI -> {
            iI.setInvoiceId(viewModel.getInvoiceId());
            invoiceItemDao.addInvoiceItem(iI);
        });

        invoiceItems = invoiceItemDao.getInvoiceItemsByInvoice(viewModel.getInvoiceId());
        viewModel.setInvoiceItemList(invoiceItems);

        return viewModel;
    }

    public InvoiceViewModel findInvoice(int id){
        Invoice invoice = invoiceDao.getInvoice(id);
        return buildViewModel(invoice);
    }

    public void removeInvoice(int id){
        List<InvoiceItem> invoiceItems = invoiceItemDao.getInvoiceItemsByInvoice(id);

        invoiceItems.stream().forEach(invoiceItem -> invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId()));

        invoiceDao.deleteInvoice(id);

    }

    public List<Invoice> findInvoiceByCustomer(int id){
        List<Invoice> invoiceList = invoiceDao.getAllInvoice();

        invoiceList.stream().filter(invoice -> invoice.getCustomerId() == id);
        return invoiceList;
    }

    // Helper Method
    private InvoiceViewModel buildViewModel(Invoice invoice){
        Customer customer = customerDao.getCustomer(invoice.getCustomerId());

        InvoiceViewModel avm = new InvoiceViewModel();
        avm.setInvoiceId(invoice.getInvoiceId());
        avm.setCustomer(customer);
        avm.setOrderDate(invoice.getOrderDate());
        avm.setPickupDate(invoice.getPickupDate());
        avm.setReturnDate(invoice.getReturnDate());
        avm.setLateFee(invoice.getLateFee());
        avm.setInvoiceItemList(invoiceItemDao.getInvoiceItemsByInvoice(invoice.getInvoiceId())); //==================================================
        return avm;
    }
}
