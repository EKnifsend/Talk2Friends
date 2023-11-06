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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    User user;  // The current user

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        user = buildUser(intent);

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

        // Set up list of meetings
        ArrayList<MeetingInfo> meetings = new ArrayList<MeetingInfo>();

        meetings.add(new MeetingInfo("CSCI 310", 1, "", "", "", "", null));
        meetings.add(new MeetingInfo("CSCI 312", 1, "", "", "", "", null));
        meetings.add(new MeetingInfo("CSCI 313", 1, "", "", "", "", null));
        meetings.add(new MeetingInfo("CSCI 314", 1, "", "", "", "", null));
        meetings.add(new MeetingInfo("CSCI 310", 1, "", "", "", "", null));
        meetings.add(new MeetingInfo("CSCI 310", 1, "", "", "", "", null));
        meetings.add(new MeetingInfo("CSCI 310", 1, "", "", "", "", null));
        meetings.add(new MeetingInfo("CSCI 310", 1, "", "", "", "", null));
        meetings.add(new MeetingInfo("CSCI 310", 1, "", "", "", "", null));
        meetings.add(new MeetingInfo("CSCI 310", 1, "", "", "", "", null));
        meetings.add(new MeetingInfo("CSCI 315", 1, "", "", "", "", null));

        MeetingAdapter meetingAdapter = new MeetingAdapter(this,meetings);
        ListView meetingsView = (ListView) findViewById(R.id.meetings);
        meetingsView.setAdapter(meetingAdapter);

        meetingsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Call meeting page with meeting info from index i
                Intent intent = new Intent(MainActivity.this, MeetingActivity.class);
                //intent.putExtra("message", message); maybe user id
                intent.putExtra("user", user);
                intent.putExtra("meetingInfo", meetings.get(i));

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
        else if (intent.hasExtra("userId")) {
            // change
            String userId = intent.getStringExtra("userId");
            final String[] email = {"mike@usc.edu"};
            final String[] name = new String[1];
            final int[] age = new int[1];
            final String[] affiliation = new String[1];

            User makeUser;

            mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    email[0] = dataSnapshot.child("email").getValue(String.class);
                    name[0] = dataSnapshot.child("username").getValue(String.class);
                    age[0] = dataSnapshot.child("age").getValue(int.class);
                    affiliation[0] = dataSnapshot.child("userType").getValue(String.class);

                    // Now you can use the 'email' variable here or in a callback function
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            if (affiliation[0].compareTo("Native Speaker") == 0) {
                makeUser = new NativeSpeaker(userId, email[0], name[0], age[0]);
            }
            else {
                makeUser = new InternationalStudent(userId, email[0], name[0], age[0], "Spanish");
            }

            return makeUser;
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