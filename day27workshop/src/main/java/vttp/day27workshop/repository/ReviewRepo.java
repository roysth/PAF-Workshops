package vttp.day27workshop.repository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import vttp.day27workshop.model.EditEntry;
import vttp.day27workshop.model.Review;

@Repository
public class ReviewRepo {

    private static final String C_REVIEWS = "reviews";

    @Autowired
    private MongoTemplate mongoTemplate;

    //Part A: Add a new review
    // db.reviews.insertOne({
    // user: "Tom",
    // rating: 6,
    // comment: "good man",
    // gid: 3,
    // posted: new Date(),
    // name: "thor"
    // });
    
    public void insertReview (String user, Integer rating, String comment, Integer gid, String name) {

        //Create a Document to me inserted
        Document doc = new Document();
        doc.put("user", user);
        doc.put("rating", rating);
        doc.put("comment", comment);
        doc.put("gid", gid);
        doc.put("posted", new Date());
        doc.put("name", name);

        mongoTemplate.insert(doc, C_REVIEWS);
    }

    //Part A, to check if the gid inserted by user is present in the Collectioned titled "game"
    //db.game.find({gid: 1}, {_id: 0, gid: 1, name: 1}).count
    //SHORT CUT: If it is present, then the count should be 1 because 1 row is found
    //count returns a number

    public Boolean getGameId (Integer gid) {

        Query query = Query.query(Criteria.where("gid").is(gid));

        Integer count = (int) mongoTemplate.count(query, Document.class, "game");

        //If count = 1>0, returns a true
        return count >0;

    }

    //PART B: Updates all the new comments, rating and posted into a new column called "editted"
    //db.reviews.updateOne(
    // {gid: 5},
    // {  
    //   $push: {edited: {comment: "lousy movie", rating: 2, posted: new Date()}},
    // })
    //Correct way of doing:
    //Step 1: Have a query to push the current entry to 'edited'
    //Step 2: Have a query to set & push 
    public Boolean edit (Integer gid, EditEntry e) {

        Document doc = new Document();
        doc.put("comment", e.getComment());
        doc.put("rating", e.getRating());
        doc.put("posted", e.getPosted());

        Query query = Query.query(Criteria.where("gid").is(gid));

        Update updateOps = new Update()
        .push("edited", doc);

        //Updateresult returns:
        // {
        //     "acknowledged" : true,
        //     "insertedId" : null,
        //     "matchedCount" : 1.0,
        //     "modifiedCount" : 1.0,
        //     "upsertedCount" : 0.0
        // }
        //modifiedCount shows how many changes
        
        UpdateResult updateResult = mongoTemplate.updateMulti(query, updateOps, Document.class, C_REVIEWS);


        //Cast the (int) as updateresult returns a long
        return (int) updateResult.getModifiedCount() > 0;
    }

    //Task C: Get the data based on gid
    //db.reviews.find({_id: ObjectId("63a7ffe6b3db2930ac911cf4")})
    //Refer to Task C notes below to see what it returns
    //*****NOTE: THIS ONLY RETURNS ONE DOCUMENT AND NOT A LIST OF DOCUMENT
    public Review findReviewById (String _id) {

        ObjectId docId = new ObjectId(_id);

        Document d = mongoTemplate.findById(docId, Document.class, C_REVIEWS);

        Review r = Review.createForC(d);

        return r;
    }

    //Task D: Get all the data
    //db.reviews.find({_id: ObjectId("63a7ffe6b3db2930ac911cf4")})
    //*****NOTE: THIS ONLY RETURNS ONE DOCUMENT AND NOT A LIST OF DOCUMENT
    public Review findHistoryById (String _id) {

        ObjectId docId = new ObjectId(_id);

        Document d = mongoTemplate.findById(docId, Document.class, C_REVIEWS);

        Review r = Review.createForD(d);

        return r;
    }

 

    








}





// db.comments.find().limit(10)

// db.comments.createIndex({c_text: "text"})


//  db.comments.find(
//         {$text: {$search: "fun"}},
//         {score: {$meta: "textScore"}}
//         )
//         .sort({score: 1})
//         .limit(5)
//         .skip(5)
        
        




// //Update the entry individually 
// NOTE: MUST GROUP ALL THE SET TOGETHER
// db.reviews.updateOne(
//     {user: "Jack"},
//     {  
//       $set: {comment: "workssss", rating: 7, posted: new Date()},
// })

// //To remove the column titled "editted"
// db.reviews.updateOne(
//     {user: "Jack"},
//     {  
//       $unset: {edited: ""},
// })

//Task C Notes:
//It returns:
// {
//     "_id" : ObjectId("63a7ffe6b3db2930ac911cf4"),
//     "user" : "Jack",
//     "rating" : NumberInt(7),
//     "comment" : "workssss",
//     "gid" : NumberInt(5),
//     "posted" : ISODate("2022-12-26T07:56:31.531+0000"),
//     "name" : "bgName1",
//     "edited" : [
//         {
//             "comment" : "kns movie",
//             "rating" : NumberInt(1),
//             "posted" : ISODate("2022-12-26T09:24:47.902+0000")
//         },
//         {
//             "comment" : "bad",
//             "rating" : NumberInt(3),
//             "posted" : ISODate("2022-12-26T09:50:12.105+0000")
//         },
//         {
//             "comment" : "damn bad",
//             "rating" : NumberInt(2),
//             "posted" : ISODate("2022-12-26T15:16:27.157+0000")
//         },
//         {
//             "comment" : "good",
//             "rating" : NumberInt(7),
//             "posted" : ISODate("2022-12-26T16:15:28.480+0000")
//         },
//         {
//             "comment" : "very good",
//             "rating" : NumberInt(8),
//             "posted" : ISODate("2022-12-27T02:58:07.959+0000")
//         },
//         {
//             "comment" : "very good good",
//             "rating" : NumberInt(9),
//             "posted" : ISODate("2022-12-27T03:09:47.632+0000")
//         }
//     ]
// }
