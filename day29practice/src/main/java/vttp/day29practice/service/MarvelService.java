package vttp.day29practice.service;
import java.io.StringReader;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.day29practice.model.Marvel;


@Service
public class MarvelService {

    //Inject into th service the private and public key
    //MARVEL_PUBLIC_KEY=89aba4bac3b38549ba96303dc649cb40
    //MARVEL_PRIVATE_KEY=97f29dd73d0321fbdf7e462cbe255528fc2d227e


    
    public static final String marvelUrl = "https://gateway.marvel.com:443/v1/public/characters?";


    @Value ("${MARVEL_PUBLIC_KEY}")
    private String publicKey;

    @Value ("${MARVEL_PRIVATE_KEY}")
    private String privateKey;

    public List<Marvel> search (String name) {
        return search(name, 10);
    }

    public List<Marvel> search (String name, Integer limit){

        Long ts = System.currentTimeMillis();
        String signature = "%d%s%s".formatted(ts, privateKey, publicKey);
        String hash = "";

        try {

            //Message digest = md5, sha-1, sha512
            //Get an instance of MD5
            MessageDigest md5 =  MessageDigest.getInstance("MD5");
            //Calculate our hash
            //Update message digest
            md5.update(signature.getBytes());
            //get md5 digest
            byte[] h = md5.digest();
            //Convert the byte array into a string/ Stringify the md5 digest
            hash = HexFormat.of().formatHex(h);

        } catch (Exception ex) {}

        /*
        https://gateway.marvel.com:443/v1/public/characters?
        nameStartsWith
        limit=10
        ts=
        apikey=
        hash=
        */
        String url = UriComponentsBuilder.fromUriString(marvelUrl)
            .queryParam("nameStartsWith", name)
            .queryParam("limit", limit)
            .queryParam("ts", ts)
            .queryParam("apikey", publicKey)
            .queryParam("hash", hash)
            .toUriString();

        System.out.printf("URL = %s\n", url);


        //Use the url to make a call to Marvel
        RequestEntity<Void> req = RequestEntity.get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        String payload = resp.getBody();

        //Parse the String to JsonObject
        JsonReader reader = Json.createReader((new StringReader(payload)));

        //{data: { results { } } }
        JsonObject result = reader.readObject();
        JsonArray data = result.getJsonObject("data").getJsonArray("results");

        //Retrieve the name, description, image
        List<Marvel> superHeroes = new LinkedList<>();
            for (Integer i = 0; i < data.size(); i++)
            superHeroes.add(Marvel.create(data.getJsonObject(i)));
    
        System.out.println(">>>>>" + superHeroes.toString());

        return superHeroes;




    }




    
}
