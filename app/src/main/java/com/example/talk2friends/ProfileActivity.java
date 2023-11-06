package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    User user;

    User subject;

    boolean areFriends;

    Button addOrRemoveFriend;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        user = (User) intent.getParcelableExtra("user");

        subject = (User) intent.getParcelableExtra("subject");

        areFriends = FriendFunction.areFriends(user.getID(), subject.getID());

        // Make Profile information
        TextView username = (TextView) findViewById(R.id.username);
        username.setText(subject.getName());

        TextView age = (TextView) findViewById(R.id.age);
        age.setText(Integer.toString(subject.getAge()));

        TextView association = (TextView) findViewById(R.id.association);
        if ((subject.affiliation).compareTo("International Student") == 0) {
            association.setText(getString(R.string.international));
        }
        else {
            association.setText(getString(R.string.domestic));
        }

        TextView email = (TextView) findViewById(R.id.email);
        email.setText(subject.getEmail());

        // Set up list of interests
        ArrayList<Interests> subjectInterests = subject.getInterests();

        InterestAdapter interestAdapter = new InterestAdapter(this,subjectInterests);
        ListView interestsView = (ListView) findViewById(R.id.interests);
        interestsView.setAdapter(interestAdapter);

        interestsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Call meeting page with meeting info from index i
            }
        });

        addOrRemoveFriend = (Button) findViewById(R.id.addOrRemoveFriend);
    }

    private void setAddOrRemoveFriend() {
        if (areFriends) {
            addOrRemoveFriend.setText("Remove");
        }
        else {
            addOrRemoveFriend.setText("Add");
        }
    }

    public void exit(View view) {

    }

    public void addOrRemoveFriend() {
        if (areFriends) {
            FriendFunction.addFriends(user.getID(), subject.getID());
            areFriends = false;
        }
        else {
            FriendFunction.removeFriends(user.getID(), subject.getID());
            areFriends = true;
        }

        setAddOrRemoveFriend();
    }
}
