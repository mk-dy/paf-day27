package sg.edu.nus.iss.day27.models;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import sg.edu.nus.iss.day27.models.LineItem;

import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class PurchaseOrder {
    
    private Integer orderId;
    private String name;
    private String email;
    private List<LineItem> lineItem = new LinkedList<>();

    
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<LineItem> getLineItem() {
        return lineItem;
    }
    public void setLineItem(List<LineItem> lineItem) {
        this.lineItem = lineItem;
    }

    public static PurchaseOrder create(String jsonString) throws Exception {
        JsonObject body;
        InputStream is = new ByteArrayInputStream(jsonString.getBytes());
        JsonReader reader = Json.createReader(is);
        body = reader.readObject(); // we have our filled up jsonobject data here
        
        PurchaseOrder po = new PurchaseOrder();
        // nothing to set Order Id to yet and order Id is autogenerated anyway.
        po.setName(body.getString("name"));
        po.setEmail(body.getString("email"));
        // i need to stuff the lineItems in
        // so create a List<LineItem> first
        List<LineItem> lineItemList = new LinkedList<>();
        JsonObject item;
        JsonArray jArr = body.getJsonArray("lineitems");
        for (int i = 0; i < jArr.size(); i++) {
            item = jArr.getJsonObject(i);

            LineItem lineItem = new LineItem();
            lineItem.setDescription(item.getString("description"));
            lineItem.setQuantity(item.getInt("quantity"));
            lineItem.setUnitPrice((double)item.getJsonNumber("unitPrice").doubleValue());

            lineItemList.add(lineItem);
        }
        po.setLineItem(lineItemList);

        return po;
        // filled up object
    }
    
}
