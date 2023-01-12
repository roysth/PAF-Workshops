package vttp.day28.model;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

public class Game {

    //Choose the data you want from Game
    private String name;
    private String image;

    //Since we are merging Comments into Game, create a list to hold the data from Comment
    //Created a model for Comment as there is 2 variables to be captured
    private List<Comment> comment = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "name: %s, image: %s, comment: %s".formatted(name, image, comment);
    }

    public static Game create (Document d) {

        System.out.println(">>>> " + d.toJson());

        //Because the name and image from games is placed under _id
        //$group: {_id: {name: "$name", image:"$image"}, commentsnew: {$push: "$commentsnew"}},
        //We will need to write this: Document id = d.get("_id", Document.class)
        //After that, assigning it to the variables
        //However if we just want name,
        //$group: {_id: "$name", commentsnew: {$push: "$commentsnew"}}
        //then can just do:
        //game.setName(d.getString("_id")), without writing the Document id = d.get("_id", Document.class)
        Document id = d.get("_id", Document.class);
        Game game = new Game();
        game.setName(id.getString("name"));
        game.setImage(id.getString("image"));

        return game;
    }
}


//MONGO RESULTS:


// {
//     "_id" : {
//         "name" : "Twilight Imperium (Third Edition)",
//         "image" : "https://cf.geekdo-images.com/micro/img/DX1nX1JYHVRQXhoPMaDgaHfezsg=/fit-in/64x64/pic4128153.jpg"
//     },
//     "commentsnew" : [
//         {
//             "_id" : ObjectId("638edffb41e6755b26f193dc"),
//             "c_id" : "0a065e77",
//             "user" : "tawnos76",
//             "rating" : NumberInt(10),
//             "c_text" : "My favorite along side of Adv Civ and is very fun to add variants to. 4X I love a long epic game and this fits the bill perfectly.   I could play this anytime anywhere and as many times as possible and still not get tired of it.  Many variants loaded up here and on my mediafire account.  Just ask and I can give you access to it.   Fantasy Flights Big Box collection",
//             "gid" : NumberInt(12493)
//         },
//         {
//             "_id" : ObjectId("638edffa41e6755b26f18ba3"),
//             "c_id" : "09970eaa",
//             "user" : "Recurcive",
//             "rating" : NumberInt(10),
//             "c_text" : "My favorite game to play. Sure, it has some balancing issues, but in terms of involvement, fun and epicness, I've never played anything like it",
//             "gid" : NumberInt(12493)
//         }
//     ]
// }