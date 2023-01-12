package vttp.day26workshop.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.day26workshop.model.Games;

@Repository
public class GamesRepo {

    private static final String C_GAMES = "game";

    @Autowired
    private MongoTemplate mongoTemplate;


    //Mongo Query:
    /*
    Using projections, return only the name and gid
    //Return the name, gid with the offset and limit
    db.game.find({},{name:1, gid:1 }).skip(0).limit(30) 
    */
    public List<Games> findGames (Integer limit, Integer offset) {


        Query q = Query.query(Criteria.where("name").regex("/*")).limit(limit).skip(offset);

        q.fields().include ("name", "gid");

        // Criteria c = Criteria.where("name").regex("/*").limit(limit).skip(offset); <- will get error, cant write like
        // Query q = Query.query(c);
        // q.fields().include ("name", "gid");
        
        //return a List<Games>
        return mongoTemplate.find(q, Document.class, C_GAMES).stream().map(doc -> Games.create(doc)).toList();   
    }

    //Return the name, gid based on ranking, with offset and limit
    //db.game.find({},{name:1, gid:1 }).skip(0).limit(30).sort({ranking: -1})
    public List <Games> findByRank (Integer limit, Integer offset) {

        Query q = Query.query(Criteria.where("name").regex("/*"))
            .with(Sort.by(Sort.Direction.ASC, "ranking"))
            .skip(offset)
            .limit(limit);
        q.fields().include ("name", "gid");

        return mongoTemplate.find(q,Document.class, C_GAMES).stream().map(doc -> Games.create(doc)).toList();
    }

    //Search by gid
    //db.game.find({gid: 1})
    public List <Games> findByID (Integer id) {

        Criteria criteria = Criteria.where("gid").is(id);

        Query q = Query.query(criteria);

        return mongoTemplate.find(q, Document.class, C_GAMES).stream().map(doc -> Games.createGameDetails(doc)).toList();

    }


    
}



// public JsonObject findGames (Integer limit, Integer offset) {

//     if (limit == null) {
//         limit = 25;
//     } 

//     if (offset == null) {
//         offset = 0;
//     }

//     Criteria c = Criteria.where("name").regex("/*");

//     Query q = Query.query(c);

//     q.fields().include ("name", "gid");
    
//     //Convert to List <Games> then convert to jasonarray

//     List <Games> games = mongoTemplate.find(q, Document.class, C_GAMES).stream().map(doc -> Games.create(doc)).toList();

//     JsonArrayBuilder gamesArray = Json.createArrayBuilder();
//     for (Games g: games)
//         gamesArray.add(g.toJson());
    
//         JsonObject results = Json.createObjectBuilder()
//         .add("games", gamesArray.build())
//         .add("offset", offset)
//         .add("limit", limit)
//         .add("total", offset)
//         .add("timestamp", System.currentTimeMillis())
//         .build();

//     return results;
// }