package nl.hsleiden.vault.vault;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

//import nl.hsleiden.vault.vault.fetcher.HttpFetcher;

public class menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Welkomst bericht.
        if (getIntent().getExtras().getBoolean("loggedIn")){
            String message = "Welcome, "+getSharedPreferences("userData", 0).getString("voornaam","Geen naam gevonden.")+".";
            Snackbar sb = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
            sb.show();
            getIntent().getExtras().clear();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

//        //pasfoto in menu
//        Bitmap bitmap = null;
//        try {
//            bitmap = (Bitmap) HttpFetcher.fetchPhoto();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        //CODE VOOR INSTELLEN FOTO
//        ImageView mImg;
//        mImg = (ImageView) findViewById(R.id.profilePicture);
//        mImg.setImageBitmap(getCircleBitmap(bitmap));
//        mImg.invalidate();

        //code voor instellen van de gegevens
        // globally
        TextView myAwesomeTextView = (TextView)findViewById(R.id.voornaam);
        TextView myAwesomeTextView2 = (TextView)findViewById(R.id.emailadress);
        //in your OnCreate() method
        myAwesomeTextView.setText(getApplicationContext().getSharedPreferences("userData", 0).getString("voornaam", "Geen voornaam gevonden."));
        String email = getApplicationContext().getSharedPreferences("userData",0).getString("snr","Geen email gevonden.");
        if (email.length() < 11) {
            email = email + "@student.hsleiden.nl";
        }
        myAwesomeTextView2.setText(email);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == 1) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.logout) {
            logOut();
        }


        //Intent intent = getIntent();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    private void logOut(){
        getApplicationContext().getSharedPreferences("loginData",0).edit().clear().commit();
        Intent intent = new Intent(getApplicationContext(), start.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle intentLogin = new Bundle();
        intentLogin.putBoolean("logout", true);
        intent.putExtras(intentLogin); //Put your id to your next Intent
        startActivity(intent);
        finish();
    }
}
