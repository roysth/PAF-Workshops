package vttp.day24workshop.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.day24workshop.model.OrderDetails;
import vttp.day24workshop.model.Orders;
import vttp.day24workshop.repositories.OrderDetailsRepo;
import vttp.day24workshop.repositories.OrderRepo;

@Service
public class OrderService {

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Transactional (rollbackFor = OrderException.class) 
    public void addNewOrder (Orders order, List<OrderDetails> odList) throws OrderException {


        //PLACE THE CONDITIONS FOR ROLLBACK HERE:
        //So if more than 5, it will rollback because it is stated above
        if (odList.size() > 5) {
        //This is the message that will be throw in the OrderException
        throw new OrderException("Exception for orderId %s".formatted(order.getOrderId()));
        }
   
        orderRepo.addOrder(order);

        orderDetailsRepo.addOrderDetails(odList, order.getOrderId());

    }



    
}
