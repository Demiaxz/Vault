package nl.hsleiden.vault.vault;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.nodes.Document;

import nl.hsleiden.vault.vault.fetcher.DataFetch;
import nl.hsleiden.vault.vault.fetcher.PicFetch;
import nl.hsleiden.vault.vault.fragments.gradesFragment;
import nl.hsleiden.vault.vault.fragments.testFragment;

//import nl.hsleiden.vault.vault.fetcher.HttpFetcher;

public class menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //pasfoto in menu
    Bitmap bitmap = null;
    Document goods = null;
    stashGoods k = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Vault - Dashboard");
        showDash();

        //Welkomst bericht.
        if (getIntent().getExtras().getBoolean("loggedIn")){
            String message = "Welcome, "+getSharedPreferences("userData", 0).getString("voornaam","Geen naam gevonden.")+".";
            Snackbar sb = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
            sb.show();
            getIntent().getExtras().clear();
        }

        try {
            //Fetch the download link for the picture and store this in a bitmap and pass it
            bitmap = (Bitmap) new PicFetch(getIntent().getExtras().getString("username","0"),getIntent().getExtras().getString("password","0"),getApplicationContext()).runAuth();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //create me a nested json with every grade you can fetch.
            k = new stashGoods(goods = new DataFetch(getIntent().getExtras().getString("username","0"),getIntent().getExtras().getString("password","0"),getApplicationContext()).runAuth());
            //Object k has getGradeList, wich is a list of all the grades stored, with key = coursename and value = another json object with multiple key's.
            //we need to create a listview that uses this information.
        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        showDash();
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

    public stashGoods getK() {
        return k;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        //CODE VOOR INSTELLEN FOTO
        ImageView mImg;
        mImg = (ImageView) findViewById(R.id.profilePicture);
        mImg.setImageBitmap(getCircleBitmap(bitmap));
        mImg.invalidate();
        mImg = null;
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
        if (id == R.id.logout) {
            logOut();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dashboard) {
            //replaceFragment();
            showDash();
        } else if (id == R.id.grades) {
            //grades();
            setTitle("Vault - Grades");
            gradesFragment kipje = new gradesFragment(getK());
            replaceFragment(kipje);
        }  else if (id == R.id.about) {
            //test();
            startActivity(new Intent(menu.this, piechart.class));
        } else if (id == R.id.logout) {
            logOut();
        }


        //Intent intent = getIntent();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = null;

        try {
            output = Bitmap.createBitmap(bitmap.getWidth(),
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


        }
        catch (NullPointerException ep){
            ep.printStackTrace();
        }
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

    private void grades(){
        Intent intent = new Intent(getApplicationContext(), grades.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        onStop();
    }

    private void test(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        testFragment fragment = new testFragment(getK());
        fragment.getView();
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    private void showDash(){
        setTitle("Vault - Dashboard");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        testFragment fragment = new testFragment(getK());
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
    private void replaceFragment (Fragment fragment){
        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(R.id.fragmentContainer, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }


}
