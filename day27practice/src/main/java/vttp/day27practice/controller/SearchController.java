package vttp.day27practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.day27practice.models.SearchResults;
import vttp.day27practice.services.SearchService;

@Controller
@RequestMapping (path = "/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public String getSearch (@RequestParam String keyword, @RequestParam Float score, Model model) {

        System.out.printf(">>>>>> keyword = %s, score = %f\n", keyword, score);

        List<SearchResults> sr = searchService.search(keyword, score, 20, 0);

        //AttributeName must be the same with the one in searchresults.html
        model.addAttribute("keyword", keyword);
        model.addAttribute("score", score);
        model.addAttribute("results", sr);


        //Name of html
        return "searchresults";
    }
    
}
