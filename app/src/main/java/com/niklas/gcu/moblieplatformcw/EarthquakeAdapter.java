package com.niklas.gcu.moblieplatformcw;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class EarthquakeAdapter extends ArrayAdapter {
    private static final String TAG = "EarthquakeAdapter";
    private final int layoutResource;
    private final  LayoutInflater layoutInflater;
    private List<Earthquake> earthquakes;

    public EarthquakeAdapter(Context context, int resource, List<Earthquake> earthquakes) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.earthquakes = earthquakes;
    }

    @Override
    public int getCount() {
        return earthquakes.size();
    }


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

        Earthquake currentEarthquake = earthquakes.get(position);

        viewHolder.tvLocation.setText(currentEarthquake.getLocation());
        viewHolder.tvMagnitude.setText(String.format("M %s", currentEarthquake.getMagnitude()));
        viewHolder.tvDate.setText(currentEarthquake.getPubDate());
        viewHolder.tvDetails.setText(currentEarthquake.getDetails());

        return convertView;
    }

    private class ViewHolder{
        final TextView tvLocation;
        final TextView tvMagnitude;
        final TextView tvDate;
        final TextView tvDetails;

        ViewHolder(View v){
            this.tvLocation = v.findViewById(R.id.tvLocation);
            this.tvMagnitude = v.findViewById(R.id.tvMagnitude);
            this.tvDate = v.findViewById(R.id.tvPubDate);
            this.tvDetails = v.findViewById(R.id.tvDetails);
        }

    }

//    public void filter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        earthquakes.clear();
//        if (charText.length() == 0) {
//            earthquakes.addAll(arraylist);
//        }
//        else
//        {
//            for (WorldPopulation wp : arraylist)
//            {
//                if (wp.getCountry().toLowerCase(Locale.getDefault()).contains(charText))
//                {
//                    worldpopulationlist.add(wp);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
}
