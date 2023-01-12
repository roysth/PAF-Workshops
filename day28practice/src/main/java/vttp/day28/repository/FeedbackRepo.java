package vttp.day28.repository;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import vttp.day28.model.Comment;
import vttp.day28.model.Game;

@Repository
public class FeedbackRepo {

    @Autowired
    private MongoTemplate mongoTemplate;


    /* Mongo Query:
    NOTE: Need to unwind inorder to do the sorting
     *  db.game.aggregate([
    {   
        //Find the game that matches the name twilight imperium
        $match: {name: "Twilight Imperium"}
    },
    {   
        //Look up the comments collection based on the localField gid to match the data
        //and store it under a header called "commentsnew" under the games collection
        $lookup: {from: "comments", foreignField: "gid", localField:"gid", as:"commentsnew"}
    },
    {   
        //Choose the data you want to be reflected 
        $project: {_id: 1, name: 1, image: 1, commentsnew: 1}
    },
    {   
        //To tidy the data under "commentsnew", we need to unwind it as it is in a array
        //Note that need to include a $ infront as it is the name of the "column"
        $unwind: "$commentsnew"
    },
    {   
        //Sort the data in descending order
        $sort: {"commentsnew.rating": -1}
    },
    {   
        //Limit to 10 entries
        $limit: 10
    },
    {
        $group: {_id: {name: "$name", image:"$image"}, commentsnew: {$push: "$commentsnew"}}
    },
    ]);
     * 
     * 
     * 
     * 
     */
    
    //Returns a GAME object
    public Optional <Game> search (String name) {

        //Create stages 
        //$match the name
        //Can use regex here so as to have case insensitive search by having the "i"
        MatchOperation matchName = Aggregation.match(Criteria.where("name").regex(name, "i"));

        //$lookup
        LookupOperation lookupComments = Aggregation.lookup("comments", "gid", "gid", "commentsnew");

        //$project: _id, name
        ProjectionOperation projectFields = Aggregation.project("_id", "name", "image", "commentsnew");

        //$unwind
        UnwindOperation unwindComments = Aggregation.unwind("commentsnew");

        //$sort
        SortOperation sortRating = Aggregation.sort(Direction.DESC, "commentsnew.rating");

        //$limit
        LimitOperation limitRating = Aggregation.limit(10);

        //$group
        //Although "name" and "image" are parked under _id, java can auto retrieve it 
        GroupOperation groupByName = Aggregation.group("name", "image").push("commentsnew").as("commentsnew");

        //Create the pipeline
        Aggregation pipeline = Aggregation.newAggregation(matchName, lookupComments, projectFields, unwindComments, sortRating, limitRating, groupByName);
        

        //Query the collection
        //collectionName is the name of the collection you are getting the data from
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "game", Document.class);

    
        // if (!results.iterator().hasNext()) {
        //     return Optional.empty();
        // }


        //Get only one result
        Document doc = results.iterator().next();
        Game g = Game.create(doc);
        //key is the new column name
        List<Comment> comments = doc.getList("commentsnew", Document.class).stream().map(c -> Comment.create(c)).toList();
        g.setComment(comments);

        System.out.println(">>>>>>" + comments);

        return Optional.of(g);



    }
    
}

//-------EXAMPLE--------\\


    /*
    --MONGO PIPIELINE QUERY:
    **Similar query as above, but java cannot write out pipeline query, hence must do the long way
        db.games.aggregate([
        { 
            $match: { name: 'Gloomhaven' }
        },
        {
            $lookup: {
            from: 'comments',
            foreignField: 'gid',
            localField: 'gid',
            as: 'comments',
            pipeline: [
                { $sort: { rating: -1 } },
                { $limit: 10 }
            ]
            }
        },
        {
            $project: { name: 1, comments: 1 }
        }
        ]);     
        
        Cant do it this way:
        hence must do the long way above

        //Create the pipelne
        Aggregation pipeline = Aggregation.newAggregation(matchName);

        //Query the collection
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "games", Document.class);
    */
   

