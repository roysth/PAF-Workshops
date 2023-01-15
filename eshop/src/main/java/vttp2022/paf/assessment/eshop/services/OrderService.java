package vttp2022.paf.assessment.eshop.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.respositories.OrderRepository;

public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    //Task 3b, 3d
    @Transactional (rollbackFor = OrderException.class)
    public void addNewOrder (Order order, Customer customer) throws OrderException {

        String orderId = UUID.randomUUID().toString().substring(0, 8);
        //Date is set in the Order model already
        order.setOrderId(orderId);
        order.setAddress(customer.getAddress());
        order.setEmail(customer.getEmail());

        try {
            orderRepository.insertOrder(order);
        } catch (Exception e) {
            throw new OrderException(e.getMessage(), e);
        }

    }
    
}
