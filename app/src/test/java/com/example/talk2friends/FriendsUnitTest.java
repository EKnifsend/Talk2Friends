package com.example.talk2friends;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

public class FriendsUnitTest {
    ArrayList<String> friendList = new ArrayList<String>();
    @Before
    public void SetUp(){
        friendList.add("hello");
        friendList.add("world");
        FriendFunction.setFriends(friendList);
    }
    @Test
    public void IsFriendsTest() {
        assertEquals(true, FriendFunction.areFriends("","hello"));
        assertEquals(true, FriendFunction.areFriends("","world"));
        assertEquals(false, FriendFunction.areFriends("","helloworld"));
        assertEquals(false, FriendFunction.areFriends("",""));
    }
}