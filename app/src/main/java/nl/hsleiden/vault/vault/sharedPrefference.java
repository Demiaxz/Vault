package nl.hsleiden.vault.vault;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Perseus on 18-03-16.
 */
public class sharedPrefference {
    private static String PREF_DATA = "vakData";
    private static String USER_PIC = "userPic";

    public static SharedPreferences getVakData(Context c){
        return c.getSharedPreferences(PREF_DATA,0);
    }

    public static SharedPreferences getUserPic(Context c){
        return c.getSharedPreferences(USER_PIC,0);
    }
}
