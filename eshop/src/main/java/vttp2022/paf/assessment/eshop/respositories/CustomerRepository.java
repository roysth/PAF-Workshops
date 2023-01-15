package vttp2022.paf.assessment.eshop.respositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paf.assessment.eshop.models.Customer;

import static vttp2022.paf.assessment.eshop.respositories.Queries.*;
import static vttp2022.paf.assessment.eshop.Utilities.*;

@Repository
public class CustomerRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// You cannot change the method's signature
	public Optional<Customer> findCustomerByName(String name) {
		// TODO: Task 3 

		// prevent inheritance
		final List<Customer> customerList = new LinkedList<>();

		SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_CUSTOMER_BY_NAME, name);

		while (rs.next()) {
            customerList.add(createCustomer(rs));
        }
        
        //Get(0) cus there is only 1 item in the list
        //Without Optional, it will be just return orderList.get(0)
        return Optional.of(customerList.get(0));

	}
}
