package vttp.day27practice.models;

import org.bson.Document;

public class SearchResults {

    private String user;
    private Integer rating;
    private String text;
    private Float score;

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
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Float getScore() {
        return score;
    }
    public void setScore(Float score) {
        this.score = score;
    }

    
    @Override
    public String toString () {
        return "Comment [user = " + user + ", rating = " + rating + ", text = " + text + ", score = " + score + "]";
    }

    public static SearchResults create (Document d) {
        SearchResults sr = new SearchResults();
        sr.setUser(d.getString("user"));
        sr.setRating(d.getInteger("rating"));
        sr.setText(d.getString("c_text"));
        //There is no getFloat, hence getDouble first then change it into float
        sr.setScore(d.getDouble("score").floatValue());

        return sr;

    }


    
}

// "_id" : ObjectId("638edffd41e6755b26f28abd"),
// "c_id" : "119c1af6",
// "user" : "rmiczek",
// "rating" : NumberInt(8),
// "c_text" : "If you like puzzle games with pressure than this game is for you.  Comparable to the Ubongo games but there are multiply solutions to each puzzle in this game.  This adds a press your luck component to this game as you may find a solution but you may want to find a better one that yields more points.  That being said this is a fun puzzle game that is good but falls a bit behind the ubongo games for me  as frankly I just have more fun with ubongo extreme and 3D for reasons I just can't explain.",
// "gid" : NumberInt(136089),
// "score" : 0.7826086956521738