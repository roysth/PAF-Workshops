package vttp.day23.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.day23.models.BeerStyle;
import vttp.day23.models.Brewery;
import vttp.day23.repositories.BreweryRepository;
import vttp.day23.repositories.StylesRepository;

@Service
public class BeerService {

    @Autowired
    private StylesRepository styleRepo;

    @Autowired
    private BreweryRepository breweryRepo;

    public List<BeerStyle> getBeerStyles(){
        return styleRepo.getStyles();
    }

    public List<Brewery> getBreweriesByBeerStyle(Integer beerStyle) {
        
        return breweryRepo.getBreweriesByStyle(beerStyle);
    }
    
}
