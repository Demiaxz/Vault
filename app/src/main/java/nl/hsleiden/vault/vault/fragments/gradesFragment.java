package nl.hsleiden.vault.vault.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nl.hsleiden.vault.vault.Database.Course;
import nl.hsleiden.vault.vault.Database.PairValue;
import nl.hsleiden.vault.vault.R;
import nl.hsleiden.vault.vault.stashGoods;


/**
 * Created by Perseus on 03-04-16.
 */
public class gradesFragment extends Fragment {

    private ListView mListView;
    private gradesFragmentListAdapter mAdapter;


    private List<Course> courseModels = new ArrayList<>();    // NEED A METHOD TO FILL THIS. RETRIEVE THE DATA FROM JSON

    private stashGoods goods = null;

    public gradesFragment(stashGoods k){
        goods = k;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.grades_fragment_layout, container, false);

        super.onCreate(savedInstanceState);

        // get Arguments
        Bundle bundle = getArguments();

        // Get the Listview by ID
        mListView = (ListView) fragmentView.findViewById(R.id.my_list_view);



        // For all the items we get in the return
        for (int i = 0; i < goods.getNameList().size(); i++) {
            try {
                JSONObject element = goods.getGradeList().getJSONObject(goods.getNameList().get(i).toString());

                String testdate = element.get("testdate").toString();
                String curcus = element.get("curcus").toString();
                String omschrijving = element.get("curcus").toString();
                String toetstype = element.get("toetstype").toString();
                String weging = element.get("weging").toString();
                String resultaat = element.get("resultaat").toString();
                String concept = element.get("concept").toString();
                String mutatiedatum = element.get("mutatiedatum").toString();
                String story = element.get("verhaal").toString();

                courseModels.add(new Course(curcus, "3", resultaat , testdate, story));

            }

            catch(Exception e){
                e.printStackTrace();
            }
        }





//            // Add to the listview
//            if(nwsIDS == "" || nwsIDS.contains("[" + nws_id + "]")){


//            }


        // Now give the listview( filled) back
        mAdapter = new gradesFragmentListAdapter(getActivity(), 0, courseModels);
        mListView.setAdapter(mAdapter);

        // Create the onclicklistener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

             Course selectedNewsitem = courseModels.get(position);

//                                                 Intent intent = new Intent(getActivity(), gradesInformationFragment.class);
//                                                 intent.putExtra("curcus",selectedNewsitem.getName());
//                                                 startActivity(intent);

             FragmentManager fragmentManager = getFragmentManager();
             FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
             gradesInformationFragment fragment = new gradesInformationFragment();
             fragment.getView();
             fragmentTransaction.add(R.id.fragmentContainer, fragment);
             fragmentTransaction.commit();
         }
     }
        );
        return fragmentView;
    }
}