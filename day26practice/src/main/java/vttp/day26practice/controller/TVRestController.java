package vttp.day26practice.controller;

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
import vttp.day26practice.model.TVShows;
import vttp.day26practice.repository.TVRepository;

@RestController

@RequestMapping (path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class TVRestController {

    @Autowired
    private TVRepository tvRepository;

    @GetMapping (path = "/genres")
    public ResponseEntity <String> getGenres() {

        List<String> genres = tvRepository.findGenres();

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for (String g: genres)
			arrayBuilder.add(g);
        
        JsonArray result = arrayBuilder.build();

        return ResponseEntity 
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }


    @GetMapping (path = "/tvshows/{genre}") 
    public ResponseEntity <String> getTVShows (@PathVariable String genre) {

        //Create a list for the query
        List<TVShows> list = tvRepository.getTVShowsByGenre(genre);
        System.out.println("TESTTEST");
        //Create the json builder to build the result
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (TVShows t: list) {
            arrayBuilder.add(t.toJson());
        }
        JsonArray result = arrayBuilder.build();

        return ResponseEntity 
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    
    }

}
