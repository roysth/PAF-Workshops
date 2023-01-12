package vttp.workshop22.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import vttp.workshop22.models.Rsvp;
import vttp.workshop22.repositories.RsvpRepo;

@RestController
@RequestMapping (path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
//For PutMapping with multivaluemap and requestbody
@EnableWebMvc
public class RsvpRESTController {

    @Autowired
    private RsvpRepo rsvpRepo;


    @GetMapping (path ="/rsvps")
    public ResponseEntity <String> getAllRsvps (@RequestParam (required = false) String q) {

        List<Rsvp> rsvps = rsvpRepo.getAllRsvp(q);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Rsvp r : rsvps) {
            arrayBuilder.add(r.toJson());
        }
        JsonArray results = arrayBuilder.build();

        //NOTE: FOR Rest, if use if statement, then need a else
        if (rsvps.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{'error_code': " + HttpStatus.NOT_FOUND + "'}");
        } else {

            return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(results.toString());
        }
        
    }

    @PostMapping (path = "/rsvp")
    public ResponseEntity <String> addRsvp (@RequestBody MultiValueMap <String, String> form) {

        //Get the data from the form, which the user has filled up
        //Since the model is created for it, can write like this
        Rsvp rsvp = new Rsvp();
        rsvp.setName(form.getFirst("name"));
        rsvp.setEmail(form.getFirst("email"));
        rsvp.setPhone(form.getFirst("phone"));
        //getFrist will return a String.
        //Can get string here because it will be converted to Date as written in the model
        rsvp.setConfirmationDate(form.getFirst("confirmationDate"));
        rsvp.setComments(form.getFirst("comments"));

        String name = form.getFirst("name");

        if (rsvpRepo.authenticate(name)) {

            rsvpRepo.createRSVP(rsvp);

            JsonObject results = rsvp.toJson();

            return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body("RSVP already inside! A new entry will be added!" + results.toString());


        } else {
            //If the name is not inside:

            JsonObject results = rsvp.toJson();
            rsvpRepo.createRSVP(rsvp);

            return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body("RSVP added" + results.toString());
        }
    }

    @PutMapping (path = "/rsvp/{email}")
    public ResponseEntity <String> updateRsvp (@PathVariable (required = true) String email, @RequestBody MultiValueMap <String, String> form) {

        Rsvp rsvp = new Rsvp();
        rsvp.setName(form.getFirst("name"));
        rsvp.setEmail(form.getFirst("email"));
        rsvp.setPhone(form.getFirst("phone"));
        rsvp.setConfirmationDate(form.getFirst("confirmationDate"));
        rsvp.setComments(form.getFirst("comments"));

        if (rsvpRepo.authenticateEmail(email)) {
            //email is present:

            rsvpRepo.updateRSVP(rsvp);

            JsonObject results = rsvp.toJson();
            rsvpRepo.updateRSVP(rsvp);

            return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body("RSVP updated!" + results.toString());

        } else {
            //email not found

            return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body("Email not found!");
        }









    }



    
 











    
}
