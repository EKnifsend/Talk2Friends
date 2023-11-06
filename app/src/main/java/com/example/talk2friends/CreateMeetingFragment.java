package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateMeetingFragment extends Fragment {

    private EditText meetingNameEditText;
    private EditText meetingDescriptionEditText;
    private EditText meetingTimeEditText;
    private EditText meetingDateEditText;
    private EditText meetingLocationEditText;
    private Button createButton;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private int userId;

    public CreateMeetingFragment() {
        // Required empty public constructor
    }

    public CreateMeetingFragment(int userId){
        userId = userId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_meeting, container, false);

        meetingDateEditText = view.findViewById(R.id.meetingDate);
        meetingDescriptionEditText = view.findViewById(R.id.meetingDescription);
        meetingLocationEditText = view.findViewById(R.id.meetingLocation);
        meetingNameEditText = view.findViewById(R.id.meetingName);
        meetingTimeEditText = view.findViewById(R.id.meetingTime);
        createButton = view.findViewById(R.id.createMeeting);

        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                String date = meetingDateEditText.getText().toString();
                String description = meetingDescriptionEditText.getText().toString();
                String location = meetingLocationEditText.getText().toString();
                String name = meetingNameEditText.getText().toString();
                String time = meetingTimeEditText.getText().toString();

                boolean flag = true;
                if(date.equalsIgnoreCase(""))
                {
                    meetingDateEditText.setHint("please enter a date");
                    meetingDateEditText.setError("please enter a date");
                    flag = false;
                }
                if(location.equalsIgnoreCase(""))
                {
                    meetingLocationEditText.setHint("please enter a location");
                    meetingLocationEditText.setError("please enter a location");
                    flag = false;
                }
                if(description.equalsIgnoreCase(""))
                {
                    meetingDescriptionEditText.setHint("please enter a description");
                    meetingDescriptionEditText.setError("please enter a description");
                    flag = false;
                }
                if(name.equalsIgnoreCase(""))
                {
                    meetingNameEditText.setHint("please enter a name");
                    meetingNameEditText.setError("please enter a name");
                    flag = false;
                }
                if(time.equalsIgnoreCase(""))
                {
                    meetingTimeEditText.setHint("please enter a time");
                    meetingTimeEditText.setError("please enter a time");
                    flag = false;
                }


                if(flag)
                {
                    MeetingInfo mi = new MeetingInfo();
                    mi.CreatorId = userId;
                    mi.MeetingId = 1; // TODO create a way to generate ids
                    mi.Name = name;
                    mi.Time = date + time;
                    mi.Description = description;
                    mi.Location = location;

                    DatabaseReference myRef = database.getReference();
                    myRef.child("meetings").child(String.valueOf(userId)).setValue(mi);

                    openMainActivity(String.valueOf(userId));
                }
            }
        });
        return view;

    }


    private void openMainActivity(String userId) {
        Intent intent = new Intent(requireContext(), MainActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        requireActivity().finish();
    }
}