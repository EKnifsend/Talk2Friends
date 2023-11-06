package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(v); // Pass the view to the loginUser method
            }
        });

        return view;
    }

    private void loginUser(View view) {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Check if email and password fields are empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return; // Exit the method
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        String userId = user.getUid();
                        // Pass the user ID to MainActivity
                        openMainActivity(userId);
                    } else {
                        // Login failed, show an error message
                        Toast.makeText(getContext(), "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openMainActivity(String userId) {
        /*
        Intent intent = new Intent(requireContext(), MainActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        requireActivity().finish();

         */

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.child("email").getValue(String.class);
                String name = dataSnapshot.child("username").getValue(String.class);
                int age = Integer.parseInt(dataSnapshot.child("age").getValue(String.class));
                String affiliation = dataSnapshot.child("userType").getValue(String.class);

                User makeUser;

                if (affiliation.compareTo("Native Speaker") == 0) {
                    makeUser = new NativeSpeaker(userId, email, name, age);
                }
                else {
                    makeUser = new InternationalStudent(userId, email, name, age, "Spanish");
                }

                Intent intent = new Intent(requireContext(), MainActivity.class);
                intent.putExtra("user", makeUser);
                startActivity(intent);
                requireActivity().finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", "loadPost:onCancelled", error.toException());
            }
        });

    }
}
