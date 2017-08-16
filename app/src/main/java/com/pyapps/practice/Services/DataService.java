package com.pyapps.practice.Services;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import  com.pyapps.practice.Constants.FirebaseConstants;
import com.pyapps.practice.Events.LoginEvent;
import com.pyapps.practice.Events.SignUpEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by prudh on 7/26/2017.
 */

public class DataService {
    private static final DataService ourInstance = new DataService();

    public static DataService getInstance() {
        return ourInstance;
    }
    FirebaseAuth m_Auth;
    FirebaseUser m_User;
    FirebaseDatabase m_Db;
    DatabaseReference m_MainRef;

    private DataService() {
        m_Auth = FirebaseAuth.getInstance();
        m_Db = FirebaseDatabase.getInstance();
        if (m_Db != null) {
            m_MainRef = m_Db.getReference();
        }
    }

    public FirebaseUser getCurrentUser() {
        if (m_Auth != null) {
            return m_Auth.getCurrentUser();
        } else {
            return null;
        }
    }

    public DatabaseReference getUsers() {
        if (m_Db != null) {
            if (m_MainRef != null) {
                return m_MainRef.child(FirebaseConstants.KEY_USERS);
            }
        }
        return null;
    }

    public DatabaseReference getMessages() {
        if (m_MainRef != null) {
            return m_MainRef.child(FirebaseConstants.KEY_MESSAGES);
        }
        return null;
    }

    public String getCurrentUserUID() {
        if (m_Auth != null) {
            m_User = m_Auth.getCurrentUser();
            if (m_User != null) {
                return m_User.getUid();
            }
        }
        return null;
    }

    public void logInUser(String username, String password) {
        if (m_Auth != null) {
            m_Auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    LoginEvent loginEvent = null;
                    if (task.isSuccessful()) {
                        FirebaseUser user = m_Auth.getCurrentUser();
                        if (user != null) {
                            loginEvent = new LoginEvent(this, user.getUid());
                        }
                    }
                    EventBus.getDefault().post(loginEvent);
                }
            });
        }
    }

    public void signUpUser(final String username, String password) {
        if (m_Auth != null) {
            m_Auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    SignUpEvent signupEvent = null;
                    if (task.isSuccessful()) {
                        FirebaseUser user = m_Auth.getCurrentUser();
                        if (user != null) {
                            signupEvent = new SignUpEvent(this, user.getUid());
                        }
                    }
                    EventBus.getDefault().post(signupEvent);
                }
            });
        }
    }

    public void getCurrentUserProfile() {
        String currentUserUID = getCurrentUserUID();
        if (m_MainRef != null && currentUserUID != null) {
            DatabaseReference usersRef = m_MainRef.child(FirebaseConstants.KEY_USERS);
            if (usersRef != null) {
                DatabaseReference currentUserRef = usersRef.child(currentUserUID);
                currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        com.pyapps.practice.Models.users.User user = getUserUserFormSnapShot1(dataSnapshot);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    public void getMatches(){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref =dbRef.child(FirebaseConstants.KEY_USERS);

    }

    public com.pyapps.practice.Models.users.User getUserUserFormSnapShot1(DataSnapshot dataSnapshot){
        com.pyapps.practice.Models.users.User user=null;
        if(dataSnapshot !=null)
        {
            user = dataSnapshot.getValue(com.pyapps.practice.Models.users.User.class);
        }
        return  user;

    }

    public void sendMessage(Object message)
    {
        DatabaseReference msgRef = this.m_MainRef.child(FirebaseConstants.KEY_MESSAGES);
        this.m_MainRef.child(FirebaseConstants.KEY_SENT).child(m_Auth.getCurrentUser().getUid()).child("messgages");


    }




}
