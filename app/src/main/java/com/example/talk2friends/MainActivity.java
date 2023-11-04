package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    User user;  // The current user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        association.setText("w");

        TextView email = (TextView) findViewById(R.id.email);
        email.setText(user.getEmail());


        /*
        Intent intent = getIntent();
        String message = intent.getStringExtra("com.example.sendmessage.MESSAGE");
        message = message + " : Fight On!";

        TextView bottomButton = (TextView) findViewById(R.id.bottomButton);
        bottomButton.setOnClickListener(this::changeMode);
        tv.setText(getString(R.string.flag));
         */
        // Create user with ID
    }

    /*
     * Use Intent to set user correctly
     */
    private User buildUser(Intent intent) {
        if (intent.hasExtra("user")) { // User exists in app
            return ((User) intent.getParcelableExtra("user"));
        }
        else {
            User makeUser = new NativeSpeaker(1, "mike@usc.edu", "mike", 12);

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

    }

    /*
     *  OnClick function for @+id/suggestFriends textView
     */
    public void suggestFriends(View view){

    }

    /*
     *  OnClick function for @+id/inviteFriends textView
     */
    public void invite(View view){

    }

    /*
     *  OnClick function for @+id/create button
     */
    public void createMeeting(View view){

    }

    public void selectMeeting(View view){

    }
}