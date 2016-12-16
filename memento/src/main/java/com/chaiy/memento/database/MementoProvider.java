package com.chaiy.memento.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Chaiy on 12/4/2016.
 */

@ContentProvider(authority = MementoProvider.AUTHORITY, database = MementoDB.class,
        packageName = "com.chaiy.memento.database.provider")
public final class MementoProvider {

    public static final String AUTHORITY =
            "com.chaiy.memento.database.MementoProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    interface Path {
        String MOVIES = "Movies";
        String NEWS = "News";
        String WEATHER = "Weather";
        String FAVOURITE = "FavouriteColumns";
    }

    @TableEndpoint(table = MementoDB.Movies)
    public static class Movies {
        @ContentUri(
                path = Path.MOVIES,
                type = "vnd.android.cursor.dir/Movies"
        )
        public static final Uri CONTENT_URI = buildUri(Path.MOVIES);
       /* @InexactContentUri(
                name = "COUPON_ID",
                path = Path.COUPONS + "/#",
                type = "vnd.android.cursor.item/Coupon",
                whereColumn = DatabaseColumns._ID,
                pathSegment = 1
        )
        public static Uri withId(int id) {
            return buildUri(Path.COUPONS, String.valueOf(id));
        }*/

    }

    @TableEndpoint(table = MementoDB.News)
    public static class News {
        @ContentUri(
                path = Path.NEWS,
                type = "vnd.android.cursor.dir/News"
        )
        public static final Uri CONTENT_URI = buildUri(Path.NEWS);
    }

    @TableEndpoint(table = MementoDB.Weather)
    public static class Weather {
        @ContentUri(
                path = Path.WEATHER,
                type = "vnd.android.cursor.dir/Weather"
        )
        public static final Uri CONTENT_URI = buildUri(Path.WEATHER);
    }

    @TableEndpoint(table = MementoDB.Favourite)
    public static class Favourite {
        @ContentUri(
                path = Path.FAVOURITE,
                type = "vnd.android.cursor.dir/Favourite"
        )
        public static final Uri CONTENT_URI = buildUri(Path.FAVOURITE);
    }

}
