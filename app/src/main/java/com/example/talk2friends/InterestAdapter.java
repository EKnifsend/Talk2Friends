package com.example.talk2friends;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class InterestAdapter extends ArrayAdapter<Interests> {
    private static final String LOG_TAG = MeetingAdapter.class.getSimpleName();

    public InterestAdapter(Activity context, ArrayList<Interests> interests) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, interests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.interest_list_item, parent, false);
        }

        // Get the {@link Dessert} object located at this position in the list
        Interests currentInterest = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.interestName);
        // Get the version name from the current Dessert object and
        // set this text on the name TextView
        nameTextView.setText(currentInterest.toString());

        return listItemView;
    }
}
