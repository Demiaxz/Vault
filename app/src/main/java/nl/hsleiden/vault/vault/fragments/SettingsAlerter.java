package nl.hsleiden.vault.vault.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import nl.hsleiden.vault.vault.Database.PairValue;
import nl.hsleiden.vault.vault.R;

/**
 * Created by Perseus on 06-04-16.
 */

public class SettingsAlerter extends DialogFragment {
    private String toChange = null;
    private String oldInput = null;
    private ListView view = null;
    private PairValue pv = null;
    private menuInfoListAdapter blist = null;

    public SettingsAlerter(){
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.content_settings_alerter, null);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Settings")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        CheckBox tv1 = (CheckBox) (v.findViewById(R.id.checkBox));
                        CheckBox tv2 = (CheckBox) (v.findViewById(R.id.checkBox2));

                        if (tv1.isChecked()) {
                            //only sync no push
                            v.getContext().getSharedPreferences("userData", 0).edit().putBoolean("update", true).commit();
                            if (tv2.isChecked()) {
                                //also push
                                v.getContext().getSharedPreferences("userData", 0).edit().putBoolean("push", true).commit();
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });

        builder.setView(v);

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
