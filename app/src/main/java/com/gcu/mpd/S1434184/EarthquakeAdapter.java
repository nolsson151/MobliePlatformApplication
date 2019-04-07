package com.gcu.mpd.S1434184;

//
// Name                 Niklas Olsson
// Student ID           S1434184
// Programme of Study   Computing
//

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *  * Name                 Niklas Olsson
 *  * Student ID           S1434184
 *  * Programme of Study   Computing
 *  *
 *  * @author Niklas
 *  * @version 1.0
 */
public class EarthquakeAdapter extends ArrayAdapter implements Filterable {
    private static final String TAG = "EarthquakeAdapter";
    private final int layoutResource;
    private final  LayoutInflater layoutInflater;
    public List<Earthquake> earthquakes;
    public ArrayList<Earthquake> arrayList;


    /**
     * Constructor method for EarthquakeAdapter.
     *
     * @param context Context from activity
     * @param resource Application resource documents
     * @param earthquakes ArrayList of earthquakes
     */
    public EarthquakeAdapter(Context context, int resource, List<Earthquake> earthquakes) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.earthquakes = earthquakes;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(earthquakes);
    }

    /**
     * Method used to return the size of earthquake ArrayList.
     *
     * @return ArrayList size.
     */
    @Override
    public int getCount() {
        return earthquakes.size();
    }

    /**
     * Method used to return Earthquake object using ArrayAdapter position.
     *
     * @param position Position in ArrayAdapter.
     * @return Earthquake object.
     */
    @Override
    public Object getItem(int position){
        return earthquakes.get(position);
    }

    /**
     * Method to get View using ViewHolder pattern. A ViewHolder object is created finding the
     * the required TextView and it's value is set from the earthqaukes stored in the earthquakes
     * ArrayList.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(layoutResource,parent,false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Earthquake currentEarthquake = earthquakes.get(position);

        viewHolder.tvLocation.setText(currentEarthquake.getLocation());
        viewHolder.tvMagnitude.setText(String.format("M %s", currentEarthquake.getMagnitude()));
        viewHolder.tvDate.setText(currentEarthquake.getPubDate());
        viewHolder.tvDepth.setText(String.format("Depth: %s km", currentEarthquake.getDepth()));
        viewHolder.tvCoordinates.setText(String.format("Coordinates: %s, %s", currentEarthquake.getGeoLat(), currentEarthquake.getGeoLong()));
        setColor(viewHolder, currentEarthquake.getMagnitude());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EarthquakeActivity.class);
                intent.putExtra("title", earthquakes.get(position).getTitle());
                intent.putExtra("magnitude", earthquakes.get(position).getMagnitude());
                intent.putExtra("location", earthquakes.get(position).getLocation());
                intent.putExtra("depth", earthquakes.get(position).getDepth());
                intent.putExtra("link", earthquakes.get(position).getLink());
                intent.putExtra("pubDate", earthquakes.get(position).getPubDate());
                intent.putExtra("time",earthquakes.get(position).getTime());
                intent.putExtra("category", earthquakes.get(position).getCategory());
                intent.putExtra("geoLat", earthquakes.get(position).getGeoLat());
                intent.putExtra("geoLong", earthquakes.get(position).getGeoLong());
                Log.d(TAG, "onClick: Earthquake selected "+currentEarthquake.getLocation()+ " at position "+position);
                view.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    /**
     * Method used to style the magnitude TextView. If a certain magnitude is given, the drawable
     * chosen will reflect the severity of the earthquake, grey being severe and red being most
     * severe.
     *
     * @param viewHolder ViewHolder object to set color on
     * @param magnitude Magnitude to check severity of
     */
    public void setColor(ViewHolder viewHolder , double magnitude) {
        Log.d(TAG, "setColor: started on"+ viewHolder.toString());

        if(magnitude < 0.0){
            viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded0));
        }
        else if(magnitude <=0.9){
            viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded1));
        }
        else if(magnitude <=1.9){
            viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded2));
        }
        else if(magnitude <=2.9){
            viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded3));
        }
        else if(magnitude <=3.9){
            viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded4));
        }
        else if(magnitude <=4.9){
            viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded5));
        }
        else if(magnitude <=5.9){
            viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded6));
        }
        else if(magnitude <=6.9){
            viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded7));
        }
        else{
            viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded8));
        }
    }


    private class ViewHolder{
        private static final String TAG = "ViewHolder";
        final TextView tvLocation, tvMagnitude, tvDate, tvDepth, tvCoordinates;

        /**
         * Constructor method of ViewHolder.
         *
         * @param v
         */
        ViewHolder(View v){
            Log.d(TAG, "ViewHolder: object created");
            this.tvLocation = v.findViewById(R.id.tvLocation);
            this.tvMagnitude = v.findViewById(R.id.tvMagnitude);
            this.tvDate = v.findViewById(R.id.tvPubDate);
            this.tvDepth = v.findViewById(R.id.tvDepth);
            this.tvCoordinates = v.findViewById(R.id.tvCoordinates);
        }
    }

    /**
     * Method used to filter EarthquakeAdapter. Method takes a sequence of characters to search for
     * and matches them against any earthquakes location String value. The earthquake ArrayList is
     * first emptied. If there is no value given, earthquake ArrayList is repopulated from a
     * secondary ArrayList that contains the original earthquakes ArrayList set during the class
     * constructor. If there is a value given that matches an earthquake' location, the earthquake
     * is added from the secondary ArrayList to the original earthquakes ArrayList, repopulating the
     * list with only matched earthquakes.
     *
     * @param charText String given to filter for.
     */
    public void filter(String charText){
        charText = charText.toUpperCase(Locale.getDefault());
        earthquakes.clear();
        if(charText.length()==0){
            earthquakes.addAll(arrayList);
        }
        else {
            for (Earthquake earthquake : arrayList){
                if(earthquake.getLocation().contains(charText)){
                    earthquakes.add(earthquake);
                }
            }
        }
        notifyDataSetChanged();
    }
}
