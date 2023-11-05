package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    User user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        user = (User) intent.getParcelableExtra("user");

        // Make Profile information
        TextView username = (TextView) findViewById(R.id.username);
        username.setText(user.getName());

        TextView age = (TextView) findViewById(R.id.age);
        age.setText(Integer.toString(user.getAge()));

        TextView association = (TextView) findViewById(R.id.association);
        if ((user.affiliation).compareTo("International Student") == 0) {
            association.setText(getString(R.string.international));
        }
        else {
            association.setText(getString(R.string.domestic));
        }

        TextView email = (TextView) findViewById(R.id.email);
        email.setText(user.getEmail());

        // Set up list of interests
        ArrayList<Interests> interests = new ArrayList<Interests>();

        for (Interests interest : Interests.values()) {
            interests.add(interest);
        }

        InterestAdapter interestAdapter = new InterestAdapter(this,interests);
        ListView interestsView = (ListView) findViewById(R.id.interests);
        interestsView.setAdapter(interestAdapter);

        interestsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Call meeting page with meeting info from index i
            }
        });
    }

    public void exit(View view) {

    }

    public void addFriend(View view){
    }

    public void removeFriend(View view) {
    }
}
