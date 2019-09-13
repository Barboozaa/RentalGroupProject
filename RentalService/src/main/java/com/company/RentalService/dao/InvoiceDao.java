package com.company.RentalService.dao;

import com.company.RentalService.dto.Invoice;
import com.company.RentalService.dto.InvoiceItem;
import com.company.RentalService.dto.Item;

import java.util.List;

public interface InvoiceDao {
    Invoice addInvoice(Invoice invoice);

    Invoice getInvoice(int invoiceId);

    List<Invoice> getAllInvoice();


    void updateInvoice(Invoice invoice);

    void deleteInvoice(int invoiceId);

}
