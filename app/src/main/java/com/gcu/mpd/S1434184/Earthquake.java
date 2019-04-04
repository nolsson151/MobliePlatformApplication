package com.gcu.mpd.S1434184;

//
// Name                 Niklas Olsson
// Student ID           S1434184
// Programme of Study   Computing
//

import java.util.Comparator;

public class Earthquake {

    private String title;
    private double magnitude;
    private String location;
    private int depth;
    private String description;
    private String link;
    private String pubDate;
    private String category;
    private double geoLat;
    private double geoLong;

    /**
     *
     */
    public Earthquake() {

    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public Double getMagnitude() {
        return magnitude;
    }

    /**
     *
     * @param magnitude
     */
    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     */
    public int getDepth() {
        return depth;
    }

    /**
     *
     * @param depth
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }


    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getLink() {
        return link;
    }

    /**
     *
     * @param link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     *
     * @return
     */
    public String getPubDate() {
        return pubDate;
    }

    /**
     *
     * @param pubDate
     */
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    /**
     *
     * @return
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     */
    public double getGeoLat() {
        return geoLat;
    }

    /**
     *
     * @param geoLat
     */
    public void setGeoLat(double geoLat) {
        this.geoLat = geoLat;
    }

    /**
     *
     * @return
     */
    public double getGeoLong() {
        return geoLong;
    }

    /**
     *
     * @param geoLong
     */
    public void setGeoLong(double geoLong) {
        this.geoLong = geoLong;
    }


    public static Comparator<Earthquake> locationCompare_A_z = new Comparator<Earthquake>() {
        /**
         *
         * @param e1
         * @param e2
         * @return
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            String l1 = e1.getLocation();
            String l2 = e2.getLocation();
            return l1.compareTo(l2);
        }};
    public static Comparator<Earthquake> locationCompare_Z_a = new Comparator<Earthquake>() {
        /**
         *
         * @param e1
         * @param e2
         * @return
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            String l1 = e1.getLocation();
            String l2 = e2.getLocation();
            return l2.compareTo(l1);
        }};


    public static Comparator<Earthquake> magnitudeCompare_HighLow = new Comparator<Earthquake>() {
        /**
         *
         * @param e1
         * @param e2
         * @return
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double m1 = e1.getMagnitude();
            Double m2 = e2.getMagnitude();
            return m2.compareTo(m1);
        }};

    public static Comparator<Earthquake> magnitudeCompare_LowHigh = new Comparator<Earthquake>() {
        /**
         *
         * @param e1
         * @param e2
         * @return
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double m1 = e1.getMagnitude();
            Double m2 = e2.getMagnitude();
            return m1.compareTo(m2);
        }};

    public static Comparator<Earthquake> depthCompare_HighLow = new Comparator<Earthquake>() {
        /**
         *
         * @param e1
         * @param e2
         * @return
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            int d1 = e1.getDepth();
            int d2 = e2.getDepth();
            return d2 - d1;
        }};

    public static Comparator<Earthquake> depthCompare_LowHigh = new Comparator<Earthquake>() {
        /**
         *
         * @param e1
         * @param e2
         * @return
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            int d1 = e1.getDepth();
            int d2 = e2.getDepth();
            return d1 - d2;
        }};

    public static Comparator<Earthquake> positionCompare_North = new Comparator<Earthquake>() {
        /**
         *
         * @param e1
         * @param e2
         * @return
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double p1 = e1.getGeoLat();
            Double p2 = e2.getGeoLat();
            return p2.compareTo(p1);
        }};
    public static Comparator<Earthquake> positionCompare_South = new Comparator<Earthquake>() {
        /**
         *
         * @param e1
         * @param e2
         * @return
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double p1 = e1.getGeoLat();
            Double p2 = e2.getGeoLat();
            return p1.compareTo(p2);
        }};
    public static Comparator<Earthquake> positionCompare_East = new Comparator<Earthquake>() {
        /**
         *
         * @param e1
         * @param e2
         * @return
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double p1 = e1.getGeoLong();
            Double p2 = e2.getGeoLong();
            return p2.compareTo(p1);
    }};
    public static Comparator<Earthquake> positionCompare_West = new Comparator<Earthquake>() {
        /**
         *
         * @param e1
         * @param e2
         * @return
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double p1 = e1.getGeoLong();
            Double p2 = e2.getGeoLong();
            return p1.compareTo(p2);
        }};

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Earthquake{" +
                "title='" + title + '\'' +
                ", magnitude=" + magnitude +
                ", location='" + location + '\'' +
                ", depth=" + depth +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", category='" + category + '\'' +
                ", geoLat=" + geoLat +
                ", geoLong=" + geoLong +
                '}';
    }
}
