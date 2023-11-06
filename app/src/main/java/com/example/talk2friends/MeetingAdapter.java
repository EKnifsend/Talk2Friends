package com.example.talk2friends;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


import java.sql.Array;

public class MeetingAdapter extends ArrayAdapter<MeetingInfo> {
    private static final String LOG_TAG = MeetingAdapter.class.getSimpleName();


    public MeetingAdapter(Activity context, ArrayList<MeetingInfo> meetings) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, meetings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.meeting_list_item, parent, false);
        }

        // Get the {@link Dessert} object located at this position in the list
        MeetingInfo currentMeeting = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.meeting_name);
        // Get the version name from the current Dessert object and
        // set this text on the name TextView
        nameTextView.setText(currentMeeting.getName());

        /*
        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.dessert_number);
        // Get the version number from the current Dessert object and
        // set this text on the number TextView
        numberTextView.setText(String.valueOf(currentMeeting.getDessertNumber()));

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);
        // Get the image resource ID from the current Dessert object and
        // set the image to iconView
        iconView.setImageResource(currentDesert.getImageResourceId());
        */

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
