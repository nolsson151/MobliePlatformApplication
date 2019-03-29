package com.niklas.gcu.moblieplatformcw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DatabaseHandler {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "earthquakeDB";
    private static final String TABLE_EARTHQUAKES = "earthquakes";

    private Context context;
    private SQLiteDatabase db;

    public DatabaseHandler(Context context)
    {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();


    }
    public boolean insert(String title, String magnitude, String location, String link, String pubDate, String geoLat, String geoLng) {
        SQLiteDatabase db = this.db;
        ContentValues contentValues = new ContentValues();
        if(eqLoc !=null) {
            contentValues.put("Location", eqLoc);
            contentValues.put("Magnitude", eqMagni);
            contentValues.put("Depth", eqDepth);
            contentValues.put("Link", link);
            contentValues.put("Date", pubDate.substring(5,16));
            contentValues.put("geoLat", geoLat);
            contentValues.put("geoLng", geoLng);
//            Log.e("PUBDATE ", "- Location - " + eqLoc + "\n - Date - " + pubDate.substring(5,16) + "\n - Magnitude - " + eqMagni + "\n - Depth - " + eqDepth + "\n - Link - " + link + "\n - Geo Lat - " + geoLat + "\n- Geo Lng - " + geoLng + "\n\n");
            db.insert("earthquakes", null, contentValues);
        }
        return true;
    }


    private static class OpenHelper extends SQLiteOpenHelper
    {

        OpenHelper(Context context)
        {
            super(context, (String) DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("CREATE TABLE " + TABLE_EARTHQUAKES + "(id INTEGER PRIMARY KEY, Location TEXT, Magnitude TEXT, Depth TEXT, Link TEXT, Date TEXT, geoLat TEXT, geoLng TEXT)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w("Example", "Upgrading database, this will drop tables and recreate.");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EARTHQUAKES);
            onCreate(db);
        }
    }

}
