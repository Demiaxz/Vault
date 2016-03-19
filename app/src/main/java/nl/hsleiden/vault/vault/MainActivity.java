package nl.hsleiden.vault.vault;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import nl.hsleiden.vault.vault.fetcher.HttpFetcher;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    //private static MyApp instance;
    private static Context mContext;

    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Geef mij de application context, hoerrrr
        mContext = getApplicationContext();
        //Dit is een veiligheidje omdat hij anders gaat huilen over internet.
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //standaard shit
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dit moet later de input worden vanaf het betreffende venster.
        String username = "";
        String password = "";

        //Try Catch doordat er verschillende shit fout kan gaan.
        try {
            //maak een nieuwe fetcher en ga shit ophalen voor ons
            HttpFetcher loginActivity = new HttpFetcher(username, password,getContext());
            //Onderstaand statement geeft het cijfer van een vak terug.
            //System.out.println(sharedPrefference.getVakData(getContext()).getString("ICOMMH", ""));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Benodigd voor alles.
    public static Context getContext() {
        //  return instance.getApplicationContext();
        return mContext;
    }


}