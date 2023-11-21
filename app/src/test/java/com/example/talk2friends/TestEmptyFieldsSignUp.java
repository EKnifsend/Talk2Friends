package com.example.talk2friends;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestEmptyFieldsSignUp {

    @Test
    public void testAreFieldsEmpty() {
        SignUpFragment signUpFragment = new SignUpFragment();

        // Create mock strings
        String username = "username";
        String email = "email";
        String password = "password";
        String age = "age";

        // Create mock for SignUpFragment and call method
        SignUpFragment spySignUpFragment = spy(signUpFragment);
        boolean fieldsEmpty = spySignUpFragment.areFieldsEmpty(username, email, password, age);

        // Verify the method was called and check the result
        verify(spySignUpFragment).areFieldsEmpty(username, email, password, age);
        assertFalse(fieldsEmpty); // All fields are non-empty
    }
}
