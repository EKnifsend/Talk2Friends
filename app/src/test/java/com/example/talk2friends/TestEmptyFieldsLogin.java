package com.example.talk2friends;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestEmptyFieldsLogin {

    @Test
    public void testAreFieldsEmpty() {
        LoginFragment loginFragment = new LoginFragment();

        // Simulate empty fields
        boolean result = loginFragment.areFieldsEmpty("", "");
        assertTrue(result); // Both fields are empty

        // Simulate non-empty fields
        result = loginFragment.areFieldsEmpty("username", "password");
        assertFalse(result); // Both fields are non-empty
    }
}
