package vttp.workshop22.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.workshop22.models.Rsvp;

import static vttp.workshop22.repositories.Queries.*;



@Repository
public class RsvpRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Task 2a and 2b
    //Get all the RSVPs if no names are given
    public List <Rsvp> getAllRsvp (String name) {

        final List<Rsvp> results = new LinkedList<>();

        // final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_ALL_RSVP);
        // final SqlRowSet rsn = jdbcTemplate.queryForRowSet(SQL_SEARCH_BY_NAME, name);
        
        // if (name == null) {
        //     while (rs.next()) {
        //         results.add(Rsvp.create(rs));
        //     }
        // } else {
        //     while (rsn.next()) {
        //         results.add(Rsvp.create(rsn));
        //     }
        // }
        //Shorter way of writing:

        SqlRowSet rs = null;

        if (name == null) {
            rs = jdbcTemplate.queryForRowSet(SQL_GET_ALL_RSVP);
        } else {
            rs = jdbcTemplate.queryForRowSet(SQL_SEARCH_BY_NAME, name);
        }
        while (rs.next()) {
            results.add(Rsvp.create(rs));
        }
        return results;
    }

    //Task 2c
    //Create a new RSVP
    //NOTE: Create and updating uses "update" jdbcTemplate
    public Integer createRSVP (Rsvp rsvp) {

        return jdbcTemplate.update(SQL_ADD_NEW_RSVP, rsvp.getName(), rsvp.getEmail(), rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments());
    }

    //To authenticate
    public boolean authenticate (String name) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_AUTHENTICATAION, name);

        //If name is present
        if (rs.next()) {
            return rs.getBoolean("auth_state");
            
        } 
        return false; 
    }

    //Task 2d
    //Update the RSVP
    public Boolean updateRSVP (Rsvp rsvp) {

        //add returns the number of rows affected by update()
        Integer add = jdbcTemplate.update(SQL_ADD_NEW_RSVP, rsvp.getName(), rsvp.getEmail(), rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments());

        //If successfully added, add > 1, true
        return add > 0;
    }

    //To authenticate via email
    public boolean authenticateEmail (String email) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_AUTHENTICATAION_EMAIL, email);
    
        if (rs.next()) {
            return rs.getBoolean("auth_state");
                
        } 
        return false; 
    }



    
    
}
