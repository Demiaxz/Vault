package nl.hsleiden.vault.vault.fetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Perseus on 17-03-16.
 */
public class JSONTest {
    public JSONTest() {

        JSONObject student1 = new JSONObject();
        try {
            student1.put("id", "3");
            student1.put("name", "NAME OF STUDENT");
            student1.put("year", "3rd");
            student1.put("curriculum", "Arts");
            student1.put("birthday", "5/5/1993");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject student2 = new JSONObject();
        try {
            student2.put("id", "2");
            student2.put("name", "NAME OF STUDENT2");
            student2.put("year", "4rd");
            student2.put("curriculum", "scicence");
            student2.put("birthday", "5/5/1993");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        JSONArray jsonArray = new JSONArray();

        jsonArray.put(student1);
        jsonArray.put(student2);

        JSONObject studentsObj = new JSONObject();
        try {
            studentsObj.put("Students", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String jsonStr = studentsObj.toString();

        System.out.println("jsonString: " + jsonStr);

    }
}
