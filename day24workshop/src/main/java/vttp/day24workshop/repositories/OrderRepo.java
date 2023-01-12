package vttp.day24workshop.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.day24workshop.model.Orders;

import static vttp.day24workshop.repositories.Queries.*;

@Repository
public class OrderRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Boolean addOrder (Orders orders) {

        //This returns an Integer
        //Don't include the List<OrderDetails>
        Integer add = jdbcTemplate.update(SQL_INSERT_ORDERS, orders.getOrderId(), orders.getOrder_date(),
            orders.getCustomer_name(), orders.getShip_address(), orders.getNotes(), orders.getTax());

        //If successfully added, add = 1, hence true
        return add > 0;
    }
    
}


// private Integer orderId;
// private Date order_date;
// private String customer_name;
// private String ship_address;
// private String notes;
// private Float tax;
// private List <OrderDetails> orderDetailsList = new LinkedList<>();