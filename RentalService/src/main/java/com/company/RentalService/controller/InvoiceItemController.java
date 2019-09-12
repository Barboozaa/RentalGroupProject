package com.company.RentalService.controller;

import com.company.RentalService.dao.InvoiceItemDaoJdbcTemplateImpl;
import com.company.RentalService.dto.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class InvoiceItemController {
    @Autowired
    private InvoiceItemDaoJdbcTemplateImpl invoiceItemDaoJdbcTemplate;

    @RequestMapping(value = "/invoiceItem", method = RequestMethod.POST)
    public InvoiceItem createInvoiceItem(@RequestBody @Valid InvoiceItem invoiceItem) {
        return invoiceItemDaoJdbcTemplate.addInvoiceItem(invoiceItem);
    }

    @RequestMapping(value = "/invoiceItem/{id}", method = RequestMethod.GET)
    public InvoiceItem findInvoiceItem(@PathVariable(name = "id") int id) {
        return invoiceItemDaoJdbcTemplate.getInvoiceItem(id);
    }

    @RequestMapping(value = "/invoiceItems", method = RequestMethod.GET)
    public List<InvoiceItem> findAllInvoiceItems() {
        return invoiceItemDaoJdbcTemplate.getAllInvoiceItem();
    }

    @RequestMapping(value = "/invoiceItem/{id}", method = RequestMethod.DELETE)
    public void deleteInvoiceItem(@PathVariable(name = "id") int id) {
        invoiceItemDaoJdbcTemplate.deleteInvoiceItem(id);
    }

    @RequestMapping(value = "/invoiceItem", method = RequestMethod.PUT)
    public void updateInvoiceItem(@RequestBody InvoiceItem invoiceItem) {
        invoiceItemDaoJdbcTemplate.updateInvoiceItem(invoiceItem);
    }
}
