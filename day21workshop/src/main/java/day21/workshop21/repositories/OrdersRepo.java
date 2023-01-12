package day21.workshop21.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import day21.workshop21.models.Orders;
import static day21.workshop21.repositories.Queries.*;

@Repository
public class OrdersRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Orders> getOrdersById (String id) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_ORDERS_BY_ID, id);

        final List<Orders> orders = new LinkedList<>();

        while (rs.next()){
            orders.add(Orders.create(rs));
        }
        return orders;
    }
    
}
