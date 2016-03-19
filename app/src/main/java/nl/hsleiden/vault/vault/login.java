//package nl.hsleiden.vault.vault;
//
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.View;
//
//import nl.hsleiden.vault.vault.fetcher.HttpFetcher;
//
//public class login extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Connecting..", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        String username = "";
//        String password = "";
//
//        try {
//            HttpFetcher loginActivity = new HttpFetcher(username, password);
//            Log.d("processRequestError", "Natty.");
//        } catch (Exception e) {
//            Log.d("processRequestError", "unNatty.");
//            e.printStackTrace();
//        }
//    }
//
//}
