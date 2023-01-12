package vttp.day24.repositories;


import static vttp.day24.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.day24.models.LineItem;
import vttp.day24.models.PurchaseOrder;

//For this exercise, the schema is empty, hence need to add data into it
@Repository
public class LineItemRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    public void addLineItems (PurchaseOrder purchaseOrder) {
        addLineItems(purchaseOrder.getLineItems(), purchaseOrder.getOrderId());
    }

    //This method is needed in order to run the above step 
    //Need the orderId (foreign key) to update
    //include a String orderId cus in the model, there is no orderID 
    public void addLineItems (List<LineItem> lineItem, String orderId) {
        /* 
        Option 1:
        List<Object[]> data = new LinkedList<>();
        for (LineItem li: lineItems) {
            Object[] l = new Object[3];
            l[0] = li.getDescription();
            l[1] = li.getQuantity();
            l[2] = orderId;
            data.add(l);
        }
        */
        //Option 2:
        //Stream can be use cus its a List
        // Stream - alternative to iteration
        List<Object[]> data = lineItem.stream()
            .map(li -> {
                Object[] l = new Object[3];
                l[0] = li.getDescription();
                l[1] = li.getQuantity();
                l[2] = orderId;
                return l;
            })
            .toList();

            // Object[] l = new Object[3]:
            // Creates an array of objects with a length of 3, with a variable named "1"

        //Do a batch update
        //This returns an integer[]
        jdbcTemplate.batchUpdate(SQL_INSERT_LINE_ITEM, data);

    }
    


    
}
