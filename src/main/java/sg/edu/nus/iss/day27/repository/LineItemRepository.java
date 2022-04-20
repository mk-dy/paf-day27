package sg.edu.nus.iss.day27.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day27.models.LineItem;
import static sg.edu.nus.iss.day27.repository.Queries.*;

@Repository
public class LineItemRepository {
    
    @Autowired
    private JdbcTemplate template;

    // updates lineItem into the database
    public boolean addLineItem(LineItem lineItem, Integer orderId) {
        int added = template.update(
            SQL_INSERT_LINE_ITEM, 
            lineItem.getDescription(), 
            lineItem.getQuantity(), 
            lineItem.getUnitPrice(), 
            orderId );
            
        return added > 0;
        
    }
}
