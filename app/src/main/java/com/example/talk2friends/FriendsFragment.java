package com.example.talk2friends;

import android.app.Activity;
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
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendsFragment extends Fragment {
    private int userId;
    private ArrayList<User> friendsList;
    private TextView friendsView;
    private Button backButton;
    private ListView listView;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Activity activity;
    private FriendsList friends;


    public FriendsFragment(){
    }
    public FriendsFragment(int id, Activity activity){
        userId = id;
        this.activity = activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_tab, container, false);
        friendsView = view.findViewById(R.id.friendSign);
        backButton = view.findViewById(R.id.backButton);
        listView = view.findViewById(R.id.friendsList);

        UserListAdapter userListAdapter = new UserListAdapter(activity,friendsList);
        listView.setAdapter(userListAdapter);
        GetFriends();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
            public void AddOrRemoveFriend(View view) {
                LinearLayout parentRow = (LinearLayout) view.getParent();

                Button button = parentRow.findViewById(R.id.addOrRemoveFriend);
                TextView idView = parentRow.findViewById(R.id.hiddenId);
                if(button.getText().equals("Add Friend")){
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AddFriends(userId, Integer.parseInt((String) idView.getText()));
                            button.setText("Remove Friend");
                        }
                    });
                }
                else{
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RemoveFriends(userId, Integer.parseInt((String) idView.getText()));
                            button.setText("Add Friend");
                        }
                    });
                }
            }
        });
        return view;
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
                    ArrayList<Integer> userIds = friends.getFriends();

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

    public static void AddFriends(int userA, int userB){
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

    public static void RemoveFriends(int userA, int userB){
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

    public static boolean AreFriends(int userA, int userB){
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
