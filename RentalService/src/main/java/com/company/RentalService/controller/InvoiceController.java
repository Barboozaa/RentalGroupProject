//package com.company.RentalService.controller;
//
//import com.company.RentalService.dao.InvoiceDaoJdbcTemplateImpl;
//import com.company.RentalService.dto.Invoice;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@RestController
//public class InvoiceController {
//    @Autowired
//    private InvoiceDaoJdbcTemplateImpl invoiceDaoJdbcTemplate;
//
//    @RequestMapping(value = "/invoice", method = RequestMethod.POST)
//    public Invoice createInvoice(@RequestBody @Valid Invoice invoice) {
//        return invoiceDaoJdbcTemplate.addInvoice(invoice);
//    }
//
//    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.GET)
//    public Invoice findInvoice(@PathVariable(name = "id") int id) {
//        return invoiceDaoJdbcTemplate.getInvoice(id);
//    }
//
//    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
//    public List<Invoice> findAllInvoices() {
//        return invoiceDaoJdbcTemplate.getAllInvoice();
//    }
//
//    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.DELETE)
//    public void deleteInvoice(@PathVariable(name = "id") int id) {
//        invoiceDaoJdbcTemplate.deleteInvoice(id);
//    }
//
//    @RequestMapping(value = "/invoice", method = RequestMethod.PUT)
//    public void updateInvoice(@RequestBody Invoice invoice) {
//        invoiceDaoJdbcTemplate.updateInvoice(invoice);
//    }
//}
