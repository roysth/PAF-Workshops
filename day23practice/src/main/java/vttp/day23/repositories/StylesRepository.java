package vttp.day23.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.day23.models.BeerStyle;

//Need this one to use the Queries
import static vttp.day23.repositories.Queries.*;

@Repository
public class StylesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    //Get the style of beer from SQL and store it in a list
    public List<BeerStyle> getStyles () {

        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_STYLES);

        List<BeerStyle> styles = new LinkedList<>();
        //Use rs to read through the table row by row. If there is a row, it will return true
        while (rs.next()){
            styles.add(BeerStyle.create(rs));
        }
        return styles;

    }

}
