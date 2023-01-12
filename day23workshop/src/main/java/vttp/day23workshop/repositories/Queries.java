package vttp.day23workshop.repositories;


public class Queries {

    public static final String SQL_ORDER_DETAILS = """
        select 
        o.id as order_id, 
        date_format(o.order_date, \"%Y-%m-%d\") as order_date, 
        o.customer_id as customer_id, sum(od.quantity * od.unit_price - od.quantity * od.unit_price * od.discount) as total_price,
        sum(od.quantity * p.standard_cost) as cost_price
        from orders as o
        left join order_details as od
        on o.id = od.order_id
        left join products as p
        on od.product_id = p.id
        where o.id=? 
        """;
    
}

//SQL Query:
//Dont even need to create view!!
// select o.id as order_id, 
// date_format(o.order_date, "%Y-%m-%d") as order_date,
// o.customer_id as customer_id,
// sum(od.quantity * od.unit_price - od.quantity * od.unit_price * od.discount) as total_price,
// sum(od.quantity * p.standard_cost) as cost_price
// from orders as o
// left join order_details as od
// on o.id = od.order_id
// left join products as p
// on od.product_id = p.id
// where o.id=30;

//NOTE:
//\"%Y-%m-%d\"
//The \" is a escape character to quote %d/%m/%Y
//Java trick: use """ for long string