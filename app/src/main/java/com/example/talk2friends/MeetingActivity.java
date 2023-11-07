package com.example.talk2friends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
            user = (User) intent.getParcelableExtra("user");
            meetingInfo = (MeetingInfo) intent.getParcelableExtra("meetingInfo");
        }
        userId = user.getID();
        meetingId = meetingInfo.meetingId;

        joinOrLeaveButton = findViewById(R.id.joinOrLeave);
        meetingDescription = findViewById(R.id.meetingDescriptionTab);
        attendeeSign = findViewById(R.id.attendees);
        creatorSign = findViewById(R.id.creator);
        creatorName = findViewById(R.id.creatorName);
        meetingName = findViewById(R.id.meetingNameTab);
        attendeeList = findViewById(R.id.attendeeList);
        addOrRemoveCreatorAsFriend = findViewById(R.id.addFriend);

        attendeeSign.setText("Attendees");
        creatorSign.setText("Creator");

        // retrieve meeting info from database
        DatabaseReference myRef = database.getReference();
        myRef.child("meetings").child(String.valueOf(meetingId)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    meetingInfo = task.getResult().getValue(MeetingInfo.class);
                    if(meetingInfo == null){
                        return;
                    }
                    ArrayList<String> userIds = meetingInfo.attendeeIds;

                    for(int i = 0; i < userIds.size(); ++i){
                        if(userIds.get(i).compareTo(userId) == 0){
                            isAttendee = true;
                            continue;
                        }
                        myRef.child("users").child(String.valueOf(userIds.get(i))).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                } else {
                                    User u = (User) task.getResult().getValue();
                                    attendees.add(u);
                                }
                            }
                        });
                    }
                }
            }
        });

        meetingName.setText(meetingInfo.name);
        meetingDescription.setText(meetingInfo.location + '\n' + meetingInfo.time + '\n' + meetingInfo.description);

        // Set up creator name
        myRef.child("users").child(String.valueOf(meetingInfo.creatorId)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    User u = (User) task.getResult().getValue();
                    if(u == null) {
                        return;
                    }
                    creatorName.setText(u.getName());
                }
            }
        });

        // Set up button to add creator as friend
        if(true){
            addOrRemoveCreatorAsFriend.setText("Add Friend");
        }
        else{
            addOrRemoveCreatorAsFriend.setText("Remove Friend");
        }


        // set up join or leave meeting button
        if(isAttendee){
            joinOrLeaveButton.setText("Leave");
            joinOrLeaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    meetingInfo.attendeeIds.remove(userId);
                    String key = myRef.child("meetings").child(String.valueOf(meetingId)).push().getKey();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/meetings/" + String.valueOf(meetingId)+ key, meetingInfo);

                    myRef.updateChildren(childUpdates);
                }
            });
        }
        else{
            joinOrLeaveButton.setText("Join");
            joinOrLeaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    meetingInfo.attendeeIds.add(userId);
                    String key = myRef.child("meetings").child(String.valueOf(meetingId)).push().getKey();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/meetings/" + String.valueOf(meetingId)+ key, meetingInfo);

                    myRef.updateChildren(childUpdates);
                }
            });
        }


        // set up list of attendees
        UserListAdapter userListAdapter = new UserListAdapter(this, attendees);
        attendeeList.setAdapter(userListAdapter);
        attendeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                            FriendsActivity.AddFriends(userId, (String) idView.getText());
                            button.setText("Remove Friend");
                        }
                    });
                }
                else{
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
    }

    public void back(View view){
        Intent intent = new Intent(MeetingActivity.this, MainActivity.class);
        //intent.putExtra("message", message); maybe user id
        intent.putExtra("user", user);

        startActivity(intent);
    }
}