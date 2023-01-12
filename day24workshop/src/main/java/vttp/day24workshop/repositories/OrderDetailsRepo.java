package vttp.day24workshop.repositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.day24workshop.model.OrderDetails;
import vttp.day24workshop.model.Orders;

import static vttp.day24workshop.repositories.Queries.*;

import java.util.List;

@Repository

public class OrderDetailsRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Method to update the Order Details SQL table
    public void addOrderDetails (List<OrderDetails> orderDetailsList, String orderId) {

        List<Object[]> data = orderDetailsList
                    .stream()
                    .map(o -> {
                        //Create array size of 6 to include foreignkey orderId
                        Object [] oa = new Object[5];
                        oa[0] = o.getProduct();
                        oa[1] = o.getUnit_price();
                        oa[2] = o.getDiscount();
                        oa[3] = o.getQuantity();
                        oa[4] = orderId;
                        return oa;
                    })
                    .toList();
                    // Object[] oa = new Object[6]:
                    // Creates an array of objects with a length of 6, with a variable named "oa"

        //Do a batch update
        jdbcTemplate.batchUpdate(SQL_INSERT_ORDER_DETAILS, data);
    }

    //Get the details from the user input to use the above method to add into SQL
    public void addOrderDetails (Orders order) {
        addOrderDetails(order.getOrderDetailsList(), order.getOrderId());
    }
    
}
