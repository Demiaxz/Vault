package nl.hsleiden.vault.vault;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nl.hsleiden.vault.vault.Database.Course;
import nl.hsleiden.vault.vault.Database.GsonRequest;
import nl.hsleiden.vault.vault.Database.VolleyHelper;
import nl.hsleiden.vault.vault.List.CourseListAdapter;

public class resultaatActivity extends AppCompatActivity {
    private ListView mListView;
    private CourseListAdapter mAdapter;
    private List<Course> courseModels = new ArrayList<>();

    public void requestSubjects(Context kaas) {
        Type type = new TypeToken<List<Course>>() {
        }.getType();
        GsonRequest<List<Course>> request = new GsonRequest<List<Course>>(
                "http://fuujokan.nl/subject_lijst.json", type, null,
                new Response.Listener<List<Course>>() {
                    @Override
                    public void onResponse(List<Course> response) {
                        processRequestSucces(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                processRequestError(error);
            }
        }
        );
        VolleyHelper.getInstance(kaas).addToRequestQueue(request);
    }

    private void processRequestSucces(List<Course> subjects) {
        Log.d("processRequestSucces", "Natty.");

        //voor elke course in een aangereikte string doe ff rollen dan
        for (Course e : subjects) {
            Log.d("x:", String.valueOf(e));
        }

    }

    private void processRequestError(VolleyError error) {
        Log.d("processRequestError", "unNatty.");
    }
}
