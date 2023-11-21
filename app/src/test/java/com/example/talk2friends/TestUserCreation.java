
package com.example.talk2friends;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;

public class TestUserCreation {

    private SignUpFragment signUpFragment;

    @Before
    public void setUp() {
        signUpFragment = new SignUpFragment();
        signUpFragment = new SignUpFragment();
        // Mock FirebaseAuth and set it in the fragment
        FirebaseAuth mockedAuth = mock(FirebaseAuth.class);
        signUpFragment.mAuth = mockedAuth;

        Task<AuthResult> mockedTask = mock(Task.class);
        when(mockedAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(mockedTask);
    }

    @Test
    public void testUserCreation() {
        String username = "testUser";
        String email = "test@usc.edu"; // A USC email address
        String password = "testPassword";
        String age = "25"; // Sample age

        // Mock the EditText fields
        signUpFragment.usernameEditText = mock(android.widget.EditText.class);
        signUpFragment.emailEditText = mock(android.widget.EditText.class);
        signUpFragment.passwordEditText = mock(android.widget.EditText.class);
        signUpFragment.ageEditText = mock(android.widget.EditText.class);

        signUpFragment.userTypeSpinner = mock(android.widget.Spinner.class);
        when(signUpFragment.userTypeSpinner.getSelectedItem()).thenReturn("Native Speaker"); // Mock selected item

        // Set up mock behavior for getText().toString()
        when(signUpFragment.usernameEditText.getText()).thenReturn(mock(android.text.Editable.class));
        when(signUpFragment.usernameEditText.getText().toString()).thenReturn(username);

        when(signUpFragment.emailEditText.getText()).thenReturn(mock(android.text.Editable.class));
        when(signUpFragment.emailEditText.getText().toString()).thenReturn(email);

        when(signUpFragment.passwordEditText.getText()).thenReturn(mock(android.text.Editable.class));
        when(signUpFragment.passwordEditText.getText().toString()).thenReturn(password);

        when(signUpFragment.ageEditText.getText()).thenReturn(mock(android.text.Editable.class));
        when(signUpFragment.ageEditText.getText().toString()).thenReturn(age);

        // Mock the signUpUser() method invocation
        signUpFragment.signUpUser(null);
    }
}
