package vttp.day27practice.repository;


import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import vttp.day27practice.models.SearchResults;

@Repository
public class SearchRepo {   

    private static final String C_COMMENTS = "comments";

    @Autowired
    private MongoTemplate mongoTemplate;

    /*
                MONGO QUERY:
    Need a text index before can search text
    This is to tell the DB to search the text under which column
        db.comments.createIndex({c_text: "text"})

    Query to create the textscore 
        db.comments.find(
        {$text: {$search: "fun"}},
        {score: {$meta: "textScore"}}
        )
        .sort({score: 1})
        .limit(m)
        .skip(n)
    */

    //Using method overloading - For the different scenarios eg user provide limit but never provide offset
    //This is to capture all the different scenarios
    //If lazy, just use the last method below
    
    public List<SearchResults> search (List<String> includes, List<String> excludes) {
        return search (includes, excludes, 10, 0);
    }

    public List<SearchResults> search (List<String> includes, List <String> excludes, Integer limit) {
        return search (includes, excludes, limit, 0);
    }

    public List<SearchResults> search (List<String> includes, List<String> excludes, Integer limit, Integer skip) {

        TextCriteria tc = TextCriteria.forDefaultLanguage()
            //Converting the includes and excludes array to a string array
            .matchingAny(includes.toArray(new String[includes.size()]))
            .notMatchingAny(excludes.toArray(new String[includes.size()]));
            

        //Create the Query 
        //Putting the (TextQuery) in front is called Casting in Java
        //Basically, it means changing from one object to another
        //By using .limit and .skip, it converts it to Query
        //However, we want a TextQuery, hence putting the TextQuery infront 
        TextQuery tq = (TextQuery) TextQuery.queryText(tc)
            .includeScore("score")
            .sortByScore()
            .limit(limit)
            .skip (skip);


        //MUST ALWAYS RETURN A LIST
        return mongoTemplate.find(tq, Document.class, C_COMMENTS)
            .stream()
            .map(sr -> SearchResults.create(sr))
            .toList();

    
    }
}





// return mongoTemplate.find(tq, Document.class, "comments")
// .stream()
// .map(d -> Comment.create(d))
// .toList();