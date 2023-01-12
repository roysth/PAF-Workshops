package vttp.day27workshop.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.day27workshop.model.EditEntry;
import vttp.day27workshop.model.Review;
import vttp.day27workshop.repository.ReviewRepo;


@RestController
//This is to allow putmapping to use requestbody
//index.html must be in template
@EnableWebMvc
public class ReviewRESTController {

    @Autowired
    private ReviewRepo reviewRepo;


    //Task A (RequestBody)
    //Add a new entry into the Collection called "review"
    @PostMapping (path = "/review")
    public ResponseEntity <String> addReview (@RequestBody MultiValueMap <String, String> form) {

        String user = form.getFirst("user");
        Integer rating = Integer.parseInt(form.getFirst("rating"));
        if (rating < 0 || rating > 10) {
            return ResponseEntity
            .status(HttpStatus.NOT_ACCEPTABLE)
            .contentType(MediaType.APPLICATION_JSON)
            .body("RATING MUST BE BETWEEN 0 AND 10");
        }
        String comment = null;
        if (form.getFirst("comment") == null) {
            comment = "No Comments";
        }
        else {
            comment = form.getFirst("comment");
        }
        Integer gid = Integer.parseInt(form.getFirst("gid"));

        //Dont need to assign a new date varaible here. The method in ReviewRepo takes care of it already
        
        String name = form.getFirst("name");

        if(reviewRepo.getGameId(gid)) {

            //To insert the review into Mongo
            reviewRepo.insertReview(user, rating, comment, gid, name);

            //This step is optional: Create JSON OBJECT to see the form
            //NOTE: This cannot verify if the data has been inserted. To check, need to do a count query
            //Or just open mongo to see
            Date date = new Date();

            JsonObject results = Json.createObjectBuilder()
            .add("user", user)
            .add("rating", rating)
            .add("comment", comment)
            .add("gid", gid)
            .add("posted", date.toString())
            .add("name", name)
            .build();

            return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .contentType(MediaType.APPLICATION_JSON)
            .body("Successfully entered these data:" + results.toString());

        } else {
            //NEED TO INCLUDE THE IF ELSE FOR THE BOOLEAN CHECK METHOD!!!
            return ResponseEntity
            .status(HttpStatus.NOT_ACCEPTABLE)
            .contentType(MediaType.APPLICATION_JSON)
            .body("GID does not match!");

        }
    } 

    //TASK B
    //Key in the comment and rating in the body
    @PutMapping (path = "/review/{gid}")
    public ResponseEntity <String> editEntry (@PathVariable Integer gid, @RequestBody MultiValueMap <String, String> form) {

        String comment = null;
        if (form.getFirst("comment") == null) {
            comment = "No Comments";
        }
        else {
            comment = form.getFirst("comment");
        }
        Integer rating = Integer.parseInt(form.getFirst("rating"));
        if (rating < 0 || rating > 10) {
            return ResponseEntity
            .status(HttpStatus.NOT_ACCEPTABLE)
            .contentType(MediaType.APPLICATION_JSON)
            .body("RATING MUST BE BETWEEN 0 AND 10");
        }
       
        EditEntry e = new EditEntry();
        e.setComment(comment);
        e.setRating(rating);
        e.setPosted (new Date());

        if (reviewRepo.edit(gid, e)) {

            return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .contentType(MediaType.APPLICATION_JSON)
            .body("Added!");

        } else {
            //NEED TO INCLUDE THE IF ELSE FOR THE BOOLEAN CHECK METHOD!!!
            return ResponseEntity
            .status(HttpStatus.NOT_ACCEPTABLE)
            .contentType(MediaType.APPLICATION_JSON)
            .body("GID DOES NOT MATCH!");

        } 
    }
    //Task C
    //localhost:8080/review/63a7ffe6b3db2930ac911cf4
    @GetMapping (path = "review/{_id}" )
    public ResponseEntity <String> getReviewById (@PathVariable String _id) {


        Review r = reviewRepo.findReviewById(_id);

        JsonObject result = r.toJsonForC();

        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(result.toString());

    }
    
    //Task D
    @GetMapping (path = "review/{_id}/history")
    public ResponseEntity <String> getHistoryById (@PathVariable String _id) {

        Review r = reviewRepo.findHistoryById(_id);

        JsonObject result = r.toJsonForD();

        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(result.toString());

    }



    // reviewRepo.edit(gid, e);

    // return ResponseEntity
    // .status(HttpStatus.ACCEPTED)
    // .contentType(MediaType.APPLICATION_JSON)
    // .body("Added!");







}
