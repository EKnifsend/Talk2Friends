package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    private EditText usernameEditText, ageEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");

        /*
        TextView textView = (TextView) findViewById(R.id.resultMessage);
        textView.setText(message);
        *
         */

        usernameEditText = findViewById(R.id.username);
        ageEditText = findViewById(R.id.age);


    }

    public void cancel(View view){
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("message", message); maybe user id

        startActivity(intent);
    }

    public void submitChanges(View view) {
        String username = usernameEditText.getText().toString().trim();
        //int age = ageEditText.

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
