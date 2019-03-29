package com.niklas.gcu.moblieplatformcw;

import java.util.Comparator;

public class Earthquake {

    private String title;
    private String magnitude;
    private String location;
    private String depth;
    private String description;
    private String link;
    private String pubDate;
    private String category;
    private String geoLat;
    private String geoLong;

    public Earthquake() {

    }

    public Earthquake(String title, String magnitude, String location, String depth, String description, String link, String pubDate,
                      String category, String geoLat, String geoLong) {
        title = this.title;
        magnitude = this.magnitude;
        location = this.location;
        depth = this.depth;
        description = this.description;
        link = this.link;
        pubDate = this.pubDate;
        category = this.category;
        geoLat = this.geoLat;
        geoLong = this.geoLong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
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

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(String geoLat) {
        this.geoLat = geoLat;
    }

    public String getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(String geoLong) {
        this.geoLong = geoLong;
    }


    public String getDetails() {
        return "Magnitude: " + magnitude + '\n' +
                "Depth: " + depth + '\n' +
                "PubDate: " + pubDate + '\n' +
                "Coordinates: " + geoLat + ", " + geoLong + '\n' +
                "Link: " + link;

    }

    public static Comparator<Earthquake> locationCompare = new Comparator<Earthquake>() {
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            String location1 = e1.getLocation();
            String location2 = e2.getLocation();
            return location1.compareTo(location2);
        }};

    public static Comparator<Earthquake> magnitudeCompare = new Comparator<Earthquake>() {
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            String magnitude1 = e1.getMagnitude();
            String magnitude2 = e2.getMagnitude();
            return magnitude1.compareTo(magnitude2);
        }};

    @Override
    public String toString() {
        return
//                "title= " + title + '\n' +
                location + '\n' +
                        "Magnitude: " + magnitude + '\n' +
                        "Depth: " + depth + '\n' +
                        "PubDate: " + pubDate + '\n' +
                        "Coordinates: " + geoLat + ", " + geoLong + '\n' +
                        "Link: " + link;
    }
}
