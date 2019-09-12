package com.company.RentalService.dao;

import com.company.RentalService.dto.InvoiceItem;
import com.company.RentalService.dto.Item;

import java.util.List;

public interface InvoiceItemDao {
    InvoiceItem addInvoiceItem(InvoiceItem invoiceItem);

    InvoiceItem getInvoiceItem(int invoiceItemId);

    List<InvoiceItem> getAllInvoiceItem();

    List<Item> getItemsByInvoiceItem(int invoiceItemId);

    void updateInvoiceItem(InvoiceItem invoiceItem);

    void deleteInvoiceItem(int invoiceItemId);

}
