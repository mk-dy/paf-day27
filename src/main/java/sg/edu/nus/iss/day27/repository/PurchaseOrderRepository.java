package sg.edu.nus.iss.day27.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day27.models.PurchaseOrder;
import static sg.edu.nus.iss.day27.repository.Queries.*;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class PurchaseOrderRepository {
    
    @Autowired
    private JdbcTemplate template;

    // The whole idea here is to update the database whilst extracting the order Id from the database
    // because lineItem has order_id so we need to extract the order_id from purchase order and insert it into lineItem
    // -------------------------------------------------------------------------------------------------------------------
    /*
    public Integer addPurchaseOrder(final PurchaseOrder po) {
        int added = template.update(SQL_INSERT_PURCHASE_ORDER,po.getName(),po.getEmail());
        
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ORDER_ID, po.getName(),po.getEmail());
        rs.next();
        return rs.getInt("ord_id");
        // returns the order id;
        }
    */

    public Integer addPurchaseOrder(final PurchaseOrder po){
        // jdbc has a class called keyholder
        KeyHolder keyholder = new GeneratedKeyHolder();
        template.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT_PURCHASE_ORDER, 
                Statement.RETURN_GENERATED_KEYS);
            
            // filling up the ? in sql statement
            ps.setString(1, po.getName());
            ps.setString(2, po.getEmail());
            // the prepared statement
            return ps;
            
        }, keyholder);

        BigInteger bigint = (BigInteger) keyholder.getKey();
        return bigint.intValue();
    }
    
}   
