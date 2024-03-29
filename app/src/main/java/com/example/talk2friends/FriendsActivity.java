package com.example.talk2friends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

public class FriendsActivity extends Activity {
    private User user;
    private String userId;
    private ArrayList<User> friendsList = new ArrayList<User>();
    private TextView friendsView;
    private Button backButton;
    private ListView listView;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FriendsList friends;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_tab);

        Intent intent = getIntent();
        user = (User) intent.getParcelableExtra("user");
        friends = (FriendsList) intent.getParcelableExtra("friends");
        userId = user.ID;

        friendsView = findViewById(R.id.friendSign);
        backButton = findViewById(R.id.backButton);
        listView = findViewById(R.id.friendsList);

        UserListAdapter userListAdapter = new UserListAdapter(this,friendsList, userId);
        listView.setAdapter(userListAdapter);
        DatabaseReference myRef = database.getReference();
        ArrayList<String> fList = friends.getFriends();
        userListAdapter.clear();
        for(int i = 0; i < fList.size(); ++i){
            myRef.child("users").child(fList.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //userListAdapter.clear();
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String name = dataSnapshot.child("username").getValue(String.class);
                    int age = Integer.parseInt(dataSnapshot.child("age").getValue(String.class));
                    String affiliation = dataSnapshot.child("userType").getValue(String.class);
                    User makeUser;

                    if (affiliation.compareTo("Native Speaker") == 0) {
                        makeUser = new NativeSpeaker(dataSnapshot.getKey(), email, name, age);
                    }
                    else {
                        makeUser = new InternationalStudent(dataSnapshot.getKey(), email, name, age, "Spanish");
                    }
                    friendsList.add(makeUser);
                    Log.d("HERE", String.valueOf(friendsList.size()));

                    userListAdapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("firebase", "loadPost:onCancelled", error.toException());
                }
            });
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FriendsActivity.this, ProfileActivity.class);
                //intent.putExtra("message", message); maybe user id
                intent.putExtra("user", user);
                intent.putExtra("subject", friendsList.get(i));

                startActivity(intent);
            }
            public void AddOrRemoveFriend(View view) {
                LinearLayout parentRow = (LinearLayout) view.getParent();

                Button button = parentRow.findViewById(R.id.addOrRemoveFriend);
                TextView idView = parentRow.findViewById(R.id.hiddenId);
                if(button.getText().equals("Add Friend")){
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FriendFunction.addFriends(userId, (String) idView.getText());
                            button.setText("Remove Friend");
                        }
                    });
                }
                else{
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FriendFunction.removeFriends(userId, (String) idView.getText());
                            button.setText("Add Friend");
                        }
                    });
                }
            }
        });
    }

    private void GetFriends(){
        DatabaseReference myRef = database.getReference();
        myRef.child("friends").child(String.valueOf(userId)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    friends = (FriendsList) task.getResult().getValue();
                    if(friends == null){
                        return;
                    }
                    ArrayList<String> userIds = friends.getFriends();

                    for(int i = 0; i < userIds.size(); ++i){
                        myRef.child("users").child(String.valueOf(userIds.get(i))).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                } else {
                                    User u = (User) task.getResult().getValue();
                                    friendsList.add(u);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    public static void AddFriends(String userA, String userB){
        DatabaseReference myRef = database.getReference();
        String friendChildName;
        if (userA.compareTo(userB) < 0) {
            friendChildName = userA + userB;
        }
        else {
            friendChildName = userB + userA;
        }
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

    public static void RemoveFriends(String userA, String userB){
        DatabaseReference myRef = database.getReference();
        String friendChildName;
        if (userA.compareTo(userB) < 0) {
            friendChildName = userA + userB;
        }
        else {
            friendChildName = userB + userA;
        }
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

    public static boolean AreFriends(String userA, String userB){
        DatabaseReference myRef = database.getReference();
        final boolean[] flag = {false};
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
        return flag[0];
    }

    public void back(View view){
        Intent intent = new Intent(FriendsActivity.this, MainActivity.class);
        //intent.putExtra("message", message); maybe user id
        intent.putExtra("user", user);

        startActivity(intent);
    }
}
