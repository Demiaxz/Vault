package nl.hsleiden.vault.vault.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nl.hsleiden.vault.vault.Database.PairValue;
import nl.hsleiden.vault.vault.R;

/**
 * Created by Perseus on 05-04-16.
 */
public class menuInfoListAdapter extends ArrayAdapter<PairValue> {

    public menuInfoListAdapter(Context context, int resource, List<PairValue> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null) {
            vh = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.menu_info_row, parent, false);
            vh.key = (TextView) convertView.findViewById(R.id.info_title);
            vh.value = (TextView) convertView.findViewById(R.id.info_awnser);
//            vh.story = (TextView) convertView.findViewById(R.id.subject_content);
//            vh.code = (TextView) convertView.findViewById(R.id.subject_code);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        PairValue cm = getItem(position);
        vh.key.setText(cm.getKey());
        vh.value.setText(cm.getValue());
//        vh.story.setText(cm.getStory());
//        vh.code.setText(cm.blk_description);
        return convertView;
    }

    private static class ViewHolder {
        TextView key;
        TextView value;
    }
}