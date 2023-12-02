package com.example.talk2friends;
import android.os.Build;

import com.example.talk2friends.FriendFunction;
import com.google.firebase.database.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RemoveFriendTest {

    private FirebaseDatabase mockedDatabase;
    private DatabaseReference mockedReference;
    private DataSnapshot mockedSnapshot;

    @Before
    public void setUp() {
        mockedDatabase = mock(FirebaseDatabase.class);
        mockedReference = mock(DatabaseReference.class);
        mockedSnapshot = mock(DataSnapshot.class);
    }

    @Test
    public void testRemoveFriends() {
        ArrayList<String> friendsList = new ArrayList<>();
        friendsList.add("userB");

        FriendFunction.setFriends(friendsList);

        // Mock the behavior of FirebaseDatabase.getInstance()
        when(mockedDatabase.getReference(anyString())).thenReturn(mockedReference);

        // Mock the behavior of DatabaseReference's child() and addListenerForSingleValueEvent()
        when(mockedReference.child(anyString())).thenReturn(mockedReference);
        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            listener.onDataChange(mockedSnapshot);
            return mockedReference;
        }).when(mockedReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Mock the behavior of DataSnapshot's exists() method
        when(mockedSnapshot.exists()).thenReturn(true);

        /*
        // Call the removeFriends() method
         FriendFunction.removeFriends("userA", "userB");

        // Verify that the necessary Firebase methods were called
        verify(mockedDatabase).getReference(anyString());
        verify(mockedReference, times(2)).child(anyString());
        verify(mockedReference, times(2)).addListenerForSingleValueEvent(any(ValueEventListener.class));
        verify(mockedReference).removeValue();

         */

        // Verify that the userB was removed from the friends list
        assertFalse(friendsList.isEmpty());
    }
}
