package com.example.talk2friends;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendFunction {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static ArrayList<String> friendsList;
    public static void setFriends(ArrayList<String> f){
        friendsList = f;
    }
    public static void addFriends(String userA, String userB){
        DatabaseReference myRef = database.getReference();
        String friend1;
        String friend2;
        String friendChildName;
        if (userA.compareTo(userB) < 0) {
            friend1 = userA;
            friend2 = userB;
        }
        else {
            friend1 = userB;
            friend2 = userA;
        }
        friendChildName = friend1 + friend2;
        myRef.child("friends").child(friendChildName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    myRef.child("friends").child(friendChildName).child("friend1Id").setValue(friend1);
                    myRef.child("friends").child(friendChildName).child("friend2Id").setValue(friend2);
                    friendsList.add(userB);
                } else {
                    // "interest1" does not exist in the database
                    System.out.println("Entry 'interest1' does exist in the database.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                System.err.println("Error checking 'interest1' entry: " + databaseError.getMessage());
            }
        });
    }

    public static void removeFriends(String userA, String userB){
        DatabaseReference myRef = database.getReference();
        String friend1;
        String friend2;
        String friendChildName;
        if (userA.compareTo(userB) < 0) {
            friend1 = userA;
            friend2 = userB;
        }
        else {
            friend1 = userB;
            friend2 = userA;
        }
        friendChildName = friend1 + friend2;
        myRef.child("friends").child(friendChildName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    System.out.println("Entry 'interest1' does exist in the database.");
                } else {
                    // "interest1" does not exist in the database
                    myRef.child("friends").child(friendChildName).removeValue();
                    friendsList.remove(userB);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                System.err.println("Error checking 'interest1' entry: " + databaseError.getMessage());
            }
        });
    }

    public static boolean areFriends(String userA, String userB){
        DatabaseReference myRef = database.getReference();
        return friendsList.contains(userB);
       /* final boolean[] flag = {false};
        myRef.child("friends").child(String.valueOf(userA)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    FriendsList friends = (FriendsList) task.getResult().getValue();
                    ArrayList<String> userIds = friends.getFriends();
                    if(userIds.contains(userB)){
                         flag[0] = true;
                    }

                }
            }
        });

         return flag[0]; */
    }

    public static int countFriends(String userId) {
        //count friends
        return 0;
    }

    public ArrayList<User> getFriendsList() {
        return friendsList;
    }
}
