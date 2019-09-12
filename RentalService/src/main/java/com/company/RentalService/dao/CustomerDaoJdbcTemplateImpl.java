package com.company.RentalService.dao;

import com.company.RentalService.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDaoJdbcTemplateImpl implements CustomerDao {
    private JdbcTemplate jdbcTemplate;

    private final static String INSERT_CUSTOMER_SQL =
            "INSERT INTO customer (first_name, last_name, email, company, phone) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private final static String SELECT_CUSTOMER_SQL =
            "SELECT * FROM customer WHERE customer_id = ?";

    private final static String SELECT_ALL_CUSTOMER_SQL =
            "SELECT * FROM customer";

    private final static String UPDATE_CUSTOMER_SQL =
            "UPDATE customer " +
                    "SET first_name = ?, last_name = ?, email = ?, company = ?, phone = ? " +
                    "WHERE customer_id = ?";

    private final static String DELETE_CUSTOMER_SQL =
            "DELETE FROM customer WHERE customer_id = ?";



    @Autowired
    public CustomerDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @Transactional
    public Customer addCustomer(Customer customer) {
        jdbcTemplate.update(INSERT_CUSTOMER_SQL,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getCompany(),
                customer.getPhone());
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        customer.setCustomerId(id);
        return customer;
    }

    @Override
    public Customer getCustomer(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_CUSTOMER_SQL, this::mapRowToCustomer, id);
        } catch (EmptyResultDataAccessException e) {
            // if there is no Customer object with this id, just return null
            return null;
        }
    }

    @Override
    public List<Customer> getAllCustomer() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMER_SQL, this::mapRowToCustomer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        jdbcTemplate.update(UPDATE_CUSTOMER_SQL,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getCustomerId(),
                customer.getPhone(),
                customer.getCustomerId());
    }

    @Override
    public void deleteCustomer(int id) {
        jdbcTemplate.update(DELETE_CUSTOMER_SQL, id);
    }

    private Customer mapRowToCustomer(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setEmail(rs.getString("email"));
        customer.setCompany(rs.getString("company"));
        customer.setPhone(rs.getString("phone"));

        return customer;

    }
}
