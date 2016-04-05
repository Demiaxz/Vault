package nl.hsleiden.vault.vault.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nl.hsleiden.vault.vault.Database.PairValue;
import nl.hsleiden.vault.vault.R;

/**
 * Created by Perseus on 04-04-16.
 */
public class gradesInformationFragment extends Fragment {
    private ListView mListView;
    private menuInfoListAdapter mAdapter;
    private List<PairValue> personModels = new ArrayList<>();    // NEED A METHOD TO FILL THIS. RETRIEVE THE DATA FROM JSON

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grades_information_fragment,container,false);
        return view;
    }
}