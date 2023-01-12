package vttp.workshop22.repositories;

public class Queries {
    
    //Read
    //select * from rsvp;
    public static final String SQL_GET_ALL_RSVP = "select * from rsvp";

    //select * from rsvp where name like '%tate%';
    public static final String SQL_SEARCH_BY_NAME = "select * from rsvp where name like ?";

    //Create
    //insert into rsvp values ("akira", "akira@hotmail.com", "99999999", "2021-03-01", "nice");
    public static final String SQL_ADD_NEW_RSVP = "insert into rsvp values (?, ?, ?, ?, ?)";

    //To check if the user is added
    //select count(*) > 0 as auth_state from rsvp where name = "akira";
    public static final String SQL_AUTHENTICATAION = "select count(*) > 0 as auth_state from rsvp where name = ?";

    //Update
    //TODO: Think of how to update using html form and updating more than one item
    //update rsvp set email = 'andrewdate@gmail.com' where name = 'andrew tate';
    public static final String SQL_UPDATE_RSVP = "update rsvp set email = ?, phone = ?, confirmation_date =?, comments = ? where name = ?";

    public static final String SQL_AUTHENTICATAION_EMAIL = "select count(*) > 0 as auth_state from rsvp where email = ?";

    //Count
    //select count(distinct name) from rsvp; OR select count(*) as total from rsvp
    public static final String SQL_GET_TOTAL_NUMBER_OF_RSVP = "select count(distinct name) from rsvp;";
}

