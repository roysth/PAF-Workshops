package vttp.day26workshop.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.day26workshop.model.Games;
import vttp.day26workshop.repository.GamesRepo;

@RestController

@RequestMapping (path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)

public class GamesRestController {

    @Autowired
    private GamesRepo gamesRepo;

    //Part A (RequestParam)
    @GetMapping (path = "/games", params = {"limit", "offset"})
    public ResponseEntity <String> getGames(@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer offset) {

        if (limit == null) {
            limit = 25;
        } 

        if (offset == null) {
            offset = 0;
        }

        //Create the list of games from the repo method
        List<Games> games = gamesRepo.findGames(limit, offset);

        //Convert the list of games into json array
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Games g: games) {
            arrayBuilder.add(g.toJson());
        }
        JsonArray gamesArray = arrayBuilder.build();

        //Question state that end product is a json object
        //THIS JSON OBJECT CONTAINS VARIABLES THAT IS NOT INSIDE THE MODEL
        JsonObject results = Json.createObjectBuilder()
            .add("games", gamesArray)
            .add("limit", limit)
            .add("offset", offset)
            .add("total", games.size())
            .add("timestamp", System.currentTimeMillis())
            .build();

        return ResponseEntity 
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(results.toString());
    }

    //Part B (RequestParam)
    //http://localhost:8080/api/games/rank?limit=5&offset=5
    @GetMapping (path = "/games/rank", params = {"limit", "offset"})
    public ResponseEntity <String> getGamesByRank (@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer offset) {

        if (limit == null) {
            limit = 25;
        } 

        if (offset == null) {
            offset = 0;
        }

        List <Games> games = gamesRepo.findByRank(limit, offset);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Games g: games) {
            arrayBuilder.add(g.toJson());
        }
        JsonArray gamesArray = arrayBuilder.build();

        JsonObject results = Json.createObjectBuilder()
            .add("name", gamesArray)
            .add("limit", limit)
            .add("offset", offset)
            .add("total", games.size())
            .add("timestamp", System.currentTimeMillis())
            .build();
        
        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(results.toString());
    }

    //Part C (PathVariable)
    @GetMapping (path = "/game/{id}")
    public ResponseEntity <String> getGameDetails (@PathVariable Integer id) {


        List <Games> games = gamesRepo.findByID(id);

        JsonObject results = null;
        
        //JsonObjectBuilder builder = Json.createObjectBuilder();

        for (Games g: games) {
            results = g.toJsonGameDetails();
        }

        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(results.toString());

    }


}
