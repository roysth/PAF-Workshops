package vttp2022.day21.day21.repositories;


public class Queries {

    //Place the SQL query here
    public static final String SQL_SELECT_BOOKS_BY_RATING = 
    "select book_id, title, description, rating,image_url from book2018 where rating >= ? order by title";
    

    //cannot put %?% in the SQL query here
    public static final String SQL_SELECT_BOOKS_BY_TITLE = 
    "select book_id, title, description, rating,image_url from book2018 where title like ? limit ?";

}
