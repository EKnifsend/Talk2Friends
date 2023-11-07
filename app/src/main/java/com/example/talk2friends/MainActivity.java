package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ListView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    User user;  // The current user
    DatabaseReference mDatabase;
    ArrayList<MeetingInfo> meetings;

    ArrayList<String> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        friends = new ArrayList<String>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        user = buildUser(intent);
        meetings = new ArrayList<MeetingInfo>();

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

        mDatabase.child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(dataSnapshot.child("friend1Id").getValue().toString().equals(user.ID)){
                        if(dataSnapshot.child("friend2Id").getValue() != null) {
                            friends.add(dataSnapshot.child("friend2Id").getValue().toString());
                        }
                    }
                    else if(dataSnapshot.child("friend2Id").getValue().toString().equals(user.ID)){
                        friends.add(dataSnapshot.child("friend1Id").getValue().toString());
                    }
                }
                TextView friendCount = (TextView) findViewById(R.id.friendCount);
                String friendCountText = FriendFunction.countFriends(user.getID()) + "\nFriends";
                friendCount.setText(String.valueOf(friends.size()));
                FriendFunction.setFriends(friends);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        // Write user interests
        setInterests(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Interests> interestsToDisplay) {
                writeInterests(interestsToDisplay);
            }
        });

        // Set up list of meetings
        buildMeetings();

        // FriendFunction.addFriends("UyiFGNfDm5WpCZoGB0y70951keX2", "hjwlnl8b3BV5AZhbD1wA0HPdnSw2");
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

    private void writeInterests (ArrayList<Interests> interests) {
        String interestDisplay = "Interests: ";
        for (Interests interest: interests) {
            if (interest != null) {
                interestDisplay += interest.toStringPretty() + ", ";
            }
        }
        interestDisplay = interestDisplay.substring(0, interestDisplay.length()-2);

        TextView interestView = (TextView) findViewById(R.id.interests);
        interestView.setText(interestDisplay);
    }

    /*
     * Private helper to establish list of meetings
     */
    private void buildMeetings () {
        MeetingAdapter meetingAdapter = new MeetingAdapter(this,meetings);
        ListView meetingsView = (ListView) findViewById(R.id.meetings);
        meetingsView.setAdapter(meetingAdapter);

        mDatabase.child("meetings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                meetings.clear();
                meetingAdapter.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MeetingInfo meeting = dataSnapshot.getValue(MeetingInfo.class);
                    meetings.add(meeting);
                }
                //meetingAdapter.addAll(meetings);
                meetingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        meetingsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Call meeting page with meeting info from index i
                Intent intent = new Intent(MainActivity.this, MeetingActivity.class);
                //intent.putExtra("message", message); maybe user id
                intent.putExtra("user", user);
                intent.putExtra("meetingInfo", meetings.get(i));

                //Bundle bundle = new Bundle();
                //bundle.putParcelable("user", user);
                //bundle.putParcelable("meetingInfo", meetings.get(i));
                //intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    /*
     * Use Intent to set user correctly
     */
    private User buildUser(Intent intent) {
        if (intent.hasExtra("user")) { // User exists in app
            return ((User) intent.getParcelableExtra("user"));
        }
        else {
            User makeUser = new NativeSpeaker("1", "mike@usc.edu", "mike", 12);

            return makeUser;
        }
    }

    /*
     * OnClick function for @+id/edit button
     */
    public void editProfile(View view){
        /*
        EditText editView = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editView.getText().toString();

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra("com.example.sendmessage.MESSAGE", message);

        startActivity(intent)
         */

        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
        //intent.putExtra("message", message); maybe user id
        intent.putExtra("user", user);

        startActivity(intent);
    }

    /*
     * OnClick function for @+id/friendCount textView
     */
    public void viewFriends(View view){
        Intent intent = new Intent(MainActivity.this, FriendsActivity.class);
        //intent.putExtra("message", message); maybe user id
        intent.putExtra("user", user);
        FriendsList fList = new FriendsList(user.ID, friends);
        intent.putExtra("friends", fList);
        startActivity(intent);
    }

    /*
     *  OnClick function for @+id/suggestFriends textView
     */
    public void suggestFriends(View view){
        Intent intent = new Intent(MainActivity.this, SuggestFriendsActivity.class);
        //intent.putExtra("message", message); maybe user id
        intent.putExtra("user", user);

        startActivity(intent);
    }

    /*
     *  OnClick function for @+id/inviteFriends textView
     */
    public void invite(View view){
        Intent intent = new Intent(MainActivity.this, InviteActivity.class);
        //intent.putExtra("message", message); maybe user id
        intent.putExtra("user", user);

        startActivity(intent);
    }

    /*
     *  OnClick function for @+id/create button
     */
    public void createMeeting(View view){
        Intent intent = new Intent(MainActivity.this, CreateMeetingActivity.class);
        //intent.putExtra("message", message); maybe user id
        intent.putExtra("user", user);

        startActivity(intent);
    }
}