package com.example.talk2friends;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;

public class TestUnsuccessfulLogin {

    private LoginFragment loginFragment;

    @Before
    public void setUp() {
        loginFragment = new LoginFragment();
        // Mock FirebaseAuth and set it in the fragment
        FirebaseAuth mockedAuth = mock(FirebaseAuth.class);
        loginFragment.mAuth = mockedAuth;

        Task<AuthResult> mockedTask = mock(Task.class);
        when(mockedAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(mockedTask);
    }

    @Test
    public void testUnsuccessfulLogin() {
        // Mock the EditText fields
        loginFragment.emailEditText = mock(android.widget.EditText.class);
        loginFragment.passwordEditText = mock(android.widget.EditText.class);

        // Set up mock behavior for getText().toString()
        when(loginFragment.emailEditText.getText()).thenReturn(mock(android.text.Editable.class));
        when(loginFragment.emailEditText.getText().toString()).thenReturn("test@test.com");

        when(loginFragment.passwordEditText.getText()).thenReturn(mock(android.text.Editable.class));
        when(loginFragment.passwordEditText.getText().toString()).thenReturn("testPassword");

        // Simulate a failed login attempt by returning a failed task
        Task<AuthResult> mockedTask = mock(Task.class);
        when(mockedTask.isSuccessful()).thenReturn(false);
        when(loginFragment.mAuth.signInWithEmailAndPassword("test@test.com", "testPassword")).thenReturn(mockedTask);

        // Call loginUser method
        loginFragment.loginUser(null);

        // Check if the login attempt was unsuccessful
        assertFalse(loginFragment.isLoginSuccessful());
    }
}
