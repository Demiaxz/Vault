package nl.hsleiden.vault.vault;

import android.support.v7.app.AppCompatActivity;


/**
 * Created by Kay on 10-4-2016.
 */

public class advies extends AppCompatActivity{

    public static final int MAX_ECTS = 60;
    public static int currentEcts = 0;
    public String advies;

    advies x = new advies();

    public String advies(){

        if (currentEcts < 40){
            advies = String.valueOf(R.string.advies1);
        } else if (currentEcts < 50){
            advies = String.valueOf(R.string.advies2);
        } else if (currentEcts < 60){
            advies = String.valueOf(R.string.advies3);
        } else{
            advies = String.valueOf(R.string.advies4);
        }
        return advies;
    }

}
