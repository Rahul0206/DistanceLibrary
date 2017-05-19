package rahulsharma.distancecalculation;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;

/**
 * Created by Rahul on 09-May-17.
 */

public class AppPrefrence extends PreferenceActivity {

    public static final String PREFRENCE_NAME = "DistancePrefrence";
    public static final String KEY_PREVIOUS_LOCATION_LAT = "previous_location_lat";
    public static final String KEY_PREVIOUS_LOCATION_LNG = "previous_location_lng";

    public static final String KEY_PREVIOUS_POINT_DISTANCE = "previous_point_distance";
    public static final String KEY_PREVIOUS_DISTANCE = "previous_distance";
    public static final String KEY_CALCULATED_DISTANCE = "calculated_distance";
    public static final String KEY_LAST_SPEED = "last_speed";

    public static String setPreviousLocLat(Context context, String previousLocLat) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PREVIOUS_LOCATION_LAT, previousLocLat);
        editor.commit();
        return previousLocLat;
    }

    public static String getPreviousLocLat(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PREVIOUS_LOCATION_LAT, "0.0");
    }

    public static String setPreviousLocLng(Context context, String previousLocLng) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PREVIOUS_LOCATION_LNG, previousLocLng);
        editor.commit();
        return previousLocLng;
    }

    public static String getPreviousLocLng(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PREVIOUS_LOCATION_LNG, "0.0");
    }

    public static String setPreviousPointDistance(Context context, String distance) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PREVIOUS_POINT_DISTANCE, distance);
        editor.commit();
        return distance;
    }

    public static String getPreviousPointDistance(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PREVIOUS_POINT_DISTANCE, "0.0");
    }

    public static String setPreviousDistance(Context context, String distance) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PREVIOUS_DISTANCE, distance);
        editor.commit();
        return distance;
    }

    public static String getPreviousDistance(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PREVIOUS_DISTANCE, "0");
    }

    public static String setPreviousSpeed(Context context, String speed) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LAST_SPEED, speed);
        editor.commit();
        return speed;
    }

    public static String getPreviousSpeed(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LAST_SPEED, "0");
    }

}
