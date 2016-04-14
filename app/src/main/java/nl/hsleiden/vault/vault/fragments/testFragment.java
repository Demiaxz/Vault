package nl.hsleiden.vault.vault.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nl.hsleiden.vault.vault.Database.Course;
import nl.hsleiden.vault.vault.Database.PairValue;
import nl.hsleiden.vault.vault.R;
import nl.hsleiden.vault.vault.stashGoods;

/**
 * Created by Perseus on 30-03-16.
 */
public class testFragment extends Fragment {
    private PieChart mChart;
    public static final int MAX_ECTS = 60;
    public static int currentEcts = 0;

    private ListView mListView;
    private menuGradesListAdapter mAdapter;

    private ListView bListView;
    private menuInfoListAdapter bAdapter;

    private List<Course> courseModels = new ArrayList<>();    // NEED A METHOD TO FILL THIS. RETRIEVE THE DATA FROM JSON
    private List<PairValue> personModels = new ArrayList<>();    // NEED A METHOD TO FILL THIS. RETRIEVE THE DATA FROM JSON

    private stashGoods goods = null;
    private boolean dildo = false;

    public testFragment(stashGoods k){
        setGoods(k);
    }

    public static int getCurrentEcts() {
        return currentEcts;
    }

    public static void setCurrentEcts(int currentEcts) {
        testFragment.currentEcts = currentEcts;
    }

    public stashGoods getGoods() {
        return goods;
    }

    public void setGoods(stashGoods goods) {
        this.goods = goods;
    }

    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (RelativeLayout) inflater.inflate(R.layout.test_fragment_layout, container, false);
        mChart = (PieChart) view.findViewById(R.id.chart);
        mChart.setDescription("");
        mChart.setTouchEnabled(false);
        mChart.setDrawSliceText(true);
        mChart.getLegend().setEnabled(false);
        mChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        super.onCreate(savedInstanceState);

        // get Arguments
        Bundle bundle = getArguments();

        // Get the Listview by ID
        mListView = (ListView) view.findViewById(R.id.my_list_view);

        bListView = (ListView) view.findViewById(R.id.menuPersonalListView);

        // Get the amount of return
        //JSONArray array = goods.getGradeDetails().names();

        // For all the items we get in the return
        for (int i = 0; i < 3; i++) {
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

                courseModels.add(new Course(curcus, "3", resultaat , testdate));

            }

            catch(Exception e){
//                e.printStackTrace();
            }
        }
        //for all the information stored about the user
        //name
        //class NOT
        //mentor NOT
        //period NOT
        //TODO: itemModels.add(key,value);
        // - - - - - - - - - - - - - - - - - - - - - - - - //
        // TODO: Maak een instantie van de adviesklasse en laat die in de shareprefference "Advice" vullen met het advies.
        // Advice x = new Advice(getActivity() );

        getActivity().getSharedPreferences("userData", 0).edit().putString("EC", String.valueOf(getGoods().getPoints())).commit();

        String name = getActivity().getSharedPreferences("userData", 0).getString("voornaam","Edit me!");
        String classs = getActivity().getSharedPreferences("userData", 0).getString("Class","Edit me!");
        String period = getActivity().getSharedPreferences("userData", 0).getString("Period","Edit me!");

        String advice = getActivity().getSharedPreferences("userData", 0).getString("Advice","Unable to give advice.");

        personModels.add(new PairValue("Name",name));
        personModels.add(new PairValue("Class",getActivity().getSharedPreferences("userData", 0).getString("Class","Edit me!")));
        personModels.add(new PairValue("Period",getActivity().getSharedPreferences("userData", 0).getString("Period","" +
                "Comming soon.")));
        personModels.add(new PairValue("Advice", advice));

        // Now give the listview( filled) back
        mAdapter = new menuGradesListAdapter(getActivity(), 0, courseModels);
        mListView.setAdapter(mAdapter);


        bAdapter = new menuInfoListAdapter(getActivity(),0,personModels);
        bListView.setAdapter(bAdapter);


        // Create the onclicklistener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Course selectedNewsitem = courseModels.get(position);
                new Alerter(selectedNewsitem, goods).show(getFragmentManager(), "Info");
            }
        });

        bListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                PairValue selectedNewsitem = personModels.get(position);

                if(selectedNewsitem.getKey() != "Advice") {
                    if (selectedNewsitem.getKey() != "Period") {
                        String settings = selectedNewsitem.getKey();
                        String input = selectedNewsitem.getValue();
                        new changeAlerter(settings, input, bListView, selectedNewsitem, bAdapter).show(getFragmentManager(), "Change");
                        bAdapter = new menuInfoListAdapter(getActivity(), 0, personModels);
                        bListView.setAdapter(bAdapter);
                    }
                    else{
                        //TODO: Find out the damn period.
                    }
                }
                else{
                    //TODO: Why this advice?
                }


            }
        });

        // Execute some code after 2 seconds have passed
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {
                setData(getGoods());
            }
        }, 500);

        return view;
    }

    public void setData(stashGoods k) {
        currentEcts = Integer.valueOf(getActivity().getSharedPreferences("userData", 0).getString("EC","0"));
        int aantal = k.getPoints();
        System.out.println(aantal);
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();

        yValues.add(new Entry(aantal, 0));
        xValues.add("Behaalde ECTS");

        yValues.add(new Entry(60 - currentEcts, 1));
        xValues.add("Resterende ECTS");

        //  http://www.materialui.co/colors
        ArrayList<Integer> colors = new ArrayList<>();
        if (currentEcts <10) {
            colors.add(Color.rgb(255,21,19));
        } else if (currentEcts < 40){
            colors.add(Color.rgb(205,21,19));
        } else if  (currentEcts < 50) {
            colors.add(Color.rgb(155,21,19));
        } else {
            colors.add(Color.rgb(67,21,19));
        }

        colors.add(Color.rgb(255, 0, 0));


        PieDataSet dataSet = new PieDataSet(yValues, "ECTS");
        //  dataSet.setDrawValues(false); //schrijf ook de getallen weg.
        dataSet.setColors(colors);

        PieData data = new PieData(xValues, dataSet);
        mChart.setData(data);        // bind je dataset aan de chart.
        mChart.invalidate();        // Aanroepen van een volledige redraw
        Log.d("aantal =", "" + currentEcts);

    }
}

