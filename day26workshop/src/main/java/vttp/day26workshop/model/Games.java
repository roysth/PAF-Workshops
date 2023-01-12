package vttp.day26workshop.model;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Games {

    private Integer gID;
    private String name;
    private Integer year;
    private Integer ranking;
    private Integer users_rated;
    private String url;
    private String image;
    

    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getRanking() {
        return ranking;
    }
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
    public Integer getUsers_rated() {
        return users_rated;
    }
    public void setUsers_rated(Integer users_rated) {
        this.users_rated = users_rated;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getgID() {
        return gID;
    }
    public void setgID(Integer gID) {
        this.gID = gID;
    }

    @Override
    public String toString () {
        return "Game Details: [gID = " + gID + ", name = " + name + ", year = " + year + ", ranking = " + ranking + ", users_rated = " + users_rated 
        + ", url = " + url + ", image = " + image + "]";
    }

    //THIS IS FOR PART A AND B
    public JsonObject toJson () {
        return Json.createObjectBuilder()
        .add("name", getName())
        .add("gid", getgID())
        .build();
    }

    public static Games create (Document doc) {

        Games games = new Games();
        games.setName(doc.getString("name"));
        games.setgID(doc.getInteger("gid"));
        return games;
    }

    //THIS IS FOR PART C 
    public JsonObject toJsonGameDetails () {
        return Json.createObjectBuilder()
        .add("name", getName())
        .add("gid", getgID())
        .add("year", getYear())
        .add("ranking", getRanking())
        .add("users_rated", getUsers_rated())
        .add("url", getUrl())
        .add("image", getImage())
        .build();
    }

    public static Games createGameDetails (Document doc) {

        Games games = new Games();
        games.setName(doc.getString("name"));
        games.setgID(doc.getInteger("gid"));
        games.setYear(doc.getInteger("year"));
        games.setRanking(doc.getInteger("year"));
        games.setUsers_rated(doc.getInteger("users_rated"));
        games.setUrl(doc.getString("url"));
        games.setImage(doc.getString("image"));

        return games;
    } 

}


// private String gID;
// private String name;
// private Integer year;
// private Integer ranking;
// private String users_rated;
// private String url;
// private String thumbnail;