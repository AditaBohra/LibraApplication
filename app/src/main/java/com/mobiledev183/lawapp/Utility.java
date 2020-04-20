package com.mobiledev183.lawapp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobiledev183.lawapp.Model.UsersModel;

import java.util.ArrayList;
import java.util.Objects;

public class Utility {
    private GetUserModelListListener userlistListener;

    public Utility(GetUserModelListListener userlListListener){
        this.userlistListener = userlListListener;

    }

    public interface GetUserModelListListener{
        void getUserList(ArrayList<UsersModel> usersModelArrayList);
    }

    public void getAllUserModelList(){
        ArrayList<UsersModel> arrayList = new ArrayList<>();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = mDatabase.getReference()
                .child(Objects.requireNonNull("Users"));

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    UsersModel usersModel = dataSnapshot1.getValue(UsersModel.class);
                    arrayList.add(usersModel);
                }
                userlistListener.getUserList(arrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    public static ArrayList<String> getAllUsers(){
//        ArrayList<String> userList = new ArrayList<>();
//        ArrayList<UsersModel> arrayList =  getAllUserModelList();
//        for (UsersModel user : arrayList){
//            userList.add(user.getUname());
//        }
//        return userList;
//    }

}
