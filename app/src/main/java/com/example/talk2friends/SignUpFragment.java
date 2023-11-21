package com.example.talk2friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpFragment extends Fragment {

    EditText usernameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText ageEditText; // Add the ageEditText
    private Button signUpButton, loginButton;
    Spinner userTypeSpinner;

    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        usernameEditText = view.findViewById(R.id.usernameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        signUpButton = view.findViewById(R.id.signUpButton);
        loginButton = view.findViewById(R.id.loginButton);
        userTypeSpinner = view.findViewById(R.id.userTypeSpinner);
        ageEditText = view.findViewById(R.id.ageEditText); // Initialize the ageEditText

        // Define an array of user types
        String[] userTypes = {"Native Speaker", "International Student"};

        // Create an ArrayAdapter to populate the Spinner with user types
        ArrayAdapter<String> userTypeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, userTypes);
        userTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter for the Spinner
        userTypeSpinner.setAdapter(userTypeAdapter);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser(v); // Pass the view to the signUpUser method
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_signup_to_login);
            }
        });

        return view;
    }

    void signUpUser(View view) {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();

        // Check if any of the fields are empty
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || age.isEmpty()) {
            Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return; // Exit the method
        }

        // Check if the email ends with "@usc.edu"
        if (!email.endsWith("@usc.edu")) {
            Toast.makeText(getContext(), "Please sign up with a USC email address", Toast.LENGTH_SHORT).show();
            return; // Exit the method
        }

        String userType = userTypeSpinner.getSelectedItem().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign up successful, save user data to the database
                        String userId = mAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserDB = mDatabase.child(userId);
                        currentUserDB.child("username").setValue(username);
                        currentUserDB.child("userType").setValue(userType);
                        currentUserDB.child("email").setValue(email);
                        currentUserDB.child("age").setValue(age);

                        // You can add more user data fields here as needed

                        // Navigate to the LoginFragment on success
                        Navigation.findNavController(view).navigate(R.id.action_signup_to_login);
                    } else {
                        // Sign up failed, show an error message
                        Toast.makeText(getContext(), "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public boolean areFieldsEmpty(String username, String email, String password, String age) {
        return username.isEmpty() || email.isEmpty() || password.isEmpty() || age.isEmpty();
    }
}
