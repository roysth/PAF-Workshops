package vttp.day24.models;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//Object oriented (OO) and functional programming
//java is object oriented, everything is object
//Since java is OO, need to present the orders as objects
//In SQL, its in tables (rows and columns)
//Think of how to map it from object to tables
public class PurchaseOrder {

    private String orderId;
    private String name;
    private Date orderDate;
    private List <LineItem> lineItems = new LinkedList<>();

    //This is for the testing on inserting data into the schema via the Day24Application
    public void addLineItem(LineItem lineItem) { this.lineItems.add(lineItem); }

    public List<LineItem> getLineItems() {
        return lineItems;
    }
    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    
}


//dont put the order_id under LineItem becuase based on the model, it is not an item under LineItem
//Instead, it is under PurchaseOrder
//sql uses it (foreign key) for relationship
//in java, it uses object,
/*In SQL, LineItem knows abt the relationship [Foreign Key is in here]
 *In Java (OO), PurchaseOrder knows abt the relationship
 *NOTE: dont put foreign key in model
 */

