package com.gcu.mpd.S1434184;

import java.util.Comparator;

/**
 *  * Name                 Niklas Olsson
 *  * Student ID           S1434184
 *  * Programme of Study   Computing
 *  *
 *  * @author Niklas
 *  * @version 1.0
 */
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
     * Constructor method for Earthquake.
     */
    public Earthquake() {

    }

    /**
     * Method to get earthquake title.
     *
     * @return  Earthquake title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method to set earthquake title.
     *
     * @param title String to set earthquake title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Method to get earthquake magnitude.
     *
     * @return Double earthquake magnitude
     */
    public Double getMagnitude() {
        return magnitude;
    }

    /**
     * Method to set earthquake magnitude
     *
     * @param magnitude Double to set earthquake magnitude.
     */
    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * Method to get earthquake location.
     *
     * @return String earthquake location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Method to set earthquake location.
     *
     * @param location String to set earthquake location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *  Method to get earthquake depth.
     *
     * @return int earthquake depth.
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Method to set earthquake depth.
     *
     * @param depth int to set earthquake depth.
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }


    /**
     * Method to get earthquake description. Long unformatted value from BSG Seismology
     *
     * @return String earthquake description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to set earthquake description.
     *
     * @param description String to set earthquake description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method to get earthquake link.
     *
     * @return String earthquake link.
     */
    public String getLink() {
        return link;
    }

    /**
     * Method to set earthquake link.
     *
     * @param link String to set earthquake link.
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Method to get earthquake publication date.
     *
     * @return String earthquake publication date.
     */
    public String getPubDate() {
        return pubDate;
    }

    /**
     * Method to set earthquake publication date.
     * Value must be formatted as: dd MM uuuu.
     *
     * @param pubDate String to set earthquake publication date.
     */
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * Method to get earthquake category.
     *
     * @return String earthquake category.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Method to set earthquake category.
     *
     * @param category String to set earthquake category.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Method to get earthquake latitude.
     *
     * @return Double earthquake latitude.
     */
    public double getGeoLat() {
        return geoLat;
    }

    /**
     * Method to set earthquake latitude.
     *
     * @param geoLat Double to set earthquake latitude.
     */
    public void setGeoLat(double geoLat) {
        this.geoLat = geoLat;
    }

    /**
     * Method to get earthquake longitude.
     *
     * @return Double earthquake longitude.
     */
    public double getGeoLong() {
        return geoLong;
    }

    /**
     * Method to set earthquake longitude.
     *
     * @param geoLong Double to set earthquake longitude.
     */
    public void setGeoLong(double geoLong) {
        this.geoLong = geoLong;
    }


    public static Comparator<Earthquake> locationCompare_A_z = new Comparator<Earthquake>() {
        /**
         * Comparator method to earthquake location in alphabetical order.
         *
         * @param e1 1st earthquake to compare.
         * @param e2 2nd earthquake to compare.
         * @return int of earthquake order.
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            String l1 = e1.getLocation();
            String l2 = e2.getLocation();
            return l1.compareTo(l2);
        }};
    public static Comparator<Earthquake> locationCompare_Z_a = new Comparator<Earthquake>() {
        /**
         * Comparator method to earthquake location in reversed alphabetical order.
         *
         * @param e1 1st earthquake to compare.
         * @param e2 2nd earthquake to compare.
         * @return int of earthquake order.
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            String l1 = e1.getLocation();
            String l2 = e2.getLocation();
            return l2.compareTo(l1);
        }};


    public static Comparator<Earthquake> magnitudeCompare_HighLow = new Comparator<Earthquake>() {
        /**
         * Comparator method to earthquake magnitude in ascending order.
         *
         * @param e1 1st earthquake to compare.
         * @param e2 2nd earthquake to compare.
         * @return int of earthquake order.
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double m1 = e1.getMagnitude();
            Double m2 = e2.getMagnitude();
            return m2.compareTo(m1);
        }};

    public static Comparator<Earthquake> magnitudeCompare_LowHigh = new Comparator<Earthquake>() {
        /**
         * Comparator method to earthquake magnitude in descending order.
         *
         * @param e1 1st earthquake to compare.
         * @param e2 2nd earthquake to compare.
         * @return int of earthquake order.
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double m1 = e1.getMagnitude();
            Double m2 = e2.getMagnitude();
            return m1.compareTo(m2);
        }};

    public static Comparator<Earthquake> depthCompare_HighLow = new Comparator<Earthquake>() {
        /**
         * Comparator method to earthquake depth in ascending order.
         *
         * @param e1 1st earthquake to compare.
         * @param e2 2nd earthquake to compare.
         * @return int of earthquake order.
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            int d1 = e1.getDepth();
            int d2 = e2.getDepth();
            return d2 - d1;
        }};

    public static Comparator<Earthquake> depthCompare_LowHigh = new Comparator<Earthquake>() {
        /**
         * Comparator method to earthquake depth in descending order.
         *
         * @param e1 1st earthquake to compare.
         * @param e2 2nd earthquake to compare.
         * @return int of earthquake order.
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            int d1 = e1.getDepth();
            int d2 = e2.getDepth();
            return d1 - d2;
        }};

    public static Comparator<Earthquake> positionCompare_North = new Comparator<Earthquake>() {
        /**
         * Comparator method to earthquake coordinates in ascending order by most Northern position.
         *
         * @param e1 1st earthquake to compare.
         * @param e2 2nd earthquake to compare.
         * @return int of earthquake order.
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double p1 = e1.getGeoLat();
            Double p2 = e2.getGeoLat();
            return p2.compareTo(p1);
        }};
    public static Comparator<Earthquake> positionCompare_South = new Comparator<Earthquake>() {
        /**
         * Comparator method to earthquake coordinates in ascending order by most Southern position.
         *
         * @param e1 1st earthquake to compare.
         * @param e2 2nd earthquake to compare.
         * @return int of earthquake order.
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double p1 = e1.getGeoLat();
            Double p2 = e2.getGeoLat();
            return p1.compareTo(p2);
        }};
    public static Comparator<Earthquake> positionCompare_East = new Comparator<Earthquake>() {
        /**
         * Comparator method to earthquake coordinates in ascending order by most Eastern position.
         *
         * @param e1 1st earthquake to compare.
         * @param e2 2nd earthquake to compare.
         * @return int of earthquake order.
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double p1 = e1.getGeoLong();
            Double p2 = e2.getGeoLong();
            return p2.compareTo(p1);
    }};
    public static Comparator<Earthquake> positionCompare_West = new Comparator<Earthquake>() {
        /**
         * Comparator method to earthquake coordinates in ascending order by most Western position.
         *
         * @param e1 1st earthquake to compare.
         * @param e2 2nd earthquake to compare.
         * @return int of earthquake order.
         */
        @Override
        public int compare(Earthquake e1, Earthquake e2) {
            Double p1 = e1.getGeoLong();
            Double p2 = e2.getGeoLong();
            return p1.compareTo(p2);
        }};

    /**
     * Method to return Earthquake object to string.
     *
     * @return String of Earthquake object.
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
