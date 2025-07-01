package com.dot;
class Video {
    private String title;
    private String link;

    public Video(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nLink: " + link;
    }
}