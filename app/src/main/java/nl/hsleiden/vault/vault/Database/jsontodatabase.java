package nl.hsleiden.vault.vault.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

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
            String course_id       = rs.getString(rs.getColumnIndex("ID"));
            listedArray.add(course_id);
            emptyVideoArray.add(course_id);
            j++;
            rs.moveToNext();
        }

        rs.moveToFirst();
        /* END FETCHER FOR ALL ITEMS */

        // iterate via "for loop"
        for (int i = 0; i < subjects.size(); i++) {

            String ID       = String.valueOf(subjects.get(i).getID());


            int vid_id_int = Integer.parseInt(ID);

            String Cor_name  = String.valueOf(subjects.get(i).getName());
            String Cor_ects      = String.valueOf(subjects.get(i).getEcts());
            String Cor_grade     = String.valueOf(subjects.get(i).getGrade());
            String Cor_period     = String.valueOf(subjects.get(i).getPeriod());
            String Cor_testdate      = String.valueOf(subjects.get(i).getTestdate());
            String Cor_description      = String.valueOf(subjects.get(i).getDescription());
            String Cor_impact      = String.valueOf(subjects.get(i).getImpact());
            String Cor_concept      = String.valueOf(subjects.get(i).getConcept());
            String Cor_mutationdate      = String.valueOf(subjects.get(i).getMutationdate());
            String Cor_core      = String.valueOf(subjects.get(i).getCore());




                // Set values to insert into the database
            ContentValues values = new ContentValues();
            values.put(DatabaseInfo.CourseColumn.ID, ID);
            values.put(DatabaseInfo.CourseColumn.NAME, Cor_name);
            values.put(DatabaseInfo.CourseColumn.ECTS, Cor_ects);
            values.put(DatabaseInfo.CourseColumn.GRADE, Cor_grade);
            values.put(DatabaseInfo.CourseColumn.PERIOD, Cor_period);
            values.put(DatabaseInfo.CourseColumn.TESTDATE, Cor_testdate);
            values.put(DatabaseInfo.CourseColumn.DESCRIPTION, Cor_description);
            values.put(DatabaseInfo.CourseColumn.IMPACT, Cor_impact);
            values.put(DatabaseInfo.CourseColumn.CONCEPT, Cor_concept);
            values.put(DatabaseInfo.CourseColumn.MUTATIONDATE, Cor_mutationdate);
            values.put(DatabaseInfo.CourseColumn.CORE, Cor_core);

            // The insert itself by the dbhelper
            dbHelper.insert(DatabaseInfo.VideoTables.VIDEO, null, values);

            // Add the id to the array so we won't add it twice
            listedArray.add(vid_id);
            Log.d("MULAMETHOD-VIDEO", listedArray.toString());

        }
        Log.d("MULAMETHOD-VIDEO", listedArray.toString());
        Log.d("MULAMETHOD-VIDEOEMPTY", emptyVideoArray.toString());
        // your code
        for (String object: emptyVideoArray) {
            dbHelper.deleteItem(DatabaseInfo.VideoTables.VIDEO, DatabaseInfo.VideoColumn.VID_ID, object);
            emptyVideoArray.remove(object);
        }
        Log.d("MULAMETHOD-VIDEOEMPTY", emptyVideoArray.toString());

    }

    private void processRequestVideoError(VolleyError error){
        Log.d("MULAMETHOD", "Video JSON request failed. Internet Connection Aviable? Webserver Aviable?");
    }
}
