package vttp.day23workshop.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Order {

    //The order_date in the schema uses DateTime instead of Date, hence have to use dateTime. Else can just use getstring
    private String order_id;
    //private Date order_date;
    private String order_date;
    private String customer_id;
    //Since in the schema, unit_price and quantity decimal type while discount is a double type, use Float will be easier
    private Float price;
    private Float cost;

    public String getOrder_id() {
        return order_id;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
    // public Date getOrder_date() {
    //     return order_date;
    // }
    // public void setOrder_date(Date order_date) {
    //     this.order_date = order_date;
    // }
    // //FOR DATE, REMEMBER THIS
    // public void setOrder_date(String order_date) {
    //     try {
    //         this.order_date = new SimpleDateFormat("yyyy-MM-dd").parse(order_date);
    //     } catch (Exception ex) {
    //         ex.printStackTrace();
    //     }
    // }
    public String getOrder_date() {
        return order_date;
    }
    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
    public Float getCost() {
        return cost;
    }
    public void setCost(Float cost) {
        this.cost = cost;
    }

    @Override
    public String toString () {
        return "Order Details: [order_id = " + order_id + ", order_datel = " + order_date + ", customer_id = " + customer_id + ", price = " + price 
        + ", cost = " + cost + "]";
    }


    public static Order create (SqlRowSet rs) {
        Order order = new Order();
        order.setOrder_id(rs.getString("order_id"));
        //order.setOrder_date(rs.getDate("order_date"));
        order.setOrder_date(rs.getString("order_date"));
        order.setCustomer_id(rs.getString("customer_id"));
        order.setPrice(rs.getFloat("total_price"));
        order.setCost(rs.getFloat("cost_price"));
        return order;

    }
    


    
}
