package com.example.talk2friends;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {
    User user;

    DatabaseReference mDatabase;
    ArrayList<Interests> selectedInterests;

    private EditText usernameEditText, ageEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();
        user = (User) intent.getParcelableExtra("user");
        //MeetingInfo meetingInfo = (MeetingInfo) intent.getParcelableExtra("meetingInfo");
        selectedInterests = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference();

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
        InterestAdapter interestAdapter = new InterestAdapter(this,interests,selectedInterests);
        ListView interestsView = (ListView) findViewById(R.id.interests);
        interestsView.setAdapter(interestAdapter);

        for (Interests interest : Interests.values()) {
            interests.add(interest);

            String interestId = user.getID() + interest.toString();
            mDatabase.child("interests").child(interestId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // "interest1" does exists in the database
                        selectedInterests.add(interest);
                        interestAdapter.updateSeletected(selectedInterests);
                        interestAdapter.notifyDataSetChanged();
                    } else {
                        // "interest1" doesn't not exist in the database
                        //System.out.println("Entry " + interestId + "doesn't exist in the database.");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database error
                    System.err.println("Error checking 'interest1' entry: " + databaseError.getMessage());
                }
            });
        }

        interestsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Interests selectedInterest = interestAdapter.getValueAtIndex(i);

                if (selectedInterests.contains(selectedInterest)) {
                    view.setBackgroundColor(Color.WHITE);
                    selectedInterests.remove(selectedInterest);
                }
                else {
                    view.setBackgroundColor(getResources().getColor(R.color.lilac));
                    selectedInterests.add(selectedInterest);
                }
            }
        });

        Button logoutButton = findViewById(R.id.logOut);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform logout actions here
                // For example, navigate back to the login screen or perform logout logic
                logout();
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
            mDatabase.child("users").child(user.getID()).child("username").setValue(username);
        }
        if (ageInput.compareTo("") != 0) {
            user.changeAge(Integer.parseInt(ageInput));
            mDatabase.child("users").child(user.getID()).child("age").setValue(ageInput);
        }

        //update interests
        for (Interests interest : Interests.values()) {
            if (selectedInterests.contains(interest)) {
                System.out.println(interest);
                Interests.addInterest(user.getID(), interest);
            }
            else {
                Interests.removeInterest(user.getID(), interest);
            }
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
    // Method to perform logout actions
    private void logout() {
        // You can add code here to perform logout actions, such as clearing user session,
        // redirecting to the login screen, etc.
        // For example:
        // Clear user session data, finish current activity, and navigate back to the login screen
        Intent intent = new Intent(EditProfileActivity.this, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
