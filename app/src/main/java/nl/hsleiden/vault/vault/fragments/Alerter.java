package nl.hsleiden.vault.vault.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import nl.hsleiden.vault.vault.Database.Course;
import nl.hsleiden.vault.vault.R;
import nl.hsleiden.vault.vault.stashGoods;

/**
 * Created by Perseus on 05-04-16.
 */

public class Alerter extends DialogFragment {
    private Course kip = null;
    private stashGoods goodList = null;

    public Alerter(Course kipp, stashGoods goods){
        kip = kipp;
        goodList = goods;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.alerter_layout, null);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(kip.getName())
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });
        builder.setView(v);

        try {
            JSONObject vakSpecifiekData = goodList.getGradeList().getJSONObject(kip.getName());

            TextView curcus = (TextView) v.findViewById(R.id.curcus);
            TextView testdatum = (TextView) v.findViewById(R.id.toetsdatum);
            TextView omschrijving = (TextView) v.findViewById(R.id.omschrijving);
            TextView toetstype = (TextView) v.findViewById(R.id.toetstype);
            TextView weging = (TextView) v.findViewById(R.id.weging);
            TextView resultaat = (TextView) v.findViewById(R.id.resultaat);
            TextView concept = (TextView) v.findViewById(R.id.concept);
            TextView mutatiedatum = (TextView) v.findViewById(R.id.mutatiedatum);
            //------//
            curcus.setText(vakSpecifiekData.getString("curcus").split(" ")[0]);
            testdatum.setText(vakSpecifiekData.getString("testdate"));
            omschrijving.setText(vakSpecifiekData.getString("omschrijving"));
            toetstype.setText(vakSpecifiekData.getString("toetstype"));
            weging.setText(vakSpecifiekData.getString("weging"));
            resultaat.setText(vakSpecifiekData.getString("resultaat"));
            concept.setText(vakSpecifiekData.getString("concept"));
            mutatiedatum.setText(vakSpecifiekData.getString("mutatiedatum"));
        }
        catch (Exception e){
            e.printStackTrace();
        }




        // Create the AlertDialog object and return it
        return builder.create();
    }
}
