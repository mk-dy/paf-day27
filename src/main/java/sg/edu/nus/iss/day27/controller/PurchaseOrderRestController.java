package sg.edu.nus.iss.day27.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.day27.models.PurchaseOrder;
import sg.edu.nus.iss.day27.service.PurchaseOrderService;

@RestController
@RequestMapping(path="/api/order")
public class PurchaseOrderRestController {
    
    @Autowired
    private PurchaseOrderService poSvc;

    // post json data to your database
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postOrder(@RequestBody String json) {

        JsonObject resp;
        try {
            // Fill up purchaseorder
            PurchaseOrder purchaseOrder = PurchaseOrder.create(json);
            // create orderId
            Integer orderId = poSvc.createPurchaseOrder(purchaseOrder);
            resp = Json.createObjectBuilder()
                    .add("orderId",orderId)
                    .build();
            return ResponseEntity.ok(resp.toString());

        } catch (Exception e) {
            e.printStackTrace();
            resp = Json.createObjectBuilder()
                    .add("error",e.getMessage())
                    .build();

            return ResponseEntity.badRequest().body(resp.toString());
        }
    }

}
