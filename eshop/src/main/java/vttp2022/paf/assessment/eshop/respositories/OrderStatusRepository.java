package vttp2022.paf.assessment.eshop.respositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paf.assessment.eshop.Utilities;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.eshop.models.OrderStatusCount;

import static vttp2022.paf.assessment.eshop.respositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class OrderStatusRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Task 4
    public void insertOrderStatus (OrderStatus orderStatus) {

        //Insert into order_id, delivery_id, status
        jdbcTemplate.update(SQL_INSERT_INTO_ORDER_STATUS, orderStatus.getOrderId(), orderStatus.getDeliveryId(), orderStatus.getStatus());

    }

    //Task 6
    public OrderStatusCount orderStatusCount (String name) {

        // prevent inheritance
        final List<OrderStatusCount> orderStatusCountList = new LinkedList<>();

        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_ORDER_STATUS_BY_NAME, name);

        while (rs.next()) {
            orderStatusCountList.add(Utilities.createOrderStatusCount(rs));
        }

        return orderStatusCountList.get(0);


    }
    
}
