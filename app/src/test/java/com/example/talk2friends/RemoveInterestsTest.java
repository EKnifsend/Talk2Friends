package com.example.talk2friends;

import android.os.Build;

import com.example.talk2friends.Interests;
import com.google.firebase.database.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}) // Adjust the SDK version as needed
public class RemoveInterestsTest {

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
    public void testRemoveInterest() {
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
        when(mockedSnapshot.exists()).thenReturn(true); // Simulating the interest exists in the database

        // Call the removeInterest() method
        Interests.removeInterest("userID", Interests.FILM);

        // Verify that the necessary Firebase methods were called
        verify(mockedDatabase).getReference(anyString());
        verify(mockedReference, times(2)).child(anyString());
        verify(mockedReference, times(2)).addListenerForSingleValueEvent(any(ValueEventListener.class));
        verify(mockedReference, times(1)).removeValue();
    }
}


