package nl.hsleiden.vault.vault;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                requestSubjects();
            }
        });

        Button cijfers = (Button) findViewById(R.id.cijfers);
        cijfers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Cijfers knop ingedrukt.", "Start CourseListActivity");
                startActivity(new Intent(start.this, nl.hsleiden.vault.vault.List.CourseListActivity.class));
            }
        });


    }
    // ALLES WAT JE NODGI HEBT OM EEN REQUEST TE MAKEN
    private void requestSubjects(){
        Type type = new TypeToken<List<Course>>(){}.getType();
        GsonRequest<List<Course>> request = new GsonRequest<List<Course>>(
                "http://fuujokan.nl/subject_lijst.json", type, null,
                new Response.Listener<List<Course>>() {
                    @Override
                    public void onResponse(List<Course> response) { processRequestSucces(response); }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){ processRequestError(error);     }
        }
        );
        VolleyHelper.getInstance(this).addToRequestQueue(request);
    }

    private void processRequestSucces(List<Course> subjects ){
        Log.d("processRequestSucces","Natty.");
    }

    private void processRequestError(VolleyError error){
        Log.d("processRequestError", "unNatty.");
    }


}

