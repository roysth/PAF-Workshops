package vttp.day28workshop.repository;

import java.util.Date;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation.AddFieldsOperationBuilder;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import vttp.day28workshop.model.Game;

@Repository
public class GameRepo {

    @Autowired
    private MongoTemplate mongoTemplate;


    //PART A
    //Mongo Query:
    /*

    db.game.aggregate ([

    {$match: {gid: 5}},
    
    {$lookup: {from: "reviews", foreignField: "gid", localField: "gid", as: "reviews"}},
    
    {$project: {_id: 1, gid:1, name: 1, year: 1, ranking:1, users_rated:1, url: 1, image:1, reviews:"$reviews._id", timestamp: "$$NOW"}},
    
    ])

    */
    //Return a game object
    //Use a string gid then parse it into integer
    public Optional <Game> searchByGid (Integer gid) {

        MatchOperation matchGid = Aggregation.match(Criteria.where("gid").is(gid));

        LookupOperation lookupReviews = Aggregation.lookup("reviews", "gid", "gid", "reviews");

        ProjectionOperation projectFields = Aggregation.project("_id", "gid", "name", "year", "ranking", "users_rated", "url", "image")
                                                        .and("reviews._id").as("reviews");

        //Since timestamp is a new variable, need to use this to add
        AddFieldsOperationBuilder addFieldOpsBld = Aggregation.addFields();
        addFieldOpsBld.addFieldWithValue("timestamp", new Date());
        AddFieldsOperation newFieldOps = addFieldOpsBld.build();

        Aggregation pipeline = Aggregation.newAggregation(matchGid, lookupReviews, projectFields, newFieldOps);

        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "game", Document.class);

        //Since we only need a game object
        Document doc = results.iterator().next();
        Game g = Game.create(doc);

        return Optional.of(g);
        
    }




    
}
