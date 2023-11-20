package com.example.talk2friends;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InterestAdapter extends ArrayAdapter<Interests> {
    private static final String LOG_TAG = MeetingAdapter.class.getSimpleName();
    private ArrayList<Interests> usedInterests;
    private ArrayList<Interests> selectedInterests;

    public InterestAdapter(Activity context, ArrayList<Interests> interests) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, interests);

        this.usedInterests = interests;
        this.selectedInterests = new ArrayList<>();
    }

    public InterestAdapter(Activity context, ArrayList<Interests> interests, ArrayList<Interests> selectedInterests) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, interests);

        this.usedInterests = interests;
        this.selectedInterests = selectedInterests;
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

        if (selectedInterests.contains(currentInterest)) {
            listItemView.setBackgroundColor(Color.parseColor("#b39bd8"));
        }
        else {
            listItemView.setBackgroundColor(Color.WHITE);
        }

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.interestName);
        // Get the version name from the current Dessert object and
        // set this text on the name TextView
        nameTextView.setText(currentInterest.toStringPretty());

        return listItemView;
    }

    public void updateSeletected(ArrayList<Interests> selectedInterests) {
        this.selectedInterests = selectedInterests;
    }

    public Interests getValueAtIndex(int i) {
        if (i < usedInterests.size()) {
            return usedInterests.get(i);
        }
        else {
            return null;
        }
    }
}
