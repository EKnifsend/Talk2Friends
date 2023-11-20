package com.example.talk2friends;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public enum Interests {
    FILM(1), READING(2), SPORTS(3), WORKING_OUT(4), ART(5), TRAVEL(6), PHOTOGRAPHY(7);

    final int ID;

    Interests(int ID) {
        this.ID = ID;
    }

    public static Interests getById(long id)
    {
        for (Interests interest : Interests.values())
        {
            if (id == interest.getID()) return interest;
        }
        throw new IllegalArgumentException("oh no");
    }

    public static ArrayList<Interests> getInterests(String userID) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        return null;
    }

    public static void addInterest(String userID, Interests interest) {
        String interestId = userID + interest.toString();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("interests").child(interestId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // "interest1" doesn't exists in the database
                    String interestId = userID + interest.toString();
                    mDatabase.child("interests").child(interestId).child("userId").setValue(userID);
                    mDatabase.child("interests").child(interestId).child("interest").setValue(interest);
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

    public static void removeInterest(String userID, Interests interest) {
        String interestId = userID + interest.toString();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("interests").child(interestId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // "interest1" doesn't exists in the database
                    String interestId = userID + interest.toString();
                    mDatabase.child("interests").child(interestId).removeValue();
                } else {
                    // "interest1" does not exist in the database
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                System.err.println("Error checking 'interest1' entry: " + databaseError.getMessage());
            }
        });
    }

    public static boolean containsInterestEntry(String userID, Interests interest) {
        /*
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild("username")
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0) {
                    //username found

                }else{
                    // username not found
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

         */

        return false;
    }


    public String toStringPretty() {
        String interestString = this.toString();
        interestString = interestString.substring(0, 1).toUpperCase() + interestString.substring(1).toLowerCase();
        interestString = interestString.replace("_", " ");

        return interestString;
    }

    public int getID() {
        return ID;
    }

}
