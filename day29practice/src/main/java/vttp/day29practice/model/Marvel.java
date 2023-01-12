package vttp.day29practice.model;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

public class Marvel {

    private Integer id;
    private String name;
    private String description;
    private String image;
    private String details;
    

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    //This is for logging, to convert the list of objects to string
    @Override
    public String toString() {
        return "SuperHero {id=%d, name=%s, description=%s, image=%s, details=%s}"
                .formatted(id, name, description, image, details);
    }

    public static Marvel create (JsonObject doc) {

        final Marvel marvel = new Marvel();

        marvel.setId(doc.getInt("id"));
        marvel.setName((doc.getString("name")));
        marvel.setDescription(doc.getString("description", "No description"));
        
        JsonObject img = doc.getJsonObject("thumbnail");
        marvel.setImage("%s.%s".formatted(img.getString("path"),img.getString("extension")));

        JsonArray urls = doc.getJsonArray("urls");
        for (Integer i = 0; i < urls.size(); i++) {
            JsonObject d = urls.getJsonObject(i);
            if ("detail".equals(d.getString("type"))){
                marvel.setDetails(d.getString("url"));
                break;
            }
        }
        return marvel;
    }

    //For Redis
    public JsonObject toJson () {
        return Json.createObjectBuilder()
        .add("id", id)
        .add("name", name)
        .add("description", description)
        .add("image", image)
        .add("details", details)
        .build();
    }

    //For Redis
    public static Marvel fromCache(JsonObject doc) {

        final Marvel m = new Marvel();
        m.setId(doc.getInt("id"));
        m.setName(doc.getString("name"));
        m.setDescription(doc.getString("description"));
        m.setImage(doc.getString("image"));
        m.setDetails(doc.getString("details"));
        return m;
    }
    
}


//     "code": 200,
//     "status": "Ok",
//     "copyright": "© 2022 MARVEL",
//     "attributionText": "Data provided by Marvel. © 2022 MARVEL",
//     "attributionHTML": "<a href=\"http://marvel.com\">Data provided by Marvel. © 2022 MARVEL</a>",
//     "etag": "45ebe52beffb7f95769a6d2aa180ec2a884c2eb1",
//     "data": {
//       "offset": 0,
//       "limit": 20,
//       "total": 9,
//       "count": 9,
//       "results": [
//         {
//           "id": 1009281,
//           "name": "Doctor Doom",
//           "description": "",
//           "modified": "2016-06-22T12:07:32-0400",
//           "thumbnail": {
//             "path": "http://i.annihil.us/u/prod/marvel/i/mg/3/60/53176bb096d17",
//             "extension": "jpg"
//           },
//           "urls": [
//               {
//                 "type": "detail",
//                 "url": "http://marvel.com/comics/characters/1009281/doctor_doom?utm_campaign=apiRef&utm_source=89aba4bac3b38549ba96303dc649cb40"
//               },
//               {
//                 "type": "wiki",
//                 "url": "http://marvel.com/universe/Doctor_Doom_%28Victor_von_Doom%29?utm_campaign=apiRef&utm_source=89aba4bac3b38549ba96303dc649cb40"
//               },
//               {
//                 "type": "comiclink",
//                 "url": "http://marvel.com/comics/characters/1009281/doctor_doom?utm_campaign=apiRef&utm_source=89aba4bac3b38549ba96303dc649cb40"
//               }
//             ]
//           },