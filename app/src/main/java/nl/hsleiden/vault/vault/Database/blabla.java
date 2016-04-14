package nl.hsleiden.vault.vault.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Perseus on 14-04-16.
 */
public class blabla {
}

    // ALLES WAT JE NODGI HEBT OM EEN REQUEST TE MAKEN
    private void requestVideoSubjects(){

        Type type = new TypeToken<List<Video>>(){}.getType();
        GsonRequest<List<Video>> request = new GsonRequest<List<Video>>(
                "http://prosportsrecovery.com/webserver/videos/get/", type, null,
                new Response.Listener<List<Video>>() {
                    @Override
                    public void onResponse(List<Video> response) {
                        processRequestVideoSucces(response);
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){ processRequestVideoError(error);     }
        }
        );

        VolleyHelper.getInstance(this).addToRequestQueue(request);
    }

    private void processRequestVideoSucces(List<Video> subjects ){

        // First, get the already existing if so
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);

        // Set a list array for checking wether we added this video id already or not
        List<String> listedArray = new ArrayList<String>();
        List<String> emptyVideoArray = new ArrayList<String>();

        /* FETCHER FOR ALL ITEMS */
        // Set the cursor (items fetcher)
        Cursor rs = dbHelper.query(DatabaseInfo.VideoTables.VIDEO, new String[]{"*"}, null, null, null, null, null);

        // Get the amount of return
        String array[] = new String[rs.getCount()];
        int j = 0;

        rs.moveToFirst();

        // For all the items we get in the return
        while (!rs.isAfterLast()) {
            String vid_id       = rs.getString(rs.getColumnIndex("vid_id"));
            listedArray.add(vid_id);
            emptyVideoArray.add(vid_id);
            j++;
            rs.moveToNext();
        }

        rs.moveToFirst();
        /* END FETCHER FOR ALL ITEMS */

        // iterate via "for loop"
        for (int i = 0; i < subjects.size(); i++) {

            String vid_id       = String.valueOf(subjects.get(i).getVid_id());

            // blk to intasdasdasdasd
            int vid_id_int = Integer.parseInt(vid_id);

            String vid_title    = String.valueOf(subjects.get(i).getVid_title());
            String vid_url      = String.valueOf(subjects.get(i).getVid_url());
            String vid_desc     = String.valueOf(subjects.get(i).getVid_description());
            String vid_crea     = String.valueOf(subjects.get(i).getVid_created());
            String vid_mod      = String.valueOf(subjects.get(i).getVid_modified());
            String vid_del      = String.valueOf(subjects.get(i).getVid_deleted());
            String vid_tag      = String.valueOf(subjects.get(i).getVid_tag());
            String vid_thm      = String.valueOf(subjects.get(i).getVid_thumbnail());

            // Now check if this id already exists in the array
            if (listedArray.contains(vid_id)) {
                emptyVideoArray.remove(vid_id);

                // Update always
                dbHelper.updateVideoItem(DatabaseInfo.VideoTables.VIDEO, vid_id_int, vid_title, vid_del);

            } else {

                // Set values to insert into the database
                ContentValues values = new ContentValues();
                values.put(DatabaseInfo.VideoColumn.VID_ID, vid_id);
                values.put(DatabaseInfo.VideoColumn.VID_TITLE, vid_title);
                values.put(DatabaseInfo.VideoColumn.VID_URL, vid_url);
                values.put(DatabaseInfo.VideoColumn.VID_DESCRIPTION, vid_desc);
                values.put(DatabaseInfo.VideoColumn.VID_CREATED, vid_crea);
                values.put(DatabaseInfo.VideoColumn.VID_MODIFIED, vid_mod);
                values.put(DatabaseInfo.VideoColumn.VID_DELETED, vid_del);
                values.put(DatabaseInfo.VideoColumn.VID_TAG, vid_tag);
                values.put(DatabaseInfo.VideoColumn.VID_THUMBNAIL, vid_thm);

                // The insert itself by the dbhelper
                dbHelper.insert(DatabaseInfo.VideoTables.VIDEO, null, values);

                // Add the id to the array so we won't add it twice
                listedArray.add(vid_id);
                Log.d("MULAMETHOD-VIDEO", listedArray.toString());
            }
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