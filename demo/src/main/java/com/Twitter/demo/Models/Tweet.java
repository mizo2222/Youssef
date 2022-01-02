package com.Twitter.demo.Models;
import java.util.HashMap;
import java.util.Map;


public class Tweet {
    private String id;
    private String text;
    private Map<String, Integer> mentions;

    public Tweet(String id, String text) {
        this.id = id;
        this.text = text;

        this.countMentions();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Integer> getMentions() {
        return mentions;
    }

    public int getNumberOfMentions() {
        return this.mentions.values().stream().reduce(0, (acc, el) -> acc + el);
    }

    public void countMentions() {
        Map<String, Integer> mentions = new HashMap<String, Integer>();

        for (var word :
                this.text.split("\\s+")) {
            if (word.charAt(0) == '@') {
                mentions.put(word, mentions.getOrDefault(word, 0) + 1);
            }
        }

        this.mentions = mentions;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
