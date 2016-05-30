package nl.hsleiden.vault.vault;

import android.app.Activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kay on 10-4-2016.
 */
public class Advies {
    public String adviess = null;
    public int currentEcts = 0;
    public int period = 0;


    public Advies(Activity context){
        currentEcts = Integer.valueOf(context.getSharedPreferences("userData",0).getString("EC","0"));

        if (currentEcts < 40){
            adviess =   context.getResources().getString(R.string.advies1);
        } else if (currentEcts < 50){
            adviess = context.getResources().getString(R.string.advies2);
        } else if (currentEcts < 60){
            adviess = context.getResources().getString(R.string.advies3);
        } else {
            adviess = context.getResources().getString(R.string.advies4);
        }

        context.getSharedPreferences("userData",0).edit().putString("Advice",adviess).commit();
    }

    public String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        String dateString = dateFormat.format(date);
        System.out.println(dateString); //2014/08/06 15:59:48
        return dateString;
    }
}


