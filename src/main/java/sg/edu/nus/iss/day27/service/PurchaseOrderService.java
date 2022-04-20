package sg.edu.nus.iss.day27.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.nus.iss.day27.exception.OrderTooLargeException;
import sg.edu.nus.iss.day27.models.LineItem;
import sg.edu.nus.iss.day27.models.PurchaseOrder;
import sg.edu.nus.iss.day27.repository.LineItemRepository;
import sg.edu.nus.iss.day27.repository.PurchaseOrderRepository;

@Service
public class PurchaseOrderService {
    
    @Autowired
    private PurchaseOrderRepository poRepo;

    @Autowired
    private LineItemRepository lineItemRepo;

    // create a transactional and rollback when there is a custom exception
    @Transactional(rollbackFor = OrderTooLargeException.class)
    public Integer createPurchaseOrder(PurchaseOrder po) throws OrderTooLargeException {
        // update purchase order into database and retrieve order Id
        Integer orderId = poRepo.addPurchaseOrder(po);
        double totalUnitPrice = 0;

        for (LineItem lineItem: po.getLineItem()) {
            totalUnitPrice = lineItem.getQuantity() * lineItem.getUnitPrice();
            if (totalUnitPrice > 200) {
                OrderTooLargeException ex = new OrderTooLargeException("Order has exceeded SGD200: %,.2f".formatted(totalUnitPrice));
                throw ex;
            }
            // update lineitem into database at the same time
            lineItemRepo.addLineItem(lineItem, orderId);
        }
        
        return orderId;
    }

}
