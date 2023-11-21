package com.example.talk2friends;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;

public class TestSuccessfulLogin {

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
    public void testSuccessfulLogin() {
        // Mock the EditText fields
        loginFragment.emailEditText = mock(android.widget.EditText.class);
        loginFragment.passwordEditText = mock(android.widget.EditText.class);

        // Set up mock behavior for getText().toString()
        when(loginFragment.emailEditText.getText()).thenReturn(mock(android.text.Editable.class));
        when(loginFragment.emailEditText.getText().toString()).thenReturn("test@test.com");

        when(loginFragment.passwordEditText.getText()).thenReturn(mock(android.text.Editable.class));
        when(loginFragment.passwordEditText.getText().toString()).thenReturn("testPassword");

        // Mock the signInWithEmailAndPassword() method invocation
        loginFragment.loginUser(null);

        // Verify that signInWithEmailAndPassword is called with correct arguments
        verify(loginFragment.mAuth).signInWithEmailAndPassword("test@test.com", "testPassword");
    }

}
