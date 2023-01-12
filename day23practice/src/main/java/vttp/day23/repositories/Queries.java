package vttp.day23.repositories;

public class Queries {

    //search from style table
    //select id, style_name from styles order by style_name asc;
    public static final String SQL_SELECT_STYLES = "select id, style_name from styles order by style_name asc";

    //This is based on the view table in workbench
    //search from the view table
    public static final String SQL_SELECT_BEWERIES_BY_STYLE = "select breweries from breweries_by_style where style_id= ?";

    
}


// -- To generate the view
// create view breweries_by_style as
// 	select s.id as style_id, br.name as breweries, s.style_name as styles 
// 		from styles s 
// 		join beers b 
// 		on s.id = b.style_id
// 		join breweries br 
// 		on br.id = b.brewery_id
// 		group by br.name, s.style_name, s.id
//         order by styles;

// -- Query to get from the view
// select breweries from breweries_by_style where style_id= 33;