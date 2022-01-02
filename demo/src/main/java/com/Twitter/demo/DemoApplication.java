package com.Twitter.demo;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.Twitter.demo.Models.Tweet;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) throws IOException, URISyntaxException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        String username = scanner.nextLine();
        
        List<Tweet> tweets;
        
        try {
        	tweets = TwitterApi.getTweets(username);
        } catch (IllegalArgumentException error) {
        	System.out.println("Wrong username");
        	return;
        
        }

        

        System.out.println();
        System.out.println("Your tweets:\n");

        Map<String, Integer> allMentions = new HashMap<>();

        tweets.forEach(tweet -> {
            System.out.println("Tweet id = " + tweet.getId());
            System.out.println("Tweet text:");
            System.out.println(tweet.getText());
            System.out.println("Total Number of Mentions:");
            System.out.println(tweet.getNumberOfMentions());

            if (tweet.getNumberOfMentions() > 0) {
                System.out.println("\tMentions:");
                tweet.getMentions().forEach((mentionedUsername, numberOfMentions) -> {
                    System.out.println("\t\tUsername: " + mentionedUsername);
                    System.out.println("\t\tNumber of mentions: " + numberOfMentions);

                    allMentions.put(mentionedUsername, allMentions.getOrDefault(mentionedUsername, 0) + 1);
                });
            }

            System.out.println("------------");
        });

        String mostMentionedUser = null;
        int totalNumberOfMentions = 0;

        for (var mentionedUsername :
                allMentions.keySet()) {
            if (allMentions.get(mentionedUsername) > totalNumberOfMentions) {
                totalNumberOfMentions = allMentions.get(mentionedUsername);
                mostMentionedUser = mentionedUsername;
            }
        }

        System.out.println("\n\nMost mentioned account: " + mostMentionedUser);
        System.out.println("Total number of mentions: " + totalNumberOfMentions);


    }
}
