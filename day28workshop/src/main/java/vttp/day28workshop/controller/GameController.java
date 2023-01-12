package vttp.day28workshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vttp.day28workshop.model.Game;
import vttp.day28workshop.repository.GameRepo;

@Controller
public class GameController {

    @Autowired
    GameRepo gameRepo;
    //For HTML Form, CANNOT PUT THE INPUT FROM HTML FORM INTO PATH VARIABLE
    @GetMapping (path = "/game/{gid}/getreviews")
    public String search (@PathVariable Integer gid, Model model) {

        Optional <Game> g = gameRepo.searchByGid(gid);

        if (g.isEmpty()) {

            return "notfound";
        }

        //This is needed because Optional is used
        Game game = g.get();

        //The "game" and "reviewId" will be used in the html
        model.addAttribute("game", game);
        model.addAttribute("reviewId", game.getReviews());

        return "reviewresults";


    }
    
}
