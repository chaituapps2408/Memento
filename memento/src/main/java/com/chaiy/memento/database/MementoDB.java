package com.chaiy.memento.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.chaiy.memento.database.columns.FavouriteColumns;
import com.chaiy.memento.database.columns.MovieColumns;
import com.chaiy.memento.database.columns.NewsColumns;
import com.chaiy.memento.database.columns.WeatherColumns;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.IfNotExists;
import net.simonvt.schematic.annotation.OnConfigure;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Chaiy on 12/4/2016.
 */

@Database(version = MementoDB.VERSION,
        packageName = "com.chaiy.memento.database.provider")
public class MementoDB {

    private static final String TAG = MementoDB.class.getSimpleName();

    public static final int VERSION = 10;


    @Table(MovieColumns.class)
    @IfNotExists
    public static final String Movies = "Movies";

    @Table(NewsColumns.class)
    @IfNotExists
    public static final String News = "News";

    @Table(WeatherColumns.class)
    @IfNotExists
    public static final String Weather = "Weather";

    @Table(FavouriteColumns.class)
    @IfNotExists
    public static final String Favourite = "Favourite";


    @OnCreate
    public static void onCreate(Context context, SQLiteDatabase db) {
        Log.d(TAG, "in onCreate");
    }

    @OnUpgrade
    public static void onUpgrade(Context context, SQLiteDatabase db, int oldVersion,
                                 int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Movies);
            db.execSQL("DROP TABLE IF EXISTS " + News);
            db.execSQL("DROP TABLE IF EXISTS " + Weather);
            db.execSQL("DROP TABLE IF EXISTS " + Favourite);
        }
    }

    @OnConfigure
    public static void onConfigure(SQLiteDatabase db) {

    }


}
