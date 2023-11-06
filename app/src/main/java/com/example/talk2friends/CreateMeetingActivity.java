package com.example.talk2friends;

import static com.example.talk2friends.MainActivity.buildUser;

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

public class CreateMeetingActivity extends Activity {
    private User user;
    private EditText meetingNameEditText;
    private EditText meetingDescriptionEditText;
    private EditText meetingTimeEditText;
    private EditText meetingDateEditText;
    private EditText meetingLocationEditText;
    private Button createButton;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private int userId;

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
        user = buildUser(intent);
        userId = user.ID;
        meetingDateEditText = findViewById(R.id.meetingDate);
        meetingDescriptionEditText = findViewById(R.id.meetingDescription);
        meetingLocationEditText = findViewById(R.id.meetingLocation);
        meetingNameEditText = findViewById(R.id.meetingName);
        meetingTimeEditText = findViewById(R.id.meetingTime);
        createButton = findViewById(R.id.createMeeting);

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

    }

    public void back(View view){
        Intent intent = new Intent(CreateMeetingActivity.this, MainActivity.class);
        //intent.putExtra("message", message); maybe user id
        intent.putExtra("user", user);

        startActivity(intent);
    }
    private void openMainActivity(String userId) {
        Intent intent = new Intent(CreateMeetingActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}