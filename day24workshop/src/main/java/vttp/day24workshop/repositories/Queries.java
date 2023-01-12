package vttp.day24workshop.repositories;

public class Queries {
    //For inserting into the table: orders
    //insert into orders (order_date, customer_name, ship_address, notes, tax) values ("1996-06-06", "john", "nus starbucks", "i want it now", 0.02);
    
    public static final String SQL_INSERT_ORDERS = "insert into orders (order_id, order_date, customer_name, ship_address, notes, tax) values (?, ?, ?, ?, ?, ?)";


    //For inserting into the table: order_details
    //insert into order_details (product, unit_price, discount, quantity, order_id) values ("coffee", 3.2, 1.00, 1, 1);
    public static final String SQL_INSERT_ORDER_DETAILS = "insert into order_details (product, unit_price, discount, quantity, order_id) values (?, ?, ?, ?, ?)";


}
