package sg.edu.nus.iss.day27.repository;

public interface Queries {
    
    public static final String SQL_INSERT_PURCHASE_ORDER = "insert into po(name, email) values (?, ?)";

    public static final String SQL_INSERT_LINE_ITEM = "insert into line_item(description, quantity, unit_price, ord_id) values (?,?,?,?)";

    public static final String SQL_SELECT_ORDER_ID = "select ord_id from po where name = ? and email = ?";
}
