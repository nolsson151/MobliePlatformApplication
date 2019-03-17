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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EarthquakeAdapter extends ArrayAdapter {
    private static final String TAG = "EarthquakeAdapter";
    private final int layoutResource;
    private final  LayoutInflater layoutInflater;
    private List<Earthquake> earthquakes;
    private ArrayList<Earthquake> arraylist;

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
    public Object getItem(int position){
        return earthquakes.get(position);
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
                intent.putExtra("category", earthquakes.get(position).getCategory());
                intent.putExtra("geoLat", earthquakes.get(position).getGeoLat());
                intent.putExtra("geoLong", earthquakes.get(position).getGeoLong());
                intent.putExtra("details", earthquakes.get(position).getDetails());
                view.getContext().startActivity(intent);

            }
        });

        return convertView;
    }

    private class ViewHolder{
        final TextView tvLocation, tvMagnitude, tvDate, tvDetails;

        ViewHolder(View v){
            this.tvLocation = v.findViewById(R.id.tvLocation);
            this.tvMagnitude = v.findViewById(R.id.tvMagnitude);
            this.tvDate = v.findViewById(R.id.tvPubDate);
            this.tvDetails = v.findViewById(R.id.tvDetails);
        }

    }

    public void filter(String charText) {
        arraylist.addAll(MainActivity.earthquakeArrayList);
        charText = charText.toLowerCase(Locale.getDefault());
        earthquakes.clear();
        if (charText.length() == 0) {
            earthquakes.addAll(arraylist);
        } else {
            for (Earthquake e : arraylist) {
                if (e.getLocation().toLowerCase(Locale.getDefault()).contains(charText)) {
                    earthquakes.add(e);
                }
            }
        }
        notifyDataSetChanged();
    }
}
