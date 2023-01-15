drop database if exists eshop;

create database eshop;

grant all privileges on eshop.* to 'roy'@'%';

flush privileges;

create table customers (
	name varchar(32) not null,
	address varchar(128) not null,
	email varchar(128) not null,

	primary key(name)
);

insert into customers (name, address, email) values
	('fred', '201 Cobblestone Lane', 'fredflintstone@bedrock.com'),
	('sherlock', '221B Baker Street, London', 'sherlock@consultingdetective.org'),
	('spongebob', '124 Conch Street, Bikini Bottom', 'spongebob@yahoo.com'),
	('jessica', '698 Candlewood Land, Cabot Cove', 'fletcher@gmail.com'),
	('dursley', '4 Privet Drive, Little Whinging Surrey', 'dursley@gmail.com');

create table orders (
    order_id char(8) not null,
    order_date date not null,
	address varchar(128) not null,
	email varchar(128) not null,

    -- foreign key
    name varchar(32) not null,

    primary key (order_id),

    constraint fk_name
        foreign key (name) references customers(name)

);

-- FOR TABLES USING SIMILAR FOREIGN KEY, EG lineItems and order_status, have to specify in the constraint:
-- fk_lineItems_order_id and fk_order_status_order_id 
-- instead of just using fk_order_id for both

create table lineItems (
    id int auto_increment,
    item varchar(128) not null,
    quantity int not null,

    -- foreign key
    order_id char(8) not null,

    primary key (id),

    constraint fk_lineItems_order_id
        foreign key (order_id) references orders(order_id)

);

create table order_status (
    id int auto_increment,
    delivery_id varchar(128) not null,
    status enum ('pending', 'dispatched') default 'pending',
    status_update timestamp default current_timestamp,

    -- foreign key
    order_id char(8) not null,

    primary key (id),
    constraint fk_order_status_order_id
        foreign key (order_id) references orders(order_id)

);

create view order_status_count as 
    select c.name, os.status, count (os.status) as status_count
        from customers c
        join orders o
        on c.name = o.name
        join order_status os
        from orders o
        on os.order_id = o.order_id
        group by c.name, os.status 
