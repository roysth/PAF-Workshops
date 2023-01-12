package vttp.day27workshop.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class EditEntry {

    private String comment;
    private Integer rating;
    private Date posted;

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public Date getPosted() {
        return posted;
    }
    public void setPosted(Date posted) {
        this.posted = posted;
    }

    @Override
    public String toString () {
        return "Edited Items: [comment = " + comment + ", rating = " + rating + ", date posted = " + posted + "]";
    }

    public JsonObject toJson () {
        return Json.createObjectBuilder()
        .add("comment", getComment())
        .add("rating", getRating())
        .add("posted", getPosted().toString())
        .build();
    }

    public static EditEntry create (Document doc) {

        //SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");

        EditEntry editEntry = new EditEntry();
        editEntry.setComment(doc.getString("comment"));
        editEntry.setRating(doc.getInteger("rating"));
        editEntry.setPosted(doc.getDate("posted"));
  
        return editEntry;

    }





    
}
// public static EditEntry create (Document doc) {

//     SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");

//     EditEntry editEntry = new EditEntry();
//     editEntry.setComment(doc.getString("comment"));
//     editEntry.setRating(doc.getInteger("rating"));
//     try {
//         editEntry.setPosted(formatter.parse(doc.getString("posted")));
//     } catch (ParseException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//     }
//     return editEntry;

// }