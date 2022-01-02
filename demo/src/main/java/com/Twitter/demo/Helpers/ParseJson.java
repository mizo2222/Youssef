package com.Twitter.demo.Helpers;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.Twitter.demo.Models.Tweet;
import com.Twitter.demo.Models.TwitterProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParseJson {
    public static TwitterProfile profile(String json) {
        Gson gson = new Gson();
        TypeToken<Map<String, Map<String, String>>> typeToken = new TypeToken<>() {};
        Map<String, Map<String, String>> parsedJson = gson.fromJson(json, typeToken.getType());

        TwitterProfile twitterProfile = new TwitterProfile();

        twitterProfile.setId(parsedJson.get("data").get("id"));
        twitterProfile.setUsername(parsedJson.get("data").get("username"));
        twitterProfile.setName(parsedJson.get("data").get("name"));
        twitterProfile.setDescription(parsedJson.get("data").get("description"));
        twitterProfile.setCreatedAt(parsedJson.get("data").get("created_at"));

        return twitterProfile;
    }

    public static List<Tweet> tweets(String json) {

        var parsedJson = JsonParser.parseString(json).getAsJsonObject();

        List<Tweet> tweets = new ArrayList<>();

        parsedJson.getAsJsonArray("data").forEach(jsonTweet -> {
            var parsedJsonTweet = jsonTweet.getAsJsonObject();

            String id = parsedJsonTweet.getAsJsonPrimitive("id").getAsString();
            String text = parsedJsonTweet.getAsJsonPrimitive("text").getAsString();

            Tweet tweet = new Tweet(id, text);

            tweets.add(tweet);
        });

        return tweets;
    }

}
