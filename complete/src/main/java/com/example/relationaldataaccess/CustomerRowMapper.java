package com.example.relationaldataaccess;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.id = rs.getInt("id");
        customer.firstName = rs.getString("first_name");
        customer.lastName = rs.getString("last_name");
        customer.index = rs.getInt("index");
        return customer;
    }
}
