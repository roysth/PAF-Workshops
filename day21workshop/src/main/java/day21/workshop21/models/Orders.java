package day21.workshop21.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Orders {

    //Get the customerId from the user, dont need to store it (Unless you wanna store it)
    //else don't need to put it here
    private String orderDate;
    private Double shippingFee;

    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public Double getShippingFee() {
        return shippingFee;
    }
    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }
    
    public static Orders create (SqlRowSet rs) {
        Orders orders = new Orders();
        orders.setOrderDate(rs.getString("order_date"));
        orders.setShippingFee(rs.getDouble("shipping_fee"));
        return orders;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("orderDate", getOrderDate())
            .add("shippingFee", getShippingFee())
            .build();
    }
    
}




