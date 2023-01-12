create table orders (
    order_id char(8) not null,
    order_date date not null,
    customer_name varchar(128) not null,
    ship_address varchar(128) not null,
    notes text not null,
    tax decimal (2,2) default 0.05,

    primary key (order_id)
);

create table order_details (
    id int auto_increment not null,
    product varchar(64) not null,
    unit_price decimal (3,2) not null,
    discount decimal (3,2) default 1.00,
    quantity int not null,

    -- foreign key
    order_id char(8) not null,

    primary key (id),

    constraint fk_order_id
        foreign key(order_id) references orders(order_id)
);