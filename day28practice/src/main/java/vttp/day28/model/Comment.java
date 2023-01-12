package vttp.day28.model;

import org.bson.Document;

public class Comment {

    private String user;
    private Integer rating;
    private String text;

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

    @Override
    public String toString() {
        return "user: %s, rating: %d, text: %s".formatted(user, rating, text);
    }
    
    public static Comment create (Document d) {

        Comment comment = new Comment();
        comment.setUser(d.getString("user"));
        comment.setRating(d.getInteger("rating"));
        comment.setText(d.getString("c_text"));

        return comment;

    }
    
}



// {
//     "_id" : ObjectId("638edffa41e6755b26f181f5"),
//     "c_id" : "091910b8",
//     "user" : "PAYDIRT",
//     "rating" : NumberInt(6),
//     "c_text" : "A detailed tactical game on air and air to ground combat missions in the The Vietnam War/Second Indochina War.  Worth a look if the topic interests you.  The 2nd edition bookcase version is a cleaned up and better version.",
//     "gid" : NumberInt(6228)
// }