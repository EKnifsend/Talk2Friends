package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    User user;

    User subject;

    boolean areFriends;

    Button addOrRemoveFriend;
    DatabaseReference mDatabase;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        user = (User) intent.getParcelableExtra("user");

        subject = (User) intent.getParcelableExtra("subject");

        areFriends = FriendFunction.areFriends(user.getID(), subject.getID());

        mDatabase = FirebaseDatabase.getInstance().getReference();

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
        ArrayList<Interests> subjectInterests = new ArrayList<>();

        InterestAdapter interestAdapter = new InterestAdapter(this,subjectInterests);
        ListView interestsView = (ListView) findViewById(R.id.interests);
        interestsView.setAdapter(interestAdapter);

        /*
        interestsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
         */

        // Set up list of interests

        for (Interests interest : Interests.values()) {String interestId = user.getID() + interest.toString();
            mDatabase.child("interests").child(interestId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // "interest1" does exists in the database
                        subjectInterests.add(interest);
                        interestAdapter.clear();
                        interestAdapter.addAll(subjectInterests);
                        interestAdapter.notifyDataSetChanged();
                    } else {
                        // "interest1" doesn't not exist in the database
                        //System.out.println("Entry " + interestId + "doesn't exist in the database.");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database error
                    System.err.println("Error checking 'interest1' entry: " + databaseError.getMessage());
                }
            });
        }

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

    public void addOrRemoveFriend(View view) {
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
