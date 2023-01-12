package vttp.day24.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.day24.models.PurchaseOrder;
import vttp.day24.repositories.LineItemRepo;
import vttp.day24.repositories.PurchaseOrderRepo;

@Service
public class OrderService {
    
    @Autowired
    private PurchaseOrderRepo poRepo;

    @Autowired
    private LineItemRepo liRepo;

    @Transactional(rollbackFor = OrderException.class)
    public void createNewOrder(PurchaseOrder po) throws OrderException{

        // Generate orderId
        String orderId = UUID.randomUUID().toString().substring(0, 8);
        System.out.printf(">>>> OrderId: %s\n", orderId);

        po.setOrderId(orderId);

        // Create the purchaseOrder
        poRepo.insertPurchaseOrder(po);

        //This is the message that will be throw in the OrderException
        throw new OrderException("Exception for orderId %s".formatted(orderId));

        // Create the associated line items
        //liRepo.addLineItems(po.getLineItems(), orderId);

    } // Commit
    
}
