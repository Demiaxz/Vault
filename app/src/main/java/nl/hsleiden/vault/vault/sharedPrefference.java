package nl.hsleiden.vault.vault;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Perseus on 18-03-16.
 */
public class sharedPrefference {
    private static String PREF_DATA = "vakData";

    public static SharedPreferences getVakData(Context c){
        return c.getSharedPreferences(PREF_DATA,0);
    }
}
