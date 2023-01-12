package day21.workshop21.repositories;

public class Queries {

    //First query and Second query uses the same table, hence they share the same objects = just use one Model for both
    public static final String SQL_SELECT_LIST_OF_CUSTOMERS = "select concat (last_name, ' ', first_name) as name, job_title from customers limit ? offset ?";

    public static final String SQL_SELECT_DETAILS_BY_ID = "select id, company, concat (last_name, ' ', first_name) as name, job_title from customers where id=?";

    public static final String SQL_SELECT_ORDERS_BY_ID = "select order_date, shipping_fee from orders where customer_id =?";
    
}
