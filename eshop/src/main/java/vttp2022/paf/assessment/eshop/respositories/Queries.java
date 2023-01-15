package vttp2022.paf.assessment.eshop.respositories;

public class Queries {

    //Task 3a
    //Find customer by name form customers table
    public static final String SQL_FIND_CUSTOMER_BY_NAME = "select * from customers where name = ?";

    //Task 3d
    //Insert into orders table
    public static final String SQL_INSERT_INTO_ORDERS = "insert into orders (order_id, order_date, address, email, name) values (?, ?, ?, ?, ?)";
    
    //Task 3d
    //Insert into lineItems table
    public static final String SQL_INSERT_INTO_LINEITEMS = "insert into lineItems (item, quantity, order_id) values (?, ?, ?)";

    //Task 4
    //Don't have to include status_update as the timestamp will auto include 
    public static final String SQL_INSERT_INTO_ORDER_STATUS = "insert into order_status (order_id, delivery_id, status) values (?, ?, ?)";

    //Task 6
    //Using views
    public static final String SQL_GET_ORDER_STATUS_BY_NAME = "select * from order_status_count where name = ?";

    // //Not using views
    // public static final String SQL_GET_ORDER_STATUS = """
    //     select c.name, os.status, count (os.status) as status_count
    //     from customers c
    //     join orders o
    //     on c.name = o.name
    //     join order_status os
    //     from orders o
    //     on os.order_id = o.order_id
    //     group by c.name, os.status 
    //     """;

}
