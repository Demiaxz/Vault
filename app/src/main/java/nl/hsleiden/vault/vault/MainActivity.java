package nl.hsleiden.vault.vault;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import nl.hsleiden.vault.vault.fetcher.AuthFetch;

public class MainActivity extends AppCompatActivity {
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //standaard shit
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get some shit
        Bundle b = getIntent().getExtras();
        //Try Catch doordat er verschillende shit fout kan gaan.
        if (new AuthFetch(b.getString("username"),b.getString("password"),getApplicationContext()).runAuth()) {
            Intent homepage = new Intent(MainActivity.this, menu.class).putExtra("loggedIn",true);
            startActivity(homepage);
            finish();
        }
        else{
            System.out.println("Verkeerde login.");
            getApplicationContext().getSharedPreferences("loginData",0).edit().clear().commit();
            startActivity(new Intent(MainActivity.this, start.class).putExtra("loginFail", true));
            finish();
        }
    }
}