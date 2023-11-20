package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
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

        userAdapter  = new UserListAdapter(this,friendRecs, user.getID());
        recsView = (ListView) findViewById(R.id.recommendations);
        recsView.setAdapter(userAdapter);

        setInterests(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Interests> userInterests) {
                generateRecommendations(userInterests);
            }
        });

        recsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
            public void AddOrRemoveFriend(View view) {
                LinearLayout parentRow = (LinearLayout) view.getParent();

                Button button = parentRow.findViewById(R.id.addOrRemoveFriend);
                TextView idView = parentRow.findViewById(R.id.hiddenId);
                if(button.getText().equals("Add Friend")){
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FriendFunction.addFriends(user.getID(), (String) idView.getText());
                            button.setText("Remove Friend");
                        }
                    });
                }
                else{
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FriendFunction.removeFriends(user.getID(), (String) idView.getText());
                            button.setText("Add Friend");
                        }
                    });
                }
            }
        });
    }

    private void setInterests (FirebaseCallback firebaseCallback) {
        ArrayList<Interests> interests = new ArrayList<>();

        for (Interests interest : Interests.values()) {
            String interestId = user.getID() + interest.toString();
            mDatabase.child("interests").child(interestId).addListenerForSingleValueEvent(new ValueEventListener() {
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
        void onCallback(ArrayList<Interests> userInterests);
    }

    /*
     *  Helper to generate recommendations
     */
    private void generateRecommendations(ArrayList<Interests> userInterests) {
        ArrayList<User> recommendedUsers = new ArrayList<>();
        System.out.println("Generate");
        mDatabase.child("interests").addListenerForSingleValueEvent(new ValueEventListener() {
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
                int i = 0;

                System.out.println("map" + potentialFriends.size());
                System.out.println("list" + sortRecomendations.size());

                userAdapter.clear();
                System.out.println(userAdapter.isEmpty());

                while (i < NUMBER_OF_RECS && i < sortRecomendations.size()) {
                    userAdapter.clear();
                    System.out.println(userAdapter.isEmpty());
                    String userId = sortRecomendations.get(i).getKey();
                    mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // "interest1" doesn't exists in the database
                                int age = Integer.parseInt(dataSnapshot.child("age").getValue(String.class));
                                String email = dataSnapshot.child("email").getValue(String.class);
                                String affiliation = dataSnapshot.child("userType").getValue(String.class);
                                String username = dataSnapshot.child("username").getValue(String.class);

                                User rec = makeUser(userId, age, email, affiliation, username);

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

                    i++;
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

    private User makeUser(String userId, int age, String email, String affiliation, String name) {
        User makeUser;

        if (affiliation.compareTo("Native Speaker") == 0) {
            makeUser = new NativeSpeaker(userId, email, name, age);
        }
        else {
            makeUser = new InternationalStudent(userId, email, name, age, "Spanish");
        }

        return makeUser;
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
