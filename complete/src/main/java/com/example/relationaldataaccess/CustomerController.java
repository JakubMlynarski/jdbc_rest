package com.example.relationaldataaccess;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

@RestController
@RequestMapping("/customers")
@CrossOrigin
public class CustomerController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@PostMapping
	public String createCustomer(@RequestBody Customer customer) {
		String sql = "INSERT INTO customers (first_name, last_name, index) VALUES (?, ?, ?)";
		int result = jdbcTemplate.update(sql, customer.firstName, customer.lastName, customer.index);
		return result > 0 ? "Customer created successfully" : "Failed to create customer";
	}

	@GetMapping
	public List<Customer> getCustomers(
			@RequestParam(required = false) String firstname,
			@RequestParam(required = false) String lastname,
			@RequestParam(required = false) Integer index) {

		StringBuilder sql = new StringBuilder("SELECT * FROM customers");
		List<Object> params = new ArrayList<>();

		if (firstname != null || lastname != null || index != null) {
			sql.append(" WHERE");

			if (firstname != null) {
				sql.append(" first_name = ?");
				params.add(firstname);
			}

			if (lastname != null) {
				if (!params.isEmpty()) sql.append(" AND");
				sql.append(" last_name = ?");
				params.add(lastname);
			}

			if (index != null) {
				if (!params.isEmpty()) sql.append(" AND");
				sql.append(" index = ?");
				params.add(index);
			}
		}

		return jdbcTemplate.query(sql.toString(), params.toArray(), new CustomerRowMapper());
	}

	@GetMapping("/{id}")
	public Customer getCustomerById(@PathVariable int id) {
		String sql = "SELECT * FROM customers WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CustomerRowMapper());
	}

	@PutMapping("/{id}")
	public String updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
		String sql = "UPDATE customers SET first_name = ?, last_name = ?, index = ? WHERE id = ?";
		int result = jdbcTemplate.update(sql, customer.firstName, customer.lastName, customer.index, id);
		return result > 0 ? "Customer updated successfully" : "Failed to update customer";
	}

	@DeleteMapping("/{id}")
	public String deleteCustomer(@PathVariable int id) {
		String sql = "DELETE FROM customers WHERE id = ?";
		int result = jdbcTemplate.update(sql, id);
		return result > 0 ? "Customer deleted successfully" : "Failed to delete customer";
	}
}
