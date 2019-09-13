//package com.company.RentalService.service;
//
//import com.company.RentalService.dao.CustomerDao;
//import com.company.RentalService.dao.InvoiceDao;
//import com.company.RentalService.dao.InvoiceItemDao;
//import com.company.RentalService.dao.ItemDao;
//import com.company.RentalService.dto.Customer;
//import com.company.RentalService.dto.Invoice;
//import com.company.RentalService.dto.InvoiceItem;
//import com.company.RentalService.dto.Item;
//import com.company.RentalService.viewmodel.InvoiceViewModel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import static java.util.stream.Collectors.*;
//import java.util.*;
//import java.util.stream.Collector;
//import java.util.stream.Collectors;
//
//import org.springframework.transaction.annotation.Transactional;
//
//import static java.util.stream.Collectors.toList;
//
//@Component
//public class CustomerInvoiceServiceLayer {
//    private CustomerDao customerDao;
//    private InvoiceDao invoiceDao;
//    private InvoiceItemDao invoiceItemDao;
//    private ItemDao itemDao;
//
//    @Autowired
//    public CustomerInvoiceServiceLayer(CustomerDao customerDao, InvoiceDao invoiceDao, ItemDao itemDao, InvoiceItemDao invoiceItemDao){
//        this.customerDao = customerDao;
//        this.invoiceDao = invoiceDao;
//        this.invoiceItemDao = invoiceItemDao;
//        this.itemDao = itemDao;
//    }
//
//    //CRUD Customer
//    public Customer saveCustomer(Customer customer){
//        return customerDao.addCustomer(customer);
//    }
//    public Customer findCustomer(int id){
//        return customerDao.getCustomer(id);
//    }
//    public List<Customer> findAllCustomers(){
//        return customerDao.getAllCustomer();
//    }
//    public void updateCustomer(Customer customer){
//        customerDao.updateCustomer(customer);
//    }
//    public void removeCustomer(int id){
//        customerDao.deleteCustomer(id);
//    }
//
//    //CRUD Invoice
//    public InvoiceViewModel saveInvoice(InvoiceViewModel viewModel){
//        Invoice i = new Invoice();
//        i.setCustomerId(viewModel.getCustomer().getCustomerId());
//        i.setOrderDate(viewModel.getOrderDate());
//        i.setPickupDate(viewModel.getPickupDate());
//        i.setReturnDate(viewModel.getReturnDate());
//        i.setLateFee(viewModel.getLateFee());
//
//        i = invoiceDao.addInvoice(i);
//
//        viewModel.setInvoiceId(i.getInvoiceId());
//
//        List<InvoiceItem> invoiceItems = viewModel.getInvoiceItemList();
//
//        invoiceItems.stream().forEach(iI ->
//        {iI.setInvoiceId(viewModel.getInvoiceId());
//            invoiceItemDao.addInvoiceItem(iI);
//        });
//
//        invoiceItems = invoiceItemDao.getInvoiceItemsByInvoice(viewModel.getInvoiceId()); //need to put this in jdbcTemplate
//        viewModel.setInvoiceItemList(invoiceItems);
//
//        return viewModel;
//    }
//
//    public InvoiceViewModel findInvoice(int id){
//        Invoice invoice = invoiceDao.getInvoice(id);
//        return buildViewModel(invoice);
//    }
//
//    //DONT NEED TO FIND INVOICE ONLY FIND INVOICE BY CUSTOMER
////    public List<Invoice> findAllInvoices(){
////        return invoiceDao.getAllInvoice();
////    }
////    public void updateInvoice(Invoice invoice){
////        invoiceDao.updateInvoice(invoice);
////    }
//
//    //where do we remove the associated invoice items? in the service layer or in the dao
//    public void removeInvoice(int id){
//        List<InvoiceItem> invoiceItems = invoiceItemDao.getInvoiceItemsByInvoice(id);
//
//        invoiceItems.stream().forEach(invoiceItem -> invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId()));
//
//        invoiceDao.deleteInvoice(id);
//
//    }
//
//    public List<Invoice> findInvoiceByCustomer(int id){
//        List<Invoice> invoiceList = invoiceDao.getAllInvoice();
//
//        invoiceList.stream().filter(invoice -> invoice.getCustomerId() == id);
//        return invoiceList;
//    }
//
//
//    //CANNOT UNCOMMENT UNTIL VIEW MODEL HAS getInvoiceItemsByInvoice
//
//    private InvoiceViewModel buildViewModel(Invoice invoice){
//        Customer customer = customerDao.getCustomer(invoice.getCustomerId());
//        List<InvoiceItem> invoiceItemList = invoiceItemDao.getAllInvoiceItem();
//
////        //get list of items, dont need remmy used itemDao
////        List<Item> itemList= invoiceItemList.stream()
////                .map(invoiceItem -> itemDao.getItem(invoiceItem.getItemId()))
////                .collect(Collectors.toList());
//
//        InvoiceViewModel avm = new InvoiceViewModel();
//        avm.setInvoiceId(invoice.getInvoiceId());
//        avm.setCustomer(customer);
//        avm.setOrderDate(invoice.getOrderDate());
//        avm.setPickupDate(invoice.getPickupDate());
//        avm.setReturnDate(invoice.getReturnDate());
//        avm.setLateFee(invoice.getLateFee());
//        avm.setInvoiceItemList(invoiceItemList);
////        avm.setItemList(itemList);
//        return avm;
//    }
//
//
//}
