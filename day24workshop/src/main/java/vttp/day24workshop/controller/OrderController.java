package vttp.day24workshop.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vttp.day24workshop.model.OrderDetails;
import vttp.day24workshop.model.Orders;
import vttp.day24workshop.service.OrderException;
import vttp.day24workshop.service.OrderService;

@Controller
@RequestMapping (path = "/")
public class OrderController {

    @Autowired
    OrderService orderService;
    
    //The path ="/addOrderInfo" is for the form in index.html
    //After the button is in the form is clicked, it goes to the orderdetails.html
    @PostMapping (path ="/addOrderInfo")
    public String addOrderInfo (@RequestBody MultiValueMap <String, String> form, Model model, HttpSession session) {

        //Create the Orders object from the session
        //Name the orders object in the session "orders"
        Orders orders = (Orders) session.getAttribute("orders");
        if (orders == null) {
            System.out.println("This is a new session!");
            //Since the order object from the session is empty, create a new order object
            //
            orders = new Orders();
            //Set the 8 digit UUID as the orderID
            orders.setOrderId(UUID.randomUUID().toString().substring(0, 8));
            orders.setOrder_date(form.getFirst("order_date"));
            orders.setCustomer_name(form.getFirst("customer_name"));
            orders.setShip_address(form.getFirst("ship_address"));
            orders.setNotes(form.getFirst("notes"));
            orders.setTax(Float.parseFloat(form.getFirst("tax"))); 
            session.setAttribute("orders", orders);
        }
        //Dont need to put in model since not showing it on any html page
        return "orderdetails";
    }

    @PostMapping (path = "/addDetails")
    public String addOrder (@RequestBody MultiValueMap <String, String> form, Model model, HttpSession session) {

        //Create a List of OrderDetails from the session
        //Name the orderDetailsList "details" under this session
        List <OrderDetails> orderDetailsList = (List <OrderDetails>) session.getAttribute("details");
        if (orderDetailsList == null) {
            System.out.println("This is a new session!");
            //Since the session just started, orderDetailsList is empty, hence create one list
            orderDetailsList = new LinkedList<>();
            session.setAttribute("details", orderDetailsList);
        }
        //Once the list is created, can just keep adding 
        //REASON WHY IT IS WRITTEN HERE VS THE ONE ABV:
        //If put inside the if statement, then only when the orderDetailsList is empty, the OrderDetails object is created
        //After one OrderDetails object is created, the orderDetailsList will NOT be null, as such no more OrderDetails object
        //can be created. So to create a list of OrderDetails object, it has to be placed outside
        String product = form.getFirst("product");
        Float unit_price  = Float.parseFloat(form.getFirst("unit_price"));
        Float discount  = Float.parseFloat(form.getFirst("discount"));
        Integer quantity = Integer.parseInt(form.getFirst("quantity"));
        //The OrderDetails method is defined in the OrderDetails Model
        orderDetailsList.add(new OrderDetails(product, unit_price, discount, quantity));

        //Saving into the model
        for (OrderDetails od: orderDetailsList) {
            System.out.printf("TESTTEST: product: %s\n", od.getProduct());  
        }
        
        model.addAttribute("orderDetailsList", orderDetailsList);
        //Binds the details session to the orderDetailsList 
        session.setAttribute("details", orderDetailsList);

        return "orderdetails";
    }

    @PostMapping (path = "/submit")
    public String addOrderDetailList (Model model, HttpSession session)   {

        List <OrderDetails> orderDetailsList = (List <OrderDetails>) session.getAttribute("details");
        Orders order = (Orders) session.getAttribute("orders");


        for (OrderDetails f : orderDetailsList) {
            System.out.println(">>>>>>>>" + f.getProduct());
        }
        

        try {
            orderService.addNewOrder(order, orderDetailsList);
        } catch (OrderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //Return error page if there is more than 5 items
            return "errorpage";
        }

        //Destroy the session
        session.invalidate();

        //To be used in the confirmation page
        model.addAttribute("orderID", order.getCustomer_name());

        return "confirmation";

    }
    
    
}
    