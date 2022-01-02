package com.Twitter.demo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.Twitter.demo.Helpers.ParseJson;
import com.Twitter.demo.Models.Tweet;
import com.Twitter.demo.Models.TwitterProfile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TwitterApi {
    private final static String bearerToken = "AAAAAAAAAAAAAAAAAAAAAL5HWgEAAAAAU2x9Mlm779lmBQsD53bSErOykbI%3DhoAij03NQp0EomeRIRGANc0YkfLppJqP0i5fuJBAy5ANQDHDnp";

    private final static HttpClient httpClient = HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.STANDARD).build())
            .build();

    /*
     * This method calls the v2 Users endpoint with usernames as query parameter
     * */
    public static TwitterProfile getUserProfile(String username) throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/users/by/username/%s", username));
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("user.fields", "created_at,description,pinned_tweet_id"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if(response.getStatusLine().getStatusCode() !=200) {
        	return null;
        }

        if (entity != null) {
            String jsonResponse = EntityUtils.toString(entity, "UTF-8");

            return ParseJson.profile(jsonResponse);
        }

        return null;
    }


    public static List<Tweet> getTweets(String username) throws IOException, URISyntaxException {
        TwitterProfile twitterProfile = getUserProfile(username);

        if (twitterProfile == null) {
            throw new IllegalArgumentException("Username not found");
        }

        String userId = Objects.requireNonNull(getUserProfile(username)).getId();

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/users/%s/tweets", userId));
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at"));
        queryParameters.add(new BasicNameValuePair( "max_results", "10"));

        
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String json = EntityUtils.toString(entity, "UTF-8");

            return ParseJson.tweets(json);
        }

        return null;
    }
}