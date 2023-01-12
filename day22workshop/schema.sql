create table rsvp (
    name varchar(128) not null,
    email varchar(128) not null,
    phone varchar(28) not null,
    confirmation_date date not null,
    comments text,

    primary key (name)

);