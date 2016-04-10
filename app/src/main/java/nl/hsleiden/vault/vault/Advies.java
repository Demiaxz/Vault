package nl.hsleiden.vault.vault;

import nl.hsleiden.vault.vault.fragments.testFragment;

/**
 * Created by Kay on 10-4-2016.
 */
public class Advies {
    public String advies;


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


