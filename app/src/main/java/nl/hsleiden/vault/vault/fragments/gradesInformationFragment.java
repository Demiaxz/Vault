package nl.hsleiden.vault.vault.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.hsleiden.vault.vault.R;

/**
 * Created by Perseus on 04-04-16.
 */
public class gradesInformationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grades_information_fragment,container,false);
    }
}