package com.niklas.gcu.moblieplatformcw;

import java.util.Comparator;

public class Earthquake {

    private String title;
    private double magnitude;
    private String location;
    private String depth;
    private String description;
    private String link;
    private String pubDate;
    private String category;
    private double geoLat;
    private double geoLong;

    public Earthquake() {

    }

    public Earthquake(String title, double magnitude, String location, String depth, String description, String link, String pubDate,
                      String category, double geoLat, double geoLong) {
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

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
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

    public double getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(double geoLat) {
        this.geoLat = geoLat;
    }

    public double getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(double geoLong) {
        this.geoLong = geoLong;
    }


    public String getDetails() {
        return "Magnitude: " + magnitude + '\n' +
                "Depth: " + depth + '\n' +
                "PubDate: " + pubDate + '\n' +
                "Coordinates: " + geoLat + ", " + geoLong + '\n' +
                "Link: " + link;

    }

    public static Comparator<Earthquake> locationCompare_Az = new Comparator<Earthquake>() {
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            String l1 = e1.getLocation();
            String l2 = e2.getLocation();
            return l1.compareTo(l2);
        }};

    public static Comparator<Earthquake> locationCompare_Za = new Comparator<Earthquake>() {
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            String l1 = e1.getLocation();
            String l2 = e2.getLocation();
            return l2.compareTo(l1);
        }};


    public static Comparator<Earthquake> magnitudeCompare_HighLow = new Comparator<Earthquake>() {
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double m1 = e1.getMagnitude();
            Double m2 = e2.getMagnitude();
            return m2.compareTo(m1);
        }};

    public static Comparator<Earthquake> positionCompare_North = new Comparator<Earthquake>() {
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double p1 = e1.getGeoLat();
            Double p2 = e2.getGeoLat();
            return p2.compareTo(p1);
        }};
    public static Comparator<Earthquake> positionCompare_South = new Comparator<Earthquake>() {
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double p1 = e1.getGeoLat();
            Double p2 = e2.getGeoLat();
            return p1.compareTo(p2);
        }};
    public static Comparator<Earthquake> positionCompare_East = new Comparator<Earthquake>() {
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double p1 = e1.getGeoLong();
            Double p2 = e2.getGeoLong();
            return p2.compareTo(p1);
        }};
    public static Comparator<Earthquake> positionCompare_West = new Comparator<Earthquake>() {
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double p1 = e1.getGeoLong();
            Double p2 = e2.getGeoLong();
            return p1.compareTo(p2);
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
