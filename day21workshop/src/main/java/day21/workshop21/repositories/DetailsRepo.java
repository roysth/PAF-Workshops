package day21.workshop21.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import day21.workshop21.models.Details;
import static day21.workshop21.repositories.Queries.*;

//The List Of Customers and Details by ID query will be done here since they share the same table
@Repository
public class DetailsRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Details> getListOfCustomers (Integer limit, Integer offset) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_LIST_OF_CUSTOMERS, limit, offset);

        final List<Details> list = new LinkedList<>();
        
        while (rs.next()){
            list.add(Details.createOne(rs));
        }
        return list;
    }


    public List<Details> getDetailsById (String id) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_DETAILS_BY_ID, id);

        final List<Details> details = new LinkedList<>();

        while (rs.next()){
            details.add(Details.create(rs));
        }

        return details;
    }

}
