package vttp.day27workshop.model;

import java.beans.JavaBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;


public class Review {
    
    private List<EditEntry> edits = new LinkedList<>();
    private String user;
    private Integer rating;
    private String comment;
    private Integer gid;
    private String posted;
    private String name;
    private Date timestamp;
    private Boolean edited;

    public Boolean getEdited() {
        return edited;
    }
    public void setEdited(Boolean edited) {
        this.edited = edited;
    }
    public List<EditEntry> getEdits() {
        return edits;
    }
    public void setEdits(List<EditEntry> edits) {
        this.edits = edits;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getGid() {
        return gid;
    }
    public void setGid(Integer gid) {
        this.gid = gid;
    }
    public String getPosted() {
        return posted;
    }
    public void setPosted(String posted) {
        this.posted = posted;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString () {
        return "Game Details: [user = " + user + ", rating = " + rating + ", comment = " + comment + ", gid = " + gid 
        + ", posted = " + posted + ", name = " + name + ", timestamp = " + timestamp + ", edited = " + edited + ", edits = " + edits + "]";
    }

    //For Part C

    public static Review createForC (Document d) {

        //Store the edited array into a list <editentry> first
        List<EditEntry> editEntry = d.getList("edited", Document.class).stream().map(e -> EditEntry.create(e)).toList();

        Review review = new Review();
        review.setUser(d.getString("user"));
        review.setGid(d.getInteger("gid"));
        review.setName(d.getString("name"));
        review.setTimestamp(new Date());
        review.setRating(editEntry.get(editEntry.size()-1).getRating());
        review.setComment(editEntry.get(editEntry.size()-1).getComment());
        review.setPosted(editEntry.get(editEntry.size()-1).getPosted().toString());

        if (editEntry.size() > 0) {
            review.setEdited(true);
        } else {
            review.setEdited(false);
        }
    
        return review;
    }

    public JsonObject toJsonForC () {
        return Json.createObjectBuilder()
        .add("user", user)
        .add("gid", gid)
        .add("name", name)
        .add("timestamp", timestamp.toString())
        .add("edited", edited)
        .add("rating", rating)
        .add("comment", comment)
        .add("posted", posted.toString())
        .build();
    }

    //For Part D

    public static Review createForD (Document d) {

        //Store the edited array into a list <editentry> first
        List<EditEntry> editEntry = d.getList("edited", Document.class).stream().map(e -> EditEntry.create(e)).toList();
        System.out.println(">>>>>" + editEntry);

        Review review = new Review();
        review.setUser(d.getString("user"));
        review.setGid(d.getInteger("gid"));
        review.setName(d.getString("name"));
        review.setTimestamp(new Date());
        review.setRating(editEntry.get(editEntry.size()-1).getRating());
        review.setComment(editEntry.get(editEntry.size()-1).getComment());
        review.setPosted(editEntry.get(editEntry.size()-1).getPosted().toString());
        

        if (editEntry.size() > 0) {
            review.setEdited(true);
        } else {
            review.setEdited(false);
        }
        //THE ONLY DIFFERENCE IS GOT ONE MORE ALIST, WHICH WILL BE CHANGED TO JSON ARRAY LATER
        review.setEdits(editEntry);
    
        return review;
    }

    public JsonObject toJsonForD () {

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (EditEntry e : edits) {
            jsonArrayBuilder.add(objectBuilder.add("comment", e.getComment())
                                                .add("rating", e.getRating())
                                                .add("posted", e.getPosted().toString()));
        }

        return Json.createObjectBuilder()
        .add("user", getUser())
        .add("rating", getRating())
        .add("comment", getComment())
        .add("gid", getGid())
        .add("posted", getPosted().toString())
        .add("name", getName())
        .add("timestamp", getTimestamp().toString())
        .add("edits", jsonArrayBuilder)
        .build();
    }


}




    // public static Review create (Document d) {

    //     SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");

    //     Review review = new Review();
    //     review.set_id(d.getString("_id"));
    //     review.setUser(d.getString("user"));
    //     review.setRating(d.getInteger("rating"));
    //     review.setComment(d.getString("comment"));
    //     review.setGid(d.getInteger("gid"));



    //     return review;


        
    // }