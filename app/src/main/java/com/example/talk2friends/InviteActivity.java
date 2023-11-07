package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InviteActivity extends AppCompatActivity  {
    User user;

    private EditText friendEmailEditText;
    private TextView results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        Intent intent = getIntent();
        user = (User) intent.getParcelableExtra("user");

        // Set up textbox
        friendEmailEditText = (EditText) findViewById(R.id.friendEmail);
        String hintFriendEmail = "Enter friend's @usc.edu email";
        friendEmailEditText.setHint(hintFriendEmail);

        // Set up results view
        results = (TextView) findViewById(R.id.results);
        results.setText("");

    }

    /*
     *  OnClick function for @+id/back button
     */
    public void cancel(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void submitChanges(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{String.valueOf(friendEmailEditText.getText())});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Invitation to Talk2Friends");
        intent.putExtra(Intent.EXTRA_TEXT, "Hi, this is an invitation to join Talk2Friends!");
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an Email client :")); 
        /*
        String username = usernameEditText.getText().toString().trim();
        String ageInput = ageEditText.getText().toString().trim();

        if (username.compareTo("") != 0) {
            user.changeName(username);
        }
        if (ageInput.compareTo("") != 0) {
            user.changeAge(Integer.parseInt(ageInput));
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign up successful, save user data to the database
                        String userId = mAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserDB = mDatabase.child(userId);
                        currentUserDB.child("username").setValue(username);

                        // You can add more user data fields here as needed

                        // Navigate to the LoginFragment on success
                        Navigation.findNavController(view).navigate(R.id.action_signup_to_login);
                    } else {
                        // Sign up failed, show an error message
                        Toast.makeText(getContext(), "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });

         */
    }
}
