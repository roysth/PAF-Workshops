package vttp.day24.repositories;

import static vttp.day24.repositories.Queries.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.day24.models.PurchaseOrder;

@Repository
public class PurchaseOrderRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean insertPurchaseOrder (PurchaseOrder po) {

        //Needs a > 0 cus this method returns an integer. It returns how many rows it updates
        return jdbcTemplate.update (SQL_INSERT_PURCHASE_ORDER, po.getOrderId(), po.getName(), po.getOrderDate()) > 0;

        
    }



    
}
