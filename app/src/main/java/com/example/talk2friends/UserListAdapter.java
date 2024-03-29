package com.example.talk2friends;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * {@link DessertAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link Dessert} objects.
 * */
public class UserListAdapter extends ArrayAdapter<User> {

    private static final String LOG_TAG = UserListAdapter.class.getSimpleName();
    private String userid;

    public UserListAdapter(Activity context, ArrayList<User> users, String id) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, users);
        userid = id;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        User currentUser = getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name);
        nameTextView.setText(currentUser.getName());
        TextView idView = (TextView) listItemView.findViewById(R.id.hiddenId);
        idView.setText(String.valueOf(currentUser.ID));

        Button button = listItemView.findViewById(R.id.addOrRemoveFriend);
        button.setVisibility(View.INVISIBLE);
        if(currentUser.ID.equals(userid)){
            button.setVisibility(View.INVISIBLE);
        }


        /* LOOK AT
        if(FriendsFragment.AreFriends(MainActivity.user.ID, currentUser.ID)){
            button.setText("Remove Friend");
        }
        else{
            button.setText("Add Friend");
        }
        *
         */

        return listItemView;
    }
}
