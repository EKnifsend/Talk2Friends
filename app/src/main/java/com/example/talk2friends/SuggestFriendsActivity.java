package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class SuggestFriendsActivity extends AppCompatActivity {
    User user;
    DatabaseReference mDatabase;

    final int NUMBER_OF_RECS = 7;
    UserListAdapter userAdapter;
    ListView recsView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_friends);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        user = (User) intent.getParcelableExtra("user");

        // Set up list of Friend recommendations
        ArrayList<User> friendRecs = new ArrayList<User>();

        userAdapter  = new UserListAdapter(this,friendRecs);
        recsView = (ListView) findViewById(R.id.recommendations);
        recsView.setAdapter(userAdapter);

        setInterests(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Interests> interestsToDisplay) {

            }
        });
    }

    private void setInterests (FirebaseCallback firebaseCallback) {
        ArrayList<Interests> interests = new ArrayList<>();

        for (Interests interest : Interests.values()) {
            String interestId = user.getID() + interest.toString();
            mDatabase.child("interests").child(interestId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // "interest1" does exists in the database
                        interests.add(dataSnapshot.child("interest").getValue(Interests.class));

                        firebaseCallback.onCallback(interests);
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
    }

    private interface FirebaseCallback {
        void onCallback(ArrayList<Interests> interestsToDisplay);
    }

    /*
     *  Helper to generate recommendations
     */
    private void generateRecommendations(ArrayList<Interests> userInterests) {
        ArrayList<User> recommendedUsers = new ArrayList<>();
        mDatabase.child("interests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Integer> potentialFriends = new HashMap<String, Integer>();

                recommendedUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Interests interest = dataSnapshot.child("interest").getValue(Interests.class);
                    String potentialFriend = dataSnapshot.child("userId").getValue(String.class);

                    if (userInterests.contains(interest) && potentialFriend.compareTo(user.getID()) != 0) {
                        if (potentialFriends.containsKey(potentialFriend)) {
                            int count = potentialFriends.get(potentialFriend);
                            potentialFriends.put(potentialFriend, count + 1);
                        }
                        else {
                            potentialFriends.put(potentialFriend, 1);
                        }
                    }
                }

                List<Map.Entry<String, Integer>> sortRecomendations = sortHashMapByValue(potentialFriends);

                userAdapter.clear();
                for (int i = 0; i < NUMBER_OF_RECS; i++) {
                     mDatabase.child("users").child(sortRecomendations.get(i).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // "interest1" doesn't exists in the database
                                User rec = dataSnapshot.getValue(User.class);
                                userAdapter.add(rec);
                                userAdapter.notifyDataSetChanged();
                            } else {
                                // "interest1" does not exist in the database
                                System.out.println("Entry 'interest1' does exist in the database.");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle database error
                            System.err.println("Error checking 'interest1' entry: " + databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private List<Map.Entry<String, Integer>> sortHashMapByValue(HashMap<String, Integer> hashMap) {
        // Convert the HashMap entries to a List
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(hashMap.entrySet());

        // Sort the List using a custom comparator
        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        return entryList;
    }

    /*
     *  OnClick function for @+id/back button
     */
    public void cancel(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
