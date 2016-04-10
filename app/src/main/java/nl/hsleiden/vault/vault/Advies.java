package nl.hsleiden.vault.vault;

import android.app.Activity;

/**
 * Created by Kay on 10-4-2016.
 */
public class Advies {
    public String adviess = null;
    public int currentEcts = 0;

    public Advies(Activity context){
        currentEcts = Integer.valueOf(context.getSharedPreferences("userData",0).getString("EC","0"));

        if (currentEcts < 40){
            adviess = String.valueOf(R.string.advies1);
        } else if (currentEcts < 50){
            adviess = String.valueOf(R.string.advies2);
        } else if (currentEcts < 60){
            adviess = String.valueOf(R.string.advies3);
        } else{
            adviess = String.valueOf(R.string.advies4);
        }

        context.getSharedPreferences("userData",0).edit().putString("Advice",adviess).commit();
    }
}


