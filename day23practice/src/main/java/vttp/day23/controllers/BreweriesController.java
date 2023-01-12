package vttp.day23.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.day23.models.Brewery;
import vttp.day23.services.BeerService;

@Controller
@RequestMapping(path="/breweries")
public class BreweriesController {

    @Autowired
    private BeerService beerSvc;

    @GetMapping
    //Since for @RequestParm, there is only a string and int, the int will be used. beerStyle is used in html
    public String getBreweries(@RequestParam Integer beerStyle, Model model) {
        List<Brewery> breweries = beerSvc.getBreweriesByBeerStyle(beerStyle);
        for (Brewery b: breweries) {
            System.out.println(b.getName());
        }
        

        model.addAttribute("breweries", breweries);

        return "breweries";
    }
    
}
