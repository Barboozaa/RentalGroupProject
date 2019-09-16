package com.company.RentalService.dao;

import com.company.RentalService.dto.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceItemDaoJdbcTemplateImpl implements InvoiceItemDao {
    private JdbcTemplate jdbcTemplate;

    private final static String INSERT_CUSTOMER_SQL =
            "INSERT INTO invoice_item (invoice_id, item_id, quantity, unit_rate, discount) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private final static String SELECT_CUSTOMER_SQL =
            "SELECT * FROM invoice_item WHERE invoice_item_id = ?";

    private final static String SELECT_ALL_CUSTOMER_SQL =
            "SELECT * FROM invoice_item";

    private final static String UPDATE_CUSTOMER_SQL =
            "UPDATE invoice_item " +
                    "SET invoice_id = ?, item_id = ?, quantity = ?, unit_rate = ?, discount = ? " +
                    "WHERE invoice_item_id = ?";

    private final static String DELETE_CUSTOMER_SQL =
            "DELETE FROM invoice_item WHERE invoice_item_id = ?";

    private final static String SELECT_INVOICE_ITEM_BY_INVOICE_SQL =
            "SELECT * FROM invoice_item WHERE invoice_id = ?";

    @Autowired
    public InvoiceItemDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public InvoiceItem addInvoiceItem(InvoiceItem invoiceItem) {
        jdbcTemplate.update(INSERT_CUSTOMER_SQL,
                invoiceItem.getInvoiceId(),
                invoiceItem.getItemId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitRate(),
                invoiceItem.getDiscount());
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        invoiceItem.setInvoiceItemId(id);
        return invoiceItem;
    }

    @Override
    public InvoiceItem getInvoiceItem(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_CUSTOMER_SQL, this::mapRowToInvoiceItem, id);
        } catch (EmptyResultDataAccessException e) {
            // if there is no InvoiceItem object with this id, just return null
            return null;
        }
    }

    @Override
    public List<InvoiceItem> getAllInvoiceItem() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMER_SQL, this::mapRowToInvoiceItem);
    }

    @Override
    public void updateInvoiceItem(InvoiceItem invoiceItem) {
        jdbcTemplate.update(UPDATE_CUSTOMER_SQL,
                invoiceItem.getInvoiceId(),
                invoiceItem.getItemId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitRate(),
                invoiceItem.getDiscount(),
                invoiceItem.getInvoiceItemId());
    }

    @Override
    public void deleteInvoiceItem(int id) {
        jdbcTemplate.update(DELETE_CUSTOMER_SQL, id);

    }

    @Override
    public List<InvoiceItem> getInvoiceItemsByInvoice(int id){
        return jdbcTemplate.query(SELECT_INVOICE_ITEM_BY_INVOICE_SQL, this::mapRowToInvoiceItem, id);
    }


    private InvoiceItem mapRowToInvoiceItem(ResultSet rs, int rowNum) throws SQLException {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(rs.getInt("invoice_Item_id"));
        invoiceItem.setInvoiceId(rs.getInt("invoice_id"));
        invoiceItem.setItemId(rs.getInt("item_id"));
        invoiceItem.setQuantity(rs.getInt("quantity"));
        invoiceItem.setUnitRate(rs.getBigDecimal("unit_rate"));
        invoiceItem.setDiscount(rs.getBigDecimal("discount"));

        return invoiceItem;

    }

}
