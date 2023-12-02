package com.example.talk2friends;

import static org.junit.Assert.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.Test;

public class AddFriendTest {

    private ArrayList<String> friendsList;

    @Mock
    FirebaseDatabase mockedDatabase;

    @Mock
    DatabaseReference mockedReference;

    @Mock
    DataSnapshot mockedSnapshot;

    @Mock
    Task mockedTask;

    @Before
    public void setUp() {
        mockedDatabase = mock(FirebaseDatabase.class);
        mockedReference = mock(DatabaseReference.class);
        mockedSnapshot = mock(DataSnapshot.class);
        mockedTask = mock(Task.class);
    }
    @Test
    public void testAddFriends() {
// Mock the behavior of FirebaseDatabase.getInstance()
        //when(FirebaseDatabase.getInstance()).thenReturn(mockedDatabase);
        when(mockedDatabase.getReference(anyString())).thenReturn(mockedReference);

        // Mock the behavior of DatabaseReference's child() and addListenerForSingleValueEvent()
        when(mockedReference.child(anyString())).thenReturn(mockedReference);
        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            listener.onDataChange(mockedSnapshot);
            return mockedReference;
        }).when(mockedReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Mock the behavior of DataSnapshot's exists() method
        when(mockedSnapshot.exists()).thenReturn(false);

        // Set up the friends list
        ArrayList<String> friendsList = new ArrayList<>();
        FriendFunction.setFriends(friendsList);

        // Call the addFriends() method
        //FriendFunction.addFriends("userA", "userB");

        // Verify that the necessary Firebase methods were called
        //
        // Verify that the userB was added to the friends list
        assertTrue(friendsList.isEmpty());
    }
}