package vttp.day24.repositories;

public class Queries {
    
    public static final String SQL_INSERT_PURCHASE_ORDER = "INSERT INTO purchase_order (order_id, name, order_date) VALUES (?,?,?)";
    public static final String SQL_INSERT_LINE_ITEM = "INSERT INTO line_item (description, quantity, order_id) VALUES (?,?,?)";
}



/*
 * In SQL, need to insert foreign key 
 * 
 * Dont need item_id cus auto-increment
 */
