package nl.hsleiden.vault.vault.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import nl.hsleiden.vault.vault.Database.PairValue;
import nl.hsleiden.vault.vault.R;

/**
 * Created by Perseus on 09-04-16.
 */
public class changeAlerter  extends DialogFragment {
    private String toChange = null;
    private String oldInput = null;
    private ListView view = null;
    private PairValue pv = null;
    private menuInfoListAdapter blist = null;

    public changeAlerter(String settingName, String input,ListView v,PairValue person, menuInfoListAdapter dikzo){
        toChange = settingName;
        oldInput = input;
        view = v;
        pv = person;
        blist = dikzo;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.changesetting_layout, null);

        TextView tv1 = (TextView) (v.findViewById(R.id.input));
        TextView tv2 = (TextView) (v.findViewById(R.id.toChange));

        tv1.setHint(oldInput);
        tv2.setText(toChange);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Personalise")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TextView tv1 = (TextView) (v.findViewById(R.id.input));
                        if (tv1.getText().length() > 1) {
                            CharSequence bam = tv1.getText();
                            String kip = bam.toString();
                            v.getContext().getSharedPreferences("userData", 0).edit().putString(toChange, kip).commit();
                            pv.setValue(kip);
                            view.invalidate();
                            view.setAdapter(blist);
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
