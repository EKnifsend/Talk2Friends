package com.example.talk2friends;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendFunction {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static void addFriends(int userA, int userB){
        DatabaseReference myRef = database.getReference();
        myRef.child("friends").child(String.valueOf(userA)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    FriendsList friends = (FriendsList) task.getResult().getValue();
                    friends.friends.add(userB);
                    String key = myRef.child("friends").child(String.valueOf(userA)).push().getKey();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/meetings/" + String.valueOf(userA)+ key, friends);

                    myRef.updateChildren(childUpdates);
                }
            }
        });
        myRef.child("friends").child(String.valueOf(userB)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    FriendsList friends = (FriendsList) task.getResult().getValue();
                    friends.friends.add(userA);
                    String key = myRef.child("friends").child(String.valueOf(userB)).push().getKey();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/meetings/" + String.valueOf(userB)+ key, friends);

                    myRef.updateChildren(childUpdates);
                }
            }
        });
    }

    public static void removeFriends(int userA, int userB){
        DatabaseReference myRef = database.getReference();
        myRef.child("friends").child(String.valueOf(userA)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    FriendsList friends = (FriendsList) task.getResult().getValue();
                    friends.friends.remove(userB);
                    String key = myRef.child("friends").child(String.valueOf(userA)).push().getKey();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/meetings/" + String.valueOf(userA)+ key, friends);

                    myRef.updateChildren(childUpdates);
                }
            }
        });
        myRef.child("friends").child(String.valueOf(userB)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    FriendsList friends = (FriendsList) task.getResult().getValue();
                    friends.friends.remove(userA);
                    String key = myRef.child("friends").child(String.valueOf(userB)).push().getKey();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/meetings/" + String.valueOf(userB)+ key, friends);

                    myRef.updateChildren(childUpdates);
                }
            }
        });
    }

    public static boolean areFriends(int userA, int userB){
        DatabaseReference myRef = database.getReference();
        final boolean[] flag = {false};
        myRef.child("friends").child(String.valueOf(userA)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    FriendsList friends = (FriendsList) task.getResult().getValue();
                    ArrayList<Integer> userIds = friends.getFriends();
                    if(userIds.contains(userB)){
                        flag[0] = true;
                    }

                }
            }
        });

        return flag[0];
    }
}
