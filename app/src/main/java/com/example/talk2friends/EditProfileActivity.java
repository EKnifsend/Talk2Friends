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

public class EditProfileActivity extends AppCompatActivity {
    User user;

    private EditText usernameEditText, ageEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();
        user = (User) intent.getParcelableExtra("user");

        // Make Profile information
        usernameEditText = (EditText) findViewById(R.id.username);
        String hintUsername = "Username: " + user.getName();
        usernameEditText.setHint(hintUsername);

        ageEditText = (EditText) findViewById(R.id.age);
        String hintAge = "Age: " + Integer.toString(user.getAge());
        ageEditText.setHint(hintAge);

        TextView association = (TextView) findViewById(R.id.association);
        if ((user.affiliation).compareTo("International Student") == 0) {
            association.setText(getString(R.string.international));
        }
        else {
            association.setText(getString(R.string.domestic));
        }

        TextView email = (TextView) findViewById(R.id.email);
        email.setText(user.getEmail());

        // Set up list of interests
        ArrayList<Interests> interests = new ArrayList<Interests>();

        for (Interests interest : Interests.values()) {
            interests.add(interest);
        }

        InterestAdapter interestAdapter = new InterestAdapter(this,interests);
        ListView interestsView = (ListView) findViewById(R.id.interests);
        interestsView.setAdapter(interestAdapter);

        interestsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Call meeting page with meeting info from index i
            }
        });
    }

    public void cancel(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void submitChanges(View view) {
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

        /*
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
