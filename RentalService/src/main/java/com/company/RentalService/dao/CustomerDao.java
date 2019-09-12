package com.company.RentalService.dao;

import com.company.RentalService.dto.Customer;

import java.util.List;

public interface CustomerDao {
    Customer addCustomer(Customer customer);

    Customer getCustomer(int id);

    List<Customer> getAllCustomer();

    void updateCustomer(Customer customer);

    void deleteCustomer(int id);

}
