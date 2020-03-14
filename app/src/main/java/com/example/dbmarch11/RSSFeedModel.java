package com.example.dbmarch11;

public class RSSFeedModel {

    public String title;
    public String link;
    public String description;

    public RSSFeedModel(String title, String link, String description)
    {
        this.title = title;
        this.link = link;
        this.description = description;
    }

    @Override
    public String toString()
    {
        return this.title;
    }

}
