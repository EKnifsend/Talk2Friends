package com.example.talk2friends;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.logging.StreamHandler;

public class MeetingFragment extends Fragment {

    private Button joinOrLeaveButton;
    private ArrayList<Button> addOrRemoveFriendsButton;
    private ArrayList<TextView> attendeeNames;
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

        UserListAdapter userListAdapter = new UserListAdapter(activity, attendees);
        attendeeList.setAdapter(userListAdapter);

        return view;
    }
}