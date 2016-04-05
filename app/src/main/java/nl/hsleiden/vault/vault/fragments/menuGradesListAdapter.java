package nl.hsleiden.vault.vault.fragments;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nl.hsleiden.vault.vault.Database.Course;
import nl.hsleiden.vault.vault.R;

/**
 * Created by Perseus on 05-04-16.
 */
public class menuGradesListAdapter extends ArrayAdapter<Course> {

    public menuGradesListAdapter(Context context, int resource, List<Course> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null) {
            vh = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.menu_grades_row, parent, false);
            vh.name = (TextView) convertView.findViewById(R.id.subject_name);
            vh.grade = (TextView) convertView.findViewById(R.id.subject_grade);
//            vh.story = (TextView) convertView.findViewById(R.id.subject_content);
//            vh.code = (TextView) convertView.findViewById(R.id.subject_code);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Course cm = getItem(position);
        vh.name.setText(cm.getName());
        vh.grade.setText(cm.getGrade());
//        vh.story.setText(cm.getStory());
//        vh.code.setText(cm.blk_description);
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView grade;
    }
}