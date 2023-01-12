package vttp.day26practice.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.day26practice.model.TVShows;


@Repository
public class TVRepository {

    //This is to prevent typo in the mongoTemplate, so can just use TV_SHOWS
    //C means collection
    private static final String C_TV_SHOWS = "tvshows";

    @Autowired
    private MongoTemplate mongoTemplate;

    //To display all the types of Genres from the tvshows db
    //Mongo query:
    //db.tvshows.distinct("genres")
    public List<String> findGenres () {

        return mongoTemplate.findDistinct(new Query(), "genres", C_TV_SHOWS, String.class);
    
    }

    //To display all types of data (name, rating, image url, summary ) under the type of selected Genres 
    //Mongo query:
    //db.tvshows.find ({genres: {$in: ["type_of_genre"] }}).limit(x).skip(y)
    //User will dictate the type_of_genre
    public List<TVShows> getTVShowsByGenre (String type_of_genre) {

        Criteria c = Criteria.where("genres").in(type_of_genre);

        Query q = Query.query(c);

        //mongoTemplate.find(q, Document.class, C_TV_SHOWS) will return a List <Document>
        //To convert it to List <TVShows>, need this part: stream().map(doc -> TVShows.create(doc)).toList()
        return mongoTemplate.find(q, Document.class, C_TV_SHOWS).stream().map(doc -> TVShows.create(doc)).toList();
    } 

    //---------BELOW ARE EXTRAS------------\\


     /* We want to run this in Mongo:
     * db.tvshows.find({language: "English"})
     */
    public List<Document> findTVShowByLanguage (String language) {

        //Create a criteria/predicate
        //language is the key
        Criteria c = Criteria.where("language").is(language);

        //Create a Query to use the criteria
        //So that we can do the action/manipulate the query eg limit, 
        Query q = Query.query(c);

        //Return a document class
        return mongoTemplate.find(q, Document.class, C_TV_SHOWS);

    }

    /* **there is no year in the table
     * Whenever there is a dot eg rating.average, will need a ' '
     *  db.tvshows.find ({
     *      "rating.average" :{$gte: 6},
     *       year: {$gte: 1990},
     *       language: "English"
     * })
     */
    public List<Document> findTVShowsByRating(Float f, Integer y) {
        Criteria c = Criteria
            .where("rating.average").gte(f)
            .andOperator(
                //Criteria.where("year").gte(y),
                Criteria.where("language").is("English")
            );

        Query q = Query.query(c);

        return mongoTemplate.find(q, Document.class, "tvshows");
    }







}

