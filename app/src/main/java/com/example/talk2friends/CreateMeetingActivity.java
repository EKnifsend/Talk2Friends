package com.example.talk2friends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateMeetingActivity extends Activity {
    private User user;
    private EditText meetingNameEditText;
    private EditText meetingDescriptionEditText;
    private EditText meetingTimeEditText;
    private EditText meetingDateEditText;
    private EditText meetingLocationEditText;
    private Button createButton;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String userId;

    public CreateMeetingActivity() {
        // Required empty public constructor
    }

    public CreateMeetingActivity(int userId){
        userId = userId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_meeting);

        Intent intent = getIntent();
        user = (User) intent.getParcelableExtra("user");
        userId = user.ID;
        meetingDateEditText = findViewById(R.id.meetingDate);
        meetingDescriptionEditText = findViewById(R.id.meetingDescription);
        meetingLocationEditText = findViewById(R.id.meetingLocation);
        meetingNameEditText = findViewById(R.id.meetingName);
        meetingTimeEditText = findViewById(R.id.meetingTime);
        createButton = findViewById(R.id.createMeeting);

        meetingDateEditText.setHint("Date");
        meetingLocationEditText.setHint("Location");
        meetingDescriptionEditText.setHint("Description");
        meetingNameEditText.setHint("Name");
        meetingTimeEditText.setHint("Time");
        
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                String date = meetingDateEditText.getText().toString();
                String description = meetingDescriptionEditText.getText().toString();
                String location = meetingLocationEditText.getText().toString();
                String name = meetingNameEditText.getText().toString();
                String time = meetingTimeEditText.getText().toString();

                boolean flag = CheckInput(date, location, description, name, time);
                if(date.equalsIgnoreCase(""))
                {
                    meetingDateEditText.setError("please enter a date");
                }
                if(location.equalsIgnoreCase(""))
                {
                    meetingLocationEditText.setError("please enter a location");
                }
                if(description.equalsIgnoreCase(""))
                {
                    meetingDescriptionEditText.setError("please enter a description");
                }
                if(name.equalsIgnoreCase(""))
                {
                    meetingNameEditText.setError("please enter a name");
                }
                if(time.equalsIgnoreCase(""))
                {
                    meetingTimeEditText.setError("please enter a time");
                }


                if(flag)
                {
                    MeetingInfo mi = new MeetingInfo(name, 1, description, location, time, userId, new String());

                    DatabaseReference myRef = database.getReference();
                    myRef.child("meetings").child(mi.name).setValue(mi);

                    openMainActivity();
                }
            }
        });

    }

    public void back(View view){
        Intent intent = new Intent(CreateMeetingActivity.this, MainActivity.class);
        //intent.putExtra("message", message); maybe user id
        intent.putExtra("user", user);

        startActivity(intent);
    }
    private void openMainActivity() {
        Intent intent = new Intent(CreateMeetingActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public static boolean CheckInput(String date, String location, String description, String name, String time){
        if(date == null || date.equals("")){
            return false;
        }
        if(location == null || location.equals("")){
            return false;
        }
        if(description == null || description.equals("")){
            return false;
        }
        if(name == null || name.equals("")){
            return false;
        }
        if(time == null || time.equals("")){
            return false;
        }
        return true;
    }
}