package day21.workshop21.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import day21.workshop21.models.Details;
import day21.workshop21.models.Orders;
import day21.workshop21.repositories.DetailsRepo;
import day21.workshop21.repositories.OrdersRepo;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;

@RestController

@RequestMapping(path = "/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)

public class ControllerREST {

    @Autowired
    private DetailsRepo detailsRepo;

    @Autowired
    private OrdersRepo ordersRepo;  

    @GetMapping 
    //@PathVariable is more suitable for customer_ID when it doesnt change api/customers/<custoer_ID>
    //If want to use it in this case, need to add (path = "{limit}/{offset}") in GetMapping and change 
    //@RequestParm or @PathVariable
    //Use @Requestparam is better for such instances http://localhost:8080/api/customers?limit=10&offset=10
    //RequestEntity <String> for Json
    public ResponseEntity <String> getCustomerList (@RequestParam(required = false, name = "limit") Integer limit, @RequestParam (required = false, name = "offset") Integer offset) {

        if (limit == null){
            limit = 5;
        }

        if (offset == null) {
            offset = 0;
        }

        //Query the DB for the list of customers
        //Create a new List for the query
        List<Details> list = detailsRepo.getListOfCustomers(limit, offset);

        //Create the json builder first and build the result
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Details d: list){
            arrayBuilder.add(d.toJsonOne());
        }
        JsonArray result = arrayBuilder.build();
        
        return ResponseEntity 
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(result.toString());
    }

    //http://localhost:8080/api/customers/1
    @GetMapping (path = "{customerID}")
    //the customerID need not be the same name as the id in Details
    public ResponseEntity <String> getDetailsOfCustomer (@PathVariable String customerID) {

        List<Details> details = detailsRepo.getDetailsById(customerID);
 
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (Details d: details){
                arrayBuilder.add(d.toJson());
            }
            JsonArray result = arrayBuilder.build();

        return ResponseEntity 
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(result.toString());

    }
    //http://localhost:8080/api/customers/selectorders/28
    @GetMapping (path = "/selectorders/{customerID}")
    public ResponseEntity <String> selectOrders (@PathVariable String customerID) {

        JsonArray result = null;
        try {

        //Query the database for the orders
        List<Orders> orders = ordersRepo.getOrdersById(customerID);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Orders o: orders){
            arrayBuilder.add(o.toJson());
        }
        result = arrayBuilder.build();

        } catch (IndexOutOfBoundsException e) {

            return ResponseEntity 
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error_mesg\": \"record not found\"}");

        }


        return ResponseEntity 
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(result.toString());
    }

}
