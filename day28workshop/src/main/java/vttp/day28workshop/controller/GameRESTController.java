package vttp.day28workshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import vttp.day28workshop.model.Game;
import vttp.day28workshop.repository.GameRepo;

@RestController
public class GameRESTController {

    @Autowired
    GameRepo gameRepo;


    @GetMapping (path = "/game/{gid}/reviews")
    public ResponseEntity <String> getGameReviewsById (@PathVariable Integer gid) {

        Optional<Game> g = gameRepo.searchByGid(gid);

        //Since its optional, neet a get() method
        JsonObject result = g.get().toJson();

        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(result.toString());

    }
    
}
