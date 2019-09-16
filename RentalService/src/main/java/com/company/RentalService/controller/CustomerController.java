package com.company.RentalService.controller;

import com.company.RentalService.dao.CustomerDaoJdbcTemplateImpl;
import com.company.RentalService.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerDaoJdbcTemplateImpl customerDaoJdbcTemplate;

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public Customer createCustomer(@RequestBody @Valid Customer customer) {
        return customerDaoJdbcTemplate.addCustomer(customer);
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    public Customer findCustomer(@PathVariable(name="id") int id) {
        return customerDaoJdbcTemplate.getCustomer(id);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public List<Customer> findAllCustomers() {
        return customerDaoJdbcTemplate.getAllCustomer();
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable(name = "id") int id) {
        customerDaoJdbcTemplate.deleteCustomer(id);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.PUT)
    public void updateCustomer(@RequestBody Customer customer) {
        customerDaoJdbcTemplate.updateCustomer(customer);
    }
}
