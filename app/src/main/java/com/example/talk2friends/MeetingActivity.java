package com.example.talk2friends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.util.*;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MeetingActivity extends AppCompatActivity {
    private User user;
    private Button joinOrLeaveButton;
    private Button addOrRemoveCreatorAsFriend;
    private ListView attendeeList;
    private int meetingId;
    private String userId;
    private TextView attendeeSign;
    private TextView meetingName;
    private TextView meetingDescription;
    private TextView creatorSign;
    private TextView creatorName;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private MeetingInfo meetingInfo;
    private ArrayList<User> attendees = new ArrayList<User>();
    private boolean isAttendee = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_tab);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            user = (User) intent.getExtras().getParcelable("user");
            meetingInfo = (MeetingInfo) intent.getExtras().getParcelable("meetingInfo");
            Log.d("meeting info", meetingInfo.name);
        }
        userId = user.getID();
        meetingId = meetingInfo.meetingId;

        joinOrLeaveButton = findViewById(R.id.joinOrLeave);
        meetingDescription = findViewById(R.id.meetingDescriptionTab);
        attendeeSign = findViewById(R.id.attendees);
        creatorSign = findViewById(R.id.creator);
        creatorName = findViewById(R.id.creatorName);
        meetingName = findViewById(R.id.meetingNameTab);
        attendeeList = (ListView) findViewById(R.id.attendeeList);
        addOrRemoveCreatorAsFriend = findViewById(R.id.addFriend);

        attendeeSign.setText("Attendees");
        creatorSign.setText("Creator");

        DatabaseReference myRef = database.getReference();

        meetingName.setText(meetingInfo.name);
        meetingDescription.setText(meetingInfo.location + '\n' + meetingInfo.time + '\n' + meetingInfo.description);

        // Set up creator name
        myRef.child("users").child(meetingInfo.creatorId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                creatorName.setText(dataSnapshot.child("username").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", "loadPost:onCancelled", error.toException());
            }
        });


        // Get attendee list
        ArrayList<String> attendeeIds = new ArrayList<String>();

        // set up list of attendees
        UserListAdapter userListAdapter = new UserListAdapter(this, attendees, userId);
        attendeeList.setAdapter(userListAdapter);
        myRef.child("meetings/" + meetingInfo.name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                attendees.clear();
                attendeeIds.clear();
                userListAdapter.clear();
                meetingInfo.attendeeIds = snapshot.child("attendeeIds").getValue(String.class);
                attendeeIds.addAll(Arrays.asList(meetingInfo.attendeeIds.split(",")));
                if (meetingInfo.attendeeIds.isEmpty()) {
                    attendeeIds.clear();
                }
                for (int i = 0; i < attendeeIds.size(); ++i) {
                    if (attendeeIds.get(i) == user.ID) {
                        isAttendee = true;
                        continue;
                    }
                    myRef.child("users").child(attendeeIds.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String email = dataSnapshot.child("email").getValue(String.class);
                            String name = dataSnapshot.child("username").getValue(String.class);
                            int age = Integer.parseInt(dataSnapshot.child("age").getValue(String.class));
                            String affiliation = dataSnapshot.child("userType").getValue(String.class);
                            User makeUser;

                            if (affiliation.compareTo("Native Speaker") == 0) {
                                makeUser = new NativeSpeaker(dataSnapshot.getValue().toString(), email, name, age);
                            } else {
                                makeUser = new InternationalStudent(dataSnapshot.getValue().toString(), email, name, age, "Spanish");
                            }
                            attendees.add(makeUser);
                            userListAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w("firebase", "loadPost:onCancelled", error.toException());
                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        attendeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MeetingActivity.this, ProfileActivity.class);
                //intent.putExtra("message", message); maybe user id
                intent.putExtra("user", user);
                intent.putExtra("subject", attendees.get(i));

                startActivity(intent);
            }

            public void AddOrRemoveFriend(View view) {

                LinearLayout parentRow = (LinearLayout) view.getParent();

                Button button = parentRow.findViewById(R.id.addOrRemoveFriend);
                TextView idView = parentRow.findViewById(R.id.hiddenId);
                if (button.getText().equals("Add Friend")) {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FriendFunction.addFriends(userId, (String) idView.getText());
                            button.setText("Remove Friend");
                        }
                    });
                } else {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FriendsActivity.RemoveFriends(userId, (String) idView.getText());
                            button.setText("Add Friend");
                        }
                    });
                }
            }
        });

        // Set up button to add creator as friend
        if (!FriendFunction.areFriends(userId, meetingInfo.creatorId)) {
            addOrRemoveCreatorAsFriend.setText("Add Friend");
        } else {
            addOrRemoveCreatorAsFriend.setText("Remove Friend");
        }
        addOrRemoveCreatorAsFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FriendFunction.areFriends(userId, meetingInfo.creatorId)) {
                    addOrRemoveCreatorAsFriend.setText("Add Friend");
                    FriendFunction.addFriends(userId, meetingInfo.creatorId);
                } else {
                    addOrRemoveCreatorAsFriend.setText("Remove Friend");
                    FriendFunction.removeFriends(userId, meetingInfo.creatorId);
                }
            }
        });


        // set up join or leave meeting button
        if (isAttendee){
            joinOrLeaveButton.setText("Leave");
        }
        else{
            joinOrLeaveButton.setText("Join");
        }
        joinOrLeaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isAttendee){
                    joinOrLeaveButton.setText("Leave");
                    attendeeIds.remove(userId);
                    String key = myRef.child("meetings").child(String.valueOf(meetingId)).push().getKey();
                    StringBuilder str = new StringBuilder();
                    String commaseparatedlist = attendeeIds.toString();
                    commaseparatedlist
                            = commaseparatedlist.replace("[", "")
                            .replace("]", "")
                            .replace(" ", "");

                    meetingInfo.attendeeIds = commaseparatedlist;
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/meetings/" + meetingInfo.name, meetingInfo);
                    isAttendee = true;
                    myRef.updateChildren(childUpdates);
                }
                else {
                    joinOrLeaveButton.setText("Join");
                    attendeeIds.add(userId);
                    String key = myRef.child("meetings").child(String.valueOf(meetingId)).push().getKey();

                    String commaseparatedlist = attendeeIds.toString();
                    commaseparatedlist
                            = commaseparatedlist.replace("[", "")
                            .replace("]", "")
                            .replace(" ", "");

                    meetingInfo.attendeeIds = commaseparatedlist;

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/meetings/" + meetingInfo.name, meetingInfo);
                    isAttendee = false;
                    myRef.updateChildren(childUpdates);
                }
            }
        });




    }

    public void back(View view){
        Intent intent = new Intent(MeetingActivity.this, MainActivity.class);
        //intent.putExtra("message", message); maybe user id
        intent.putExtra("user", user);

        startActivity(intent);
    }
}