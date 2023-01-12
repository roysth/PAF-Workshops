package vttp.day27practice.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.day27practice.models.SearchResults;
import vttp.day27practice.repository.InsertRepo;
import vttp.day27practice.repository.SearchRepo;

@Service
public class SearchService {

    @Autowired
    private SearchRepo searchRepo;

    @Autowired
    private InsertRepo insertRepo;
    
    public List<SearchResults> search (String keyword, Float score, Integer limit, Integer skip) {

        //Example of String keyword entered by user; "-fun +happy"

        //Create a list to put the includes and excludes
        List<String> includes = new LinkedList<>();
        List<String> excludes = new LinkedList<>();
        
        for (String s: keyword.split(" ")) {
            if (s.startsWith("-")) {
                excludes.add(keyword);
            }
            else {
                includes.add(keyword);
            }
        }
        //try...finally: No matter what happens (whether an exception is handled or not), the finally block will run
        //Benefits: Used to clean up the code, act as an ending
        
        //filter() allows to filter elements of a stream that matches the given Predicate
        //Predicate: stream().filter(predicate < super T . predicate)
        //USUALLY FOR VALUES!!

        try {
            return searchRepo.search (includes, excludes, limit).stream().filter(c -> c.getScore() >= score).toList();

        } finally {
            insertRepo.log(keyword, score);
        }
        
    }


    
}
