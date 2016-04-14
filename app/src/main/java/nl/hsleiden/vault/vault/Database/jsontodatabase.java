package nl.hsleiden.vault.vault.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kay on 14-4-2016.
 */
public class jsontodatabase {
    private Context c;
    public jsontodatabase(Context context){
        c = context;
        requestVideoSubjects();
    }
    public Context getContext(){
        return c;
    }

    // ALLES WAT JE NODGI HEBT OM EEN REQUEST TE MAKEN
    private void requestVideoSubjects(){

        Type type = new TypeToken<List<Course>>(){}.getType();
        GsonRequest<List<Course>> request = new GsonRequest<List<Course>>(
                "http://www.fuujokan.nl/subject_lijst.json", type, null,
                new Response.Listener<List<Course>>() {
                    @Override
                    public void onResponse(List<Course> response) {
                        processRequestSucces(response);
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){ processRequestVideoError(error);     }
        }
        );

        VolleyHelper.getInstance(getContext()).addToRequestQueue(request);
    }

    private void processRequestSucces(List<Course> subjects ){

        // First, get the already existing if so
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(getContext());

        // Set a list array for checking wether we added this video id already or not
        List<String> listedArray = new ArrayList<String>();
        List<String> emptyVideoArray = new ArrayList<String>();

        /* FETCHER FOR ALL ITEMS */
        // Set the cursor (items fetcher)
        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE, new String[]{"*"}, null, null, null, null, null);

        // Get the amount of return
        String array[] = new String[rs.getCount()];
        int j = 0;

        rs.moveToFirst();

        // For all the items we get in the return
        while (!rs.isAfterLast()) {
            String course_name      = rs.getString(rs.getColumnIndex("name"));
            listedArray.add(course_name);
            emptyVideoArray.add(course_name);
            j++;
            rs.moveToNext();
        }

        rs.moveToFirst();
        /* END FETCHER FOR ALL ITEMS */

        // iterate via "for loop"
        for (int i = 0; i < subjects.size(); i++) {

            String Cor_name = String.valueOf(subjects.get(i).getName());
            String Cor_ects = String.valueOf(subjects.get(i).getEcts());
            String Cor_per = String.valueOf(subjects.get(i).getPeriod());

            if (listedArray.contains(Cor_name)) {
                System.out.println(Cor_name+" Allready in DB.");
            } else {

                // Set values to insert into the database
                ContentValues values = new ContentValues();

                values.put(DatabaseInfo.CourseColumn.NAME, Cor_name);
                values.put(DatabaseInfo.CourseColumn.ECTS, Cor_ects);
                values.put(DatabaseInfo.CourseColumn.PERIOD, Cor_per);

                // The insert itself by the dbhelper
                dbHelper.insert(DatabaseInfo.CourseTables.COURSE, null, values);

                // Add the id to the array so we won't add it twice
                listedArray.add(Cor_name);
                Log.d("Database", listedArray.toString());
            }
        }

    }

    private void processRequestVideoError(VolleyError error){
        Log.d("JSON", "FAILED TO FETCH");
    }

    //dit is een mooi begin voor het normaal laten werken van hoe het hoort te werken, maar het werkt niet, en het gaatn iet werken, zonder een revise van de hele app. Dit , we houden het er lekker bij. Laterrrr.
    public void addJsonToDatabase(JSONObject gradeList, ArrayList<String> nameList){
        for (String name : nameList){
            try {
                ContentValues values = new ContentValues();
                values.put(DatabaseInfo.CourseColumn.NAME, (String) gradeList.getJSONObject(name).get(name.split(" ")[0]));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
