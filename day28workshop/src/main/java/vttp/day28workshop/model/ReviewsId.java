package vttp.day28workshop.model;

import org.bson.Document;
import org.bson.types.ObjectId;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class ReviewsId {

    private String reviewsId;

    public String getReviewsId() {
        return reviewsId;
    }

    public void setReviewsId(String reviewsId) {
        this.reviewsId = reviewsId;
    }
    
    @Override
    public String toString () {
        return "reviewsId = " + reviewsId;
    }

    public JsonObject tObject() {

        return Json.createObjectBuilder()
        .add("reviewsId", getReviewsId())
        .build();
    }

    //NOTE: Using ObjectId class instead of Document because we are not dealing with Document
    public static ReviewsId create (ObjectId r) {

        ReviewsId rId = new ReviewsId();
        rId.setReviewsId(r.toString());  

        return rId;

    }

}

//MONGO RESULTS:
// {
//     "_id" : ObjectId("639008057a2208f976a2aca8"),
//     "gid" : NumberInt(5),
//     "name" : "Acquire",
//     "year" : NumberInt(1964),
//     "ranking" : NumberInt(224),
//     "users_rated" : NumberInt(16946),
//     "url" : "https://www.boardgamegeek.com/boardgame/5/acquire",
//     "image" : "https://cf.geekdo-images.com/micro/img/SR4x4YjZk7UEc8HPUs8e55RhyqM=/fit-in/64x64/pic3299296.jpg",
//     "reviews" : [
//         ObjectId("63a7ffe6b3db2930ac911cf4")
//     ],
//     "timestamp" : ISODate("2022-12-28T10:28:07.259+0000")
// }

//reviews just contains an array of objectid. It is NOT a document
//However, if the objectid is changed to user:tom (shown below), then it will be considered a document
// {
//     "_id" : ObjectId("639008057a2208f976a2aca8"),
//     "gid" : NumberInt(5),
//     "name" : "Acquire",
//     "year" : NumberInt(1964),
//     "ranking" : NumberInt(224),
//     "users_rated" : NumberInt(16946),
//     "url" : "https://www.boardgamegeek.com/boardgame/5/acquire",
//     "image" : "https://cf.geekdo-images.com/micro/img/SR4x4YjZk7UEc8HPUs8e55RhyqM=/fit-in/64x64/pic3299296.jpg",
//     "reviews" : [
//         ObjectId("63a7ffe6b3db2930ac911cf4") -> user: tom **********
//     ],
//     "timestamp" : ISODate("2022-12-28T10:28:07.259+0000")
// }

