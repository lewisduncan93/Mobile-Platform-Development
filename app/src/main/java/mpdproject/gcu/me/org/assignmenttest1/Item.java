package mpdproject.gcu.me.org.assignmenttest1;

import java.io.Serializable;

// Student Name: Lewis Duncan
// Student ID: S1630772

public class Item implements Serializable {

    private static final long serialVersionUID = 7526472256622776147L;

    private String title;
    private String description;
    private String link;
    private String geo;
    private String author;
    private String comments;
    private String pubDate;

    public Item() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return String.format("title: %s; description: %s; link: %s; geo: %s; author: %s; comments: %s; pubDate: %s\n",
                title,
                description,
                link,
                geo,
                author,
                comments,
                pubDate);
    }

    public void parseColourCode(){


    }
}
