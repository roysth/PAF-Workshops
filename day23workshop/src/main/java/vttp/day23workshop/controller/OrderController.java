package vttp.day23workshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import vttp.day23workshop.model.Order;
import vttp.day23workshop.repositories.OrderRepo;

@Controller
@RequestMapping (path= "/")
public class OrderController {


    @Autowired
    OrderRepo orderRepo;
    
    //For HTML: very hard to get the input from form into path, hence cannot use (path="/order/{orderId}")
    //http://localhost:8080/order?orderId=30
    @GetMapping (path="/order")
    public String getOrderDetails (@RequestParam String orderId, Model model) {

        Optional<Order> o= orderRepo.getOrderDetails(orderId);

        //REquired cus optional is used
        Order order = o.get();

        //Because in SQL, it will return an empty table with no values inside. So sqlrowset will still produce
        //a result
        if (order.getPrice() == 0) {
            return "notfound";
        }

        model.addAttribute("orderId", orderId);
        model.addAttribute("orderDetails", order);

        return "results";


    }

}
