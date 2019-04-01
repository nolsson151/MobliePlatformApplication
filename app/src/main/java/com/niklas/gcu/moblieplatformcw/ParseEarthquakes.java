package com.niklas.gcu.moblieplatformcw;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.XMLFormatter;

public class ParseEarthquakes {
    private static final String TAG = "ParseEarthquakes";
    private ArrayList<Earthquake> earthquakes;

    public ParseEarthquakes(){
        this.earthquakes = new ArrayList<>();
    }

    public ArrayList<Earthquake> getEarthquakes(){
        return earthquakes;
    }

    public boolean parse(String xml){
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
                                StringBuilder depthBuilder = new StringBuilder(strings[3]);
                                StringBuilder magBuilder = new StringBuilder(strings[4]);
                                String location = locationBuilder.substring(11, locationBuilder.length()-1);
                                String depth = depthBuilder.substring(8, depthBuilder.length()-1);
                                if(depth.length() <= 2) {
                                    depth = "0 km";
                                }
                                Double magnitude = Double.parseDouble(magBuilder.substring(12));
                                earthquake.setLocation(location);
                                earthquake.setDepth(depth);
                                earthquake.setMagnitude(magnitude);

                            } else if("link".equalsIgnoreCase(tagname)){
                                earthquake.setLink(text);
                            } else if("pubDate".equalsIgnoreCase(tagname)){
                                StringBuilder dateBuilder = new StringBuilder(text);
                                String date = dateBuilder.substring(5, 16);
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
//            for(Earthquake e: earthquakes) {
//                Log.d(TAG, "**********************");
//                Log.d(TAG, e.toString());
//            }

        }catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        return status;
    }
}