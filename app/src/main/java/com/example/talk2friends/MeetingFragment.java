package com.example.talk2friends;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.StreamHandler;

public class MeetingFragment extends Fragment {

    private Button joinOrLeaveButton;
    private ArrayList<Button> addOrRemoveFriendsButton;
    private ArrayList<TextView> attendeeNames;
    private Button addOrRemoveCreatorAsFriend;
    private ListView attendeeList;
    private int meetingId;
    private int userId;
    private TextView attendeeSign;
    private TextView meetingName;
    private TextView meetingDescription;
    private TextView creatorSign;
    private TextView creatorName;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private MeetingInfo meetingInfo;
    private ArrayList<User> attendees = new ArrayList<User>();
    private Activity activity;
    private boolean isAttendee = false;

    public MeetingFragment() {
        // Required empty public constructor
    }

    public MeetingFragment(int userId, int meetingId, Activity activity){
        userId = userId;
        meetingId = meetingId;
        activity = activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meeting_tab, container, false);

        joinOrLeaveButton = view.findViewById(R.id.joinOrLeave);
        meetingDescription = view.findViewById(R.id.meetingDescriptionTab);
        attendeeSign = view.findViewById(R.id.attendees);
        creatorSign = view.findViewById(R.id.creator);
        creatorName = view.findViewById(R.id.creatorName);
        meetingName = view.findViewById(R.id.meetingNameTab);
        attendeeList = view.findViewById(R.id.attendeeList);
        addOrRemoveCreatorAsFriend = view.findViewById(R.id.addFriend);

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
                    meetingInfo = (MeetingInfo) task.getResult().getValue();
                    ArrayList<Integer> userIds = meetingInfo.AttendeeIds;

                    for(int i = 0; i < userIds.size(); ++i){
                        if(userIds.get(i) == userId){
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

        meetingName.setText(meetingInfo.Name);
        meetingDescription.setText(meetingInfo.Description);

        // Set up creator name
        myRef.child("users").child(String.valueOf(meetingInfo.CreatorId)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    User u = (User) task.getResult().getValue();
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
                    meetingInfo.AttendeeIds.remove(userId);
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
                    meetingInfo.AttendeeIds.add(userId);
                    String key = myRef.child("meetings").child(String.valueOf(meetingId)).push().getKey();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/meetings/" + String.valueOf(meetingId)+ key, meetingInfo);

                    myRef.updateChildren(childUpdates);
                }
            });
        }


        // set up list of attendees
        UserListAdapter userListAdapter = new UserListAdapter(activity, attendees);
        attendeeList.setAdapter(userListAdapter);
        attendeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }

            public void AddOrRemoveFriend(View view) {

                LinearLayout parentRow = (LinearLayout) view.getParent();

                Button button = parentRow.findViewById(R.id.addOrRemoveFriend);
                if(button.getText().equals("Add Friend")){
                    // TODO: Add friend
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
                }
                else{
                    // TODO: Remove friend
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
                }

            }
        });

        return view;
    }
}