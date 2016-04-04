package nl.hsleiden.vault.vault.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.hsleiden.vault.vault.R;

/**
 * Created by Perseus on 01-04-16.
 */
public class pieFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.piefragment_layout,container,false);
    }
}