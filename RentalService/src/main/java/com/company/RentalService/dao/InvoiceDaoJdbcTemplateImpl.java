package com.company.RentalService.dao;

import com.company.RentalService.dto.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceDaoJdbcTemplateImpl implements InvoiceDao {
    private JdbcTemplate jdbcTemplate;

    private final static String INSERT_invoice_SQL =
            "INSERT INTO invoice (customer_id, order_date, pickup_date, return_date, late_fee) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private final static String SELECT_invoice_SQL =
            "SELECT * FROM invoice WHERE invoice_id = ?";

    private final static String SELECT_ALL_invoice_SQL =
            "SELECT * FROM invoice";

    private final static String UPDATE_invoice_SQL =
            "UPDATE invoice " +
                    "SET customer_id = ?, order_date = ?, pickup_date = ?, return_date = ?, late_fee = ? " +
                    "WHERE invoice_id = ?";

    private final static String DELETE_invoice_SQL =
            "DELETE FROM invoice WHERE invoice_id = ?";

    @Autowired
    public InvoiceDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Invoice addInvoice(Invoice invoice) {
        jdbcTemplate.update(INSERT_invoice_SQL,
                invoice.getCustomerId(),
                invoice.getOrderDate(),
                invoice.getPickupDate(),
                invoice.getReturnDate(),
                invoice.getLateFee());
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        invoice.setInvoiceId(id);
        return invoice;
    }

    @Override
    public Invoice getInvoice(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_invoice_SQL, this::mapRowToInvoice, id);
        } catch (EmptyResultDataAccessException e) {
            // if there is no invoice object with this id, just return null
            return null;
        }
    }

    @Override
    public List<Invoice> getAllInvoice() {
        return jdbcTemplate.query(SELECT_ALL_invoice_SQL, this::mapRowToInvoice);
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        jdbcTemplate.update(UPDATE_invoice_SQL,
                invoice.getCustomerId(),
                invoice.getOrderDate(),
                invoice.getPickupDate(),
                invoice.getReturnDate(),
                invoice.getLateFee(),
                invoice.getInvoiceId());

    }

    @Override
    public void deleteInvoice(int id) {
        jdbcTemplate.update(DELETE_invoice_SQL, id);

    }

    private Invoice mapRowToInvoice(ResultSet rs, int rowNum) throws SQLException {
            Invoice invoice = new Invoice();
            invoice.setInvoiceId(rs.getInt("invoice_id"));
            invoice.setCustomerId(rs.getInt("customer_id"));
            invoice.setOrderDate(rs.getDate("order_date").toLocalDate());
            invoice.setPickupDate(rs.getDate("pickup_date").toLocalDate());
            invoice.setReturnDate(rs.getDate("return_date").toLocalDate());
            invoice.setLateFee(rs.getBigDecimal("late_fee"));

            return invoice;

    }
}
