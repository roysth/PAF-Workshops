package vttp.day28.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.day28.model.Game;
import vttp.day28.repository.FeedbackRepo;

@Controller
@RequestMapping ( path = "/search")
public class BoardGameController {
    
    @Autowired
    FeedbackRepo repo;

    @GetMapping
    public String create (@RequestParam String boardgame, Model model) {

        System.out.println("TESTTESTTEST");

        //Putting Optional here so that in case the game that is keyed in 
        //by the user is not there, the app will not crash

        Optional<Game> g = repo.search(boardgame);

        if (g.isEmpty()){
            return "notfound";
        }
        //This is needed because Optional class is used
        Game game = g.get();
        
        //Thse are used in results.html
        model.addAttribute("game", game);
        model.addAttribute("comment", game.getComment());



        return "results";



    }
}


//THE DETAILS IN HTML FORM ALWAYS APPEAR IN REQUESTBODY OR REQUEST PARAM
//NOT IN THE PATH