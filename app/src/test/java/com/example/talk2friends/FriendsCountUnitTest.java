package com.example.talk2friends;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

public class FriendsCountUnitTest {
    ArrayList<String> friendList = new ArrayList<String>();
    @Before
    public void SetUp(){
        friendList.add("hello");
        friendList.add("world");
        FriendFunction.setFriends(friendList);
    }
    @Test
    public void IsFriendsTest() {
        assertEquals(2, FriendFunction.countFriends());
        friendList.clear();
        FriendFunction.setFriends(friendList);
        assertEquals(0, FriendFunction.countFriends());
        friendList.add("new friend");
        FriendFunction.setFriends(friendList);
        assertEquals(1, FriendFunction.countFriends());
    }
}