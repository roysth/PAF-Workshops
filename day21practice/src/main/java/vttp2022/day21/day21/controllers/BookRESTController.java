package vttp2022.day21.day21.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import vttp2022.day21.day21.models.Book;
import vttp2022.day21.day21.repositories.BookRepositories;

@RestController
//For api endpoint, returns structured data, eg json

@RequestMapping(path = "/api/rating", produces = MediaType.APPLICATION_JSON_VALUE)

public class BookRESTController {

    @Autowired
    private BookRepositories bookRepo;

    //Our request: GET /api/rating/<rating>
    //RequestMapping only handles only until /api/rating
    @GetMapping (path = "{rating}")
    //use @PathVariable cus need it in the path. Variable is a float
    public ResponseEntity<String> getRating(@PathVariable Float rating) {

        //Query the database for books
        List<Book> books = bookRepo.getBooksByRating(rating);

        //Create the json builder first and build the result
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Book b:books)
            arrayBuilder.add(b.toJson());
        JsonArray result = arrayBuilder.build();

        return ResponseEntity 
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }

    
}
