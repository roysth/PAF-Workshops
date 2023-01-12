package day21.workshop21.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;


/*Not going to get all the details. Just going to get:
company, last_name, first_name, job_title
*/
public class Details {

    private String id;
    private String company;
    //This is the name for the first and last name combined
    private String name;
    private String jobTitle;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    //Need to create one more create method and toJson method for get Customer List
    //Cannot share the same method as the values are different

    //Method to create Detail object
    public static Details createOne (SqlRowSet rs) {
        Details detail = new Details();
        detail.setName(rs.getString("name"));

        return detail;
    }

    //Method to create json object
    public JsonObject toJsonOne () {
        return Json.createObjectBuilder()
            .add("name", getName())
            .build();
    }
    
//Create another method for Select Details by ID
    public static Details create (SqlRowSet rs) {
        Details detail = new Details();
        detail.setId(rs.getString("id"));
        detail.setCompany(rs.getString("company"));
        detail.setName(rs.getString("name"));
        detail.setJobTitle(rs.getString("job_title"));
        return detail;
    }

    public JsonObject toJson () {
        return Json.createObjectBuilder()
            .add("id", getId())
            .add("company", getCompany())
            .add("name", getName())
            .add("jobTitle", getJobTitle())
            .build();
    }
    
}

