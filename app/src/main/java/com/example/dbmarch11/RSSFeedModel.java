package com.example.dbmarch11;


/*  CLASS       : RSSFeedModel.java
 *  PROJECT     : Mobile A2
 *  PROGRAMMERS : Mohamed Benzreba, Ethan Hoekstra, Jacob Nelson, Muhammad Mamooji
 *  DUE DATE    : 14 March 2020
 * `PURPOSE     : Models an RSSFeed item for display in any ListView, RecyclerView, etc.
 */

// CITATION:
// Based on the tuorial found at:
// https://www.androidauthority.com/simple-rss-reader-full-tutorial-733245/

public class RSSFeedModel {

    public String title;        // Article title
    public String link;         // Link to go to the actual article
    public String description;  // Desription of article



    /*  !!! CONSTRUCTOR !!!
     *  FUNCTION    : RSSFeedModel()
     *  DESCRIPTION : Basic constructor for the model.
     *  PARAMETERS  :
     *      String  title       : Title of article
     *      String  link        : Link to article
     *      String  description : Description of article
     *  RETURNS     :
     *      RSSFeedModel object reference
     */
    public RSSFeedModel(String title, String link, String description)
    {
        this.title = title;
        this.link = link;
        this.description = description;
    }



    /*  FUNCTION    : toString()
     *  DESCRIPTION : Overrides the toString() method so that the object can be represented simply
     *      as a string.
     *  PARAMETERS  : void
     *  RETURNS     :
     *      String  : RSSFeedModel.title
     */
    @Override
    public String toString()
    {
        return this.title;
    }

}
