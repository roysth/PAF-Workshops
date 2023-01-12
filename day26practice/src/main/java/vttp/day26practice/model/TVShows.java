package vttp.day26practice.model;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class TVShows {

    private String name;
    private String summary;
    private String image;
    private Double rating;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }

    //This is a method to set default values if the values are null
    //NOTE that value and defValue must be of the same type, if one
    //is string, the other must be string
    private <T> T defaultValue (T value, T defValue) {
        if (value != null) {
            return value;
        }
        return defValue;
    }

    //Method to create Json object
    //Some of the variables might be empty. hence use the defaultValue method
    //For rating, both values must be of the same - double. Hence, put 0d
    public JsonObject toJson () {
        return Json.createObjectBuilder()
            .add("name", name)
            .add("summary", defaultValue(summary, "No Summary"))
            .add("image", defaultValue(image, "No Imageg URL"))
            .add("rating", defaultValue(rating, 0d))
            .build();
    }
    //Method to create TVShows
    public static TVShows create (Document doc) {
        TVShows tvShows = new TVShows();
        tvShows.setName(doc.getString("name"));
        tvShows.setSummary(doc.getString("summary"));

        //For this case, the value is under rating -> average (Refer to json view) 
        Document d = doc.get("rating", Document.class);
        Object r = d.get("average");
        //Ensure that the value is in double
        //Have to convert it to an object for the checking below
        if (r instanceof Integer) {
            tvShows.setRating(((Integer) r).doubleValue());
        }else{
            tvShows.setRating((Double) r);
        }

        //For the case of String and when the string is under image->original
        d = doc.get("image", Document.class);
        tvShows.setImage(d.getString("original"));

        return tvShows;
        
    }
    
}


// {
//     "_id" : ObjectId("638d5cee18b0b78e3f991136"),
//     "id" : NumberInt(1),
//     "url" : "http://www.tvmaze.com/shows/1/under-the-dome",
//     "name" : "Under the Dome",
//     "type" : "Scripted",
//     "language" : "English",
//     "genres" : [
//         "Drama",
//         "Science-Fiction",
//         "Thriller"
//     ],
//     "status" : "Ended",
//     "runtime" : NumberInt(60),
//     "premiered" : "2013-06-24",
//     "officialSite" : "http://www.cbs.com/shows/under-the-dome/",
//     "schedule" : {
//         "time" : "22:00",
//         "days" : [
//             "Thursday"
//         ]
//     },
//     "rating" : {
//         "average" : 6.5
//     },
//     "weight" : NumberInt(91),
//     "network" : {
//         "id" : NumberInt(2),
//         "name" : "CBS",
//         "country" : {
//             "name" : "United States",
//             "code" : "US",
//             "timezone" : "America/New_York"
//         }
//     },
//     "webChannel" : null,
//     "externals" : {
//         "tvrage" : NumberInt(25988),
//         "thetvdb" : NumberInt(264492),
//         "imdb" : "tt1553656"
//     },
//     "image" : {
//         "medium" : "http://static.tvmaze.com/uploads/images/medium_portrait/0/1.jpg",
//         "original" : "http://static.tvmaze.com/uploads/images/original_untouched/0/1.jpg"
//     },
//     "summary" : "<p><b>Under the Dome</b> is the story of a small town that is suddenly and inexplicably sealed off from the rest of the world by an enormous transparent dome. The town's inhabitants must deal with surviving the post-apocalyptic conditions while searching for answers about the dome, where it came from and if and when it will go away.</p>",
//     "updated" : NumberInt(1529612668),
//     "_links" : {
//         "self" : {
//             "href" : "http://api.tvmaze.com/shows/1"
//         },
//         "previousepisode" : {
//             "href" : "http://api.tvmaze.com/episodes/185054"
//         }
//     }
// }