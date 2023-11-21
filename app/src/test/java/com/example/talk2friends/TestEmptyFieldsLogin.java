package com.example.talk2friends;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.talk2friends.LoginFragment;

public class TestEmptyFieldsLogin {

    @Test
    public void testAreFieldsEmpty() {
        LoginFragment loginFragment = Mockito.mock(LoginFragment.class);

        // Simulate empty fields
        boolean result = loginFragment.areFieldsEmpty("", "");
        assertTrue(result); // Both fields are empty

        // Simulate non-empty fields
        result = loginFragment.areFieldsEmpty("username", "password");
        assertFalse(result); // Both fields are non-empty
    }
}
