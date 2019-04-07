package com.gcu.mpd.S1434184;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 *  * Name                 Niklas Olsson
 *  * Student ID           S1434184
 *  * Programme of Study   Computing
 *  *
 *  * @author Niklas
 *  * @version 1.0
 */
public class ParseEarthquakes {
    private static final String TAG = "ParseEarthquakes";
    private ArrayList<Earthquake> earthquakes;

    /**
     * Constructor method for ParseEarthquakes.
     *
     */
    public ParseEarthquakes(){
        this.earthquakes = new ArrayList<>();
    }

    /**
     * Method returns an ArrayList of earthquakes.
     *
     * @return ArrayList of Earthquakes
     */
    public ArrayList<Earthquake> getEarthquakes(){
        return earthquakes;
    }

    /**
     * Method that parses downloaded XML data into values that are used to set the values of an
     * Earthquake object. The method uses an XmlPullParserFactory that begins at a start tag name,
     * 'item', and end tag '/item' and then searches for matching tag names that contain any data
     * between them. This data is cleaned and formatted using StringBuilders if needed before being
     * used to set as value of a newly created Earthquake object. This Earthquake object is then
     * added to the earthquakes ArrayList.
     *
     * @param xml String containing XML data
     * @return Status if parsing was completed successfully.
     */
    public boolean parse(String xml){
        Log.d(TAG, "parse: Begin");
        boolean status = true;
        Earthquake earthquake = null;
        boolean inItem = false;
        String text = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "parse: Starting tag for "+ tagname);
                        if("item".equalsIgnoreCase(tagname)){
                            inItem = true;
                            earthquake = new Earthquake();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "parse: Ending tag for " + tagname);
                        if (earthquake != null){
                            if ("item".equalsIgnoreCase(tagname)){
                                earthquakes.add(earthquake);
                                inItem = false;
                            } else if("title".equalsIgnoreCase(tagname)){
                                earthquake.setTitle(text);
                            } else if("description".equalsIgnoreCase(tagname)){
                                earthquake.setDescription(text);
                                String[] strings = earthquake.getDescription().split(";");

                                StringBuilder locationBuilder = new StringBuilder(strings[1]);
                                String location = locationBuilder.substring(11, locationBuilder.length()-1);

                                StringBuilder depthBuilder = new StringBuilder(strings[3]);
                                int depth = Integer.parseInt(depthBuilder.substring(8, depthBuilder.length()-4));

                                StringBuilder magBuilder = new StringBuilder(strings[4]);
                                Double magnitude = Double.parseDouble(magBuilder.substring(12));

                                earthquake.setLocation(location);
                                earthquake.setDepth(depth);
                                earthquake.setMagnitude(magnitude);
                            } else if("link".equalsIgnoreCase(tagname)){
                                earthquake.setLink(text);
                            } else if("pubDate".equalsIgnoreCase(tagname)){
                                StringBuilder dateBuilder = new StringBuilder(text);
                                String date = dateBuilder.substring(5, 16);
                                String time = dateBuilder.substring(17, dateBuilder.length());
                                earthquake.setTime(time);
                                earthquake.setPubDate(date);
                            } else if("category".equalsIgnoreCase(tagname)){
                                earthquake.setCategory(text);
                            } else if("lat".equalsIgnoreCase(tagname)){
                                double geoLat = Double.parseDouble(text);
                                earthquake.setGeoLat(geoLat);
                            } else if("long".equalsIgnoreCase(tagname)){
                                double geoLong = Double.parseDouble(text);
                                earthquake.setGeoLong(geoLong);
                            }
                        }
                        break;

                    default:
                }
                eventType = parser.next();
            }
        }catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        Log.d(TAG, "parse: Data successfully parsed. Earthquakes in array is "+earthquakes.size());
        return status;
    }
}