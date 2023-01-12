package vttp.day29practice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import vttp.day29practice.model.Marvel;
import vttp.day29practice.repository.MarvelCache;
import vttp.day29practice.service.MarvelService;

@Controller
@RequestMapping (path = "/search")
public class MarvelController {

    @Autowired
    MarvelService marvelService;

    @Autowired
    MarvelCache marvelCache;

    @GetMapping
    public String create (@RequestParam String superhero, Model model) {

        List<Marvel> results = null;

        Optional<List<Marvel>> opt = marvelCache.get(superhero);
        if (opt.isEmpty()) {
            results = marvelService.search(superhero);
            marvelCache.cache(superhero, results);
        } else  { 
            results = opt.get();
            System.out.printf(">>>> from CACHE\n");
        }

        model.addAttribute("heroes", results);


        return "results";
    }


    
}
