package vttp.day28workshop.model;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Game {

    private Integer gid;
    private String name;
    private Integer year;
    private Integer rank;
    private Integer usersRated;
    private String url;
    private String image;
    private List<ReviewsId> reviews;
    private String timestamp;

    public Integer getGid() {
        return gid;
    }
    public void setGid(Integer gid) {
        this.gid = gid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getRank() {
        return rank;
    }
    public void setRank(Integer rank) {
        this.rank = rank;
    }
    public Integer getUsersRated() {
        return usersRated;
    }
    public void setUsersRated(Integer usersRated) {
        this.usersRated = usersRated;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public List<ReviewsId> getReviews() {
        return reviews;
    }
    public void setReviews(List<ReviewsId> reviews) {
        this.reviews = reviews;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "gid: %s, name: %s, year: %s, rank: %s, userRated: %s, url: %s, image: %s, reviews: %s, timestamp: %s"
        .formatted(gid, name, year, rank, usersRated, url, image, reviews, timestamp);
    }

    public static Game create (Document d) {

        //NOTE:It is not a document.class because it is mainly objectId. Refer to  the notes below
        List <ReviewsId> reviewsIdList = d.getList("reviews", ObjectId.class).stream().map(r -> ReviewsId.create(r)).toList();

        Game game = new Game();
        game.setGid(d.getInteger("gid"));
        game.setName(d.getString("name"));
        game.setYear(d.getInteger("year"));
        game.setRank(d.getInteger("ranking"));
        game.setUsersRated(d.getInteger("users_rated"));
        game.setUrl(d.getString("url"));
        game.setImage(d.getString("image"));
        game.setReviews(reviewsIdList);
        game.setTimestamp(d.getDate("timestamp").toString());

        return game;
    }

    public JsonObject toJson () {

        //Dont need jsonobject because we are not building a jsonobject inside the "reviews". Not a requirement
        //JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (ReviewsId r : reviews) {
            jsonArrayBuilder.add("/review/" + r.getReviewsId());
            //jsonArrayBuilder.add(objectBuilder.add("reviewId", r.getReviewsId()));

        }

        return Json.createObjectBuilder()
        .add("gid", gid)
        .add("name", name)
        .add("year", year)
        .add("ranking", rank)
        .add("users_rated", usersRated)
        .add("url", url)
        .add("image", image)
        .add("reviews", jsonArrayBuilder)
        .add("timestamp", timestamp)
        .build();
    }
    
}

