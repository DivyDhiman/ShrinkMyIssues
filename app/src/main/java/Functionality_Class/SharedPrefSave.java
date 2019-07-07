package Functionality_Class;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Abhay dhiman
 */

//Shared preference saved class for saving data in shared preferences
public class SharedPrefSave {

    private SharedPreferences Shrink_My_Issue;
    private static String file = "Shrink_My_Issue";

    //SharedPrefSave constructor
    public SharedPrefSave(Context context) {
        Shrink_My_Issue = ((Activity) context).getSharedPreferences(file, 0);
    }

    //Shared Preferences saved boolean value
    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editors = Shrink_My_Issue.edit();
        editors.putBoolean(key, value);
        editors.commit();
    }

    //Shared Preferences saved string value
    public void setString(String key, String value) {
        SharedPreferences.Editor editors = Shrink_My_Issue.edit();
        editors.putString(key, value);
        editors.commit();
    }

    //Shared Preferences saved long value
    public void setLong(String key, long value) {
        SharedPreferences.Editor editors = Shrink_My_Issue.edit();
        editors.putLong(key, value);
        editors.commit();
    }

    //Shared Preferences get long value
    public long getLong(String key) {
        long res = Shrink_My_Issue.getLong(key, 0);
        return res;
    }

    //Shared Preferences get boolean value
    public boolean getBoolean(String key) {
        boolean result = Shrink_My_Issue.getBoolean(key, false);
        return result;
    }

    //Shared Preferences get string value
    public String getString(String key) {
        String res = Shrink_My_Issue.getString(key, "AS");
        return res;
    }

    //Remove values from shared preferences
    public void removesharedpreferences(String key) {
        Shrink_My_Issue.edit().remove(key).commit();
    }
}