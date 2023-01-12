package vttp2022.day21.day21.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.day21.day21.models.Book;
import static vttp2022.day21.day21.repositories.Queries.*;

@Repository
public class BookRepositories {

    //Inject in the JDBC template
    @Autowired
    private JdbcTemplate jdbcTeplate;

    //Our query will return a list of books based on the rating, hence
    //list
    public List<Book> getBooksByRating (Float rating){

        //Perform the Query
        //the rating will apply to the question mark in the query (refer to the Queries.java)
        //first question mark will be from rating. If have another question mark ,the other one
        //will be from after rating eg (SQL_SELECT_BOOKS_BY_RATING, rating, stars)
        final SqlRowSet rs = jdbcTeplate.queryForRowSet(SQL_SELECT_BOOKS_BY_RATING, rating);

        final List<Book> books = new LinkedList<>();

        //Attempt to move the cursor to the next row
        //While loop will run if condition is true
        //sqlrowset will go down the column and return true if there is data
        while (rs.next()) {
            //We have a record
            //created the book object in Book
            books.add(Book.create(rs));

        }
        return books;
        
    }

    public List<Book> getBooksByTitle (String bookName, Integer limit) {

        //Perform the query
        final SqlRowSet rs = jdbcTeplate.queryForRowSet(SQL_SELECT_BOOKS_BY_TITLE, "%%%s%%".formatted(bookName), limit);

        final List<Book> results = new LinkedList<>();
        while (rs.next())
        results.add(Book.create(rs));

        return results;
    }



    
}

/* %bookName%
String concatenation: "%" + bookName + "%"
Can also be written as : "%%%s%%".formatted(bookName)
%% will return %
%s will return the string, same as printf
*/