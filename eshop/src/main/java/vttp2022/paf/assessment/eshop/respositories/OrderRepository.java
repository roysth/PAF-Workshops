package vttp2022.paf.assessment.eshop.respositories;

import static vttp2022.paf.assessment.eshop.respositories.Queries.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.paf.assessment.eshop.models.Order;

@Repository
public class OrderRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// TODO: Task 3
	// Just get the list of lineItem from order object
	public void insertOrder (Order order) {

		//Insert into Orders table
		jdbcTemplate.update(SQL_INSERT_INTO_ORDERS, order.getOrderId(), order.getOrderDate(), order.getAddress(), order.getEmail(), order.getName());

		//Insert into lineItems table
		List<Object[]> data = order.getLineItems()
			.stream()
			.map(o -> {
				Object [] oa = new Object[3];
				oa[0] = o.getItem();
				oa[1] = o.getQuantity();
				oa[2] = order.getOrderId();
				return oa;
			})
			.toList();
		jdbcTemplate.batchUpdate(SQL_INSERT_INTO_LINEITEMS, data);

	}
}
