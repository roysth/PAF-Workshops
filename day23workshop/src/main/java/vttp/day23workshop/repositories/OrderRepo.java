package vttp.day23workshop.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.day23workshop.model.Order;
import static vttp.day23workshop.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class OrderRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<Order> getOrderDetails (String orderId) {

        // prevent inheritance
        final List<Order> orderList = new LinkedList<>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_ORDER_DETAILS, orderId);

        while (rs.next()) {
            orderList.add(Order.create(rs));
        }
        
        //Get(0) cus there is only 1 item in the list
        //Without Optional, it will be just return orderList.get(0)
        return Optional.of(orderList.get(0));
    }

    
}
