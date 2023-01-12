package vttp.day24workshop.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Orders {

   
    private String orderId;
    private Date order_date;
    private String customer_name;
    private String ship_address;
    private String notes;
    private Float tax;
    private List <OrderDetails> orderDetailsList = new LinkedList<>();


    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public Date getOrder_date() {
        return order_date;
    }
    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }
    //FOR DATE, REMEMBER THIS
    //To get the date from html form
    public void setOrder_date(String order_date) {
        try {
            this.order_date = new SimpleDateFormat("yyyy-MM-dd").parse(order_date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public String getCustomer_name() {
        return customer_name;
    }
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
    public String getShip_address() {
        return ship_address;
    }
    public void setShip_address(String ship_address) {
        this.ship_address = ship_address;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public Float getTax() {
        return tax;
    }
    public void setTax(Float tax) {
        this.tax = tax;
    }
    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }
    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }
    @Override
    public String toString () {
        return "Orders: [OrderId = " + orderId + ",order_date = " + order_date + ", customer_name = " + customer_name + ", ship_address = " + ship_address 
        + ", notes = " + notes + ", tax = " + tax + "]";
    }
    
    
}

// create table orders (
//     order_id int auto_increment not null,
//     order_date date not null,
//     customer_name varchar(128) not null,
//     ship_address varchar(128) not null,
//     notes text not null,
//     tax decimal (2,2) default 0.05,

//     primary key (order_id)
// );