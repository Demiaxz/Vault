package nl.hsleiden.vault.vault;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Tegen het bitchen
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        //----------------------------------------------------------------------------------------------------------------------------//
        try { //geeft exception als de intent niet bestaat, oftewel wanneer hij niet eerder is aangeroepen.
            //in het geval van de if statement is het zo dat er verkeerd is ingelogd.
            if (getIntent().getExtras().getBoolean("loginFail")){
                Snackbar sb = Snackbar.make(findViewById(android.R.id.content), "Wrong combination of login.", Snackbar.LENGTH_LONG);
                sb.show();
                getIntent().getExtras().clear();
            }
            if (getIntent().getExtras().getBoolean("logout")){
                Snackbar sb = Snackbar.make(findViewById(android.R.id.content), "Logged out.", Snackbar.LENGTH_LONG);
                sb.show();
                getIntent().getExtras().clear();
            }
        }

        catch (NullPointerException er){
            Snackbar sb = Snackbar.make(findViewById(android.R.id.content), "Welcome, student.", Snackbar.LENGTH_LONG);
            sb.show();
        }
        //----------------------------------------------------------------------------------------------------------------------------//

        //IF USER IS KNOWN TO SYSTEM
        if (getSharedPreferences("loginData", 0).getAll().isEmpty()) {
            //haal cijfers knop
            Button cijfers = (Button) findViewById(R.id.cijfers);
            //wanneer ingedrukt
            cijfers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Cijfers knop ingedrukt.", "Start CourseListActivity");
                    Snackbar sb = Snackbar.make(v, "Attempting log in.", Snackbar.LENGTH_LONG);
                    sb.show();

                    TextView txtView = (TextView) findViewById(R.id.usernameField); //username
                    TextView txtoass = (TextView) findViewById(R.id.passwordField); //password both retrieved from textview
                    CheckBox remember = (CheckBox) findViewById(R.id.rememberMe); //remember me button

                    Intent intent = new Intent(start.this, MainActivity.class);
                    Bundle b = new Bundle();

                    if (remember.isChecked()) {
                        getSharedPreferences("loginData", 0).edit().putString("username", ((TextView) findViewById(R.id.usernameField)).getText().toString()).commit();
                        getSharedPreferences("loginData", 0).edit().putString("password", ((TextView) findViewById(R.id.passwordField)).getText().toString()).commit();
                        b.putString("username", getSharedPreferences("loginData", 0).getString("username", "s000000")); //Your id
                        b.putString("password", getSharedPreferences("loginData", 0).getString("password", "non")); //Your id
                        intent.putExtras(b);
                        startActivity(intent);
                        finish();
                        b.clear();

                    } else {
                        b.putString("username", ((TextView) findViewById(R.id.usernameField)).getText().toString()); //Your id
                        b.putString("password", ((TextView) findViewById(R.id.passwordField)).getText().toString()); //Your pass
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);
                        finish();
                        b.clear();
                    }
                }
            });
        }
        //IF USER IS NOT KNOWN TO SYSTEM
        else {
            Intent intent = new Intent(start.this, MainActivity.class);
            Bundle b = new Bundle();
            b.putString("username", getSharedPreferences("loginData",0).getString("username","s000000")); //Your id
            b.putString("password", getSharedPreferences("loginData", 0).getString("password", "non")); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
            finish();
            b.clear();
        }
    }
}
