package vttp2022.paf.assessment.eshop;

import java.io.StringReader;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.eshop.models.OrderStatusCount;

public class Utilities {

    // ------------- METHODS TO CREATE DIFFERENT JSON OBJECTS ------------- \\

    //Task 3a and Task 4 (in warehouseservice):
    //To convert the request body string to jsonobject 
    public static JsonObject toJson (String payload) {
        JsonReader reader = Json.createReader(new StringReader(payload));
        return reader.readObject();
    }

    //Task 4:
    //Create json object to be sent to warehouse
    public static JsonObject toJson (Order order, String createdBy) {

        //Create the jsonarray to be placed inside the json object
        //jsonarraybuilder requires a list of json object as input
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(
            order.getLineItems()
            .stream()
            .map(v -> Json.createObjectBuilder()
                .add("item", v.getItem())
                .add("quantity", v.getQuantity())
                .build())
            .toList());
        JsonArray lineItemsArray = arrayBuilder.build();

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("orderId", order.getOrderId())
            .add("name", order.getName())
            .add("address", order.getAddress())
            .add("email", order.getEmail())
            .add("lineItems", lineItemsArray)
            .add("createdBy", createdBy);
        JsonObject object = objectBuilder.build();

        return object;
    }

    //Task 5:
    //Converting the OrderStatus object to json object in the Controller
    /*
    Return json payload for
    If dispatch successful:
        {
            "orderId"
            "deliveryId"
            "status"
        }
    If dispatch unsuccessful:
        {
            "orderId"
            "status"
        }
    */
    public static JsonObject toJson(OrderStatus orderStatus) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("orderId", orderStatus.getOrderId())
            .add("status", orderStatus.getStatus());
        if ("dispatched".equals(orderStatus.getStatus())) {
            objectBuilder.add("deliveryId", orderStatus.getDeliveryId());
        }
        JsonObject object = objectBuilder.build();

        return object;
    }

    //Task 6 (Used in Controller):
    public static JsonObject toJson (OrderStatusCount orderStatusCount) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("name", orderStatusCount.getName())
            .add("dispatched", orderStatusCount.getDispatched())
            .add("pending", orderStatusCount.getPending());
        
        JsonObject object = objectBuilder.build();

        return object;
    }



    // ------------- METHODS TO CREATE DIFFERENT OBJECTS ------------- \\

    //Task 3a:
    public static Customer createCustomer (SqlRowSet rs) {
        Customer customer = new Customer();
        customer.setName(rs.getString("name"));
        customer.setAddress(rs.getString("address"));
        customer.setEmail(rs.getString("email"));
        return customer;
    }

    //Task 3b:
    //Since the payload is in json, the input for the method will be jsonobject
    /* Request payload:
		{name: "fred", 
		lineItems: [
		{item: "cookie", quantity: 1},
		{item: "candies", quantity: 2},
		]
        }
    */
    //Set the above items into a Order object
    public static Order createOrder (JsonObject json) {
        //The :: operator is the method reference operator used to refer a method without invoking it
        List <LineItem> lineItemList = json.getJsonArray("lineItems")
            .stream()
            .map(o -> o.asJsonObject())
            .map(Utilities::createLineItem)
            .toList();
        Order order = new Order();
        order.setLineItems(lineItemList);
        order.setName(json.getString("name"));

        return order;
    }
    //To be used in createOrder above 
    public static LineItem createLineItem (JsonObject json) {
        LineItem lineItem = new LineItem();
        lineItem.setItem(json.getString("item"));
        lineItem.setQuantity(json.getInt("quantity"));
        return lineItem;
    }

    //Task 4:
    /*
    Warehouse server will send the json payload if successful:
        {
            "orderId": <order id>
            "deliveryId": <delivery id>
        }
    order_status should contain:
        order_id: from server payload
        deliver_id: from server payload
        status: manually input - dispatched or pending (unsuccessful)
        status_update: done in sql timestamp
    */
    public static OrderStatus createOrderStatus (JsonObject json) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(json.getString("order_id"));
        orderStatus.setDeliveryId(json.getString("delivery_id"));
        orderStatus.setStatus("dispatched");
        return orderStatus;
    }

    //Task 6 (Used in OrderStatusRepository):
    public static OrderStatusCount createOrderStatusCount (SqlRowSet rs) {
        OrderStatusCount orderStatusCount = new OrderStatusCount();
        orderStatusCount.setName(rs.getString("name"));

        //Using "if..else if" here to play safe and ensure that the results is what we want
        if ("pending".equals(rs.getString("status"))) {
            orderStatusCount.setPending(rs.getInt("status_count"));
        } 
        else if ("dispatched".equals(rs.getString("status"))) {
            orderStatusCount.setPending(rs.getInt("status_count"));
        }

        return orderStatusCount;
    }


    // ------------- METHOD TO CREATE ERROR MESSAGES ------------- \\
    public static JsonObject errorMessage (String message) {
        return Json.createObjectBuilder()
            .add("Error", message)
            .build();
    }
    
}
/*
if..else: for only 2 conditions:

if (x > 0) {
    System.out.println("x is positive");
} else {
    System.out.println("x is not positive");
}
if..else if: for multiple conditions:

if (x > 0) {
    System.out.println("x is positive");
} else if (x < 0) {
    System.out.println("x is negative");
} else {
    System.out.println("x is zero");
}
*/