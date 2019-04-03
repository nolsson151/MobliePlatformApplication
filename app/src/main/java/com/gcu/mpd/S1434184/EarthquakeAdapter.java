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

public class EarthquakeAdapter extends ArrayAdapter implements Filterable {
    private static final String TAG = "EarthquakeAdapter";
    private final int layoutResource;
    private final  LayoutInflater layoutInflater;
    public List<Earthquake> earthquakes;
    public ArrayList<Earthquake> arrayList;


    public EarthquakeAdapter(Context context, int resource, List<Earthquake> earthquakes) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.earthquakes = earthquakes;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(earthquakes);
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
        ColorPicker colorPicker;
        colorPicker = new ColorPicker();
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
        colorPicker.setColor(viewHolder, currentEarthquake.getMagnitude());

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
                Log.d(TAG, "onClick: Earthquake selected "+currentEarthquake.getLocation()+ " at position "+position);
                view.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    private class ColorPicker{
    private static final String TAG = "ColorPicker";


        public void setColor(ViewHolder viewHolder , double d) {
            Log.d(TAG, "setColor: started on"+ viewHolder.toString());

            if(d < 0.0){
                viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded0));
            }
            else if(d <=0.9){
                viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded1));
            }
            else if(d <=1.9){
                viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded2));
            }
            else if(d <=2.9){
                viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded3));
            }
            else if(d <=3.9){
                viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded4));
            }
            else if(d <=4.9){
                viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded5));
            }
            else if(d <=5.9){
                viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded6));
            }
            else if(d <=6.9){
                viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded7));
            }
            else{
                viewHolder.tvMagnitude.setBackground(getContext().getResources().getDrawable(R.drawable.rounded2));
            }
        }
    }

    private class ViewHolder{
        private static final String TAG = "ViewHolder";
        final TextView tvLocation, tvMagnitude, tvDate, tvDepth, tvCoordinates;

        ViewHolder(View v){
            Log.d(TAG, "ViewHolder: object created");
            this.tvLocation = v.findViewById(R.id.tvLocation);
            this.tvMagnitude = v.findViewById(R.id.tvMagnitude);
            this.tvDate = v.findViewById(R.id.tvPubDate);
            this.tvDepth = v.findViewById(R.id.tvDepth);
            this.tvCoordinates = v.findViewById(R.id.tvCoordinates);
        }
    }

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
