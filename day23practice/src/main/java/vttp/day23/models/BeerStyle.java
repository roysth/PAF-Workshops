package vttp.day23.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class BeerStyle {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Create a method to be used to create the user's choice of style
    //SqlRowSet will retrieve the value in under the indicated column in the current row as a Timestamp object 
    //use rs to get the required value by just providing the column name
    public static BeerStyle create (SqlRowSet rs) {
        //create new BeerStyle object
        final BeerStyle style = new BeerStyle();
        style.setId(rs.getInt("id"));
        style.setName(rs.getString("style_name"));
        return style;
    }
    
    
}
 