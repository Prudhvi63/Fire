package com.pyapps.practice.Services;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

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
import com.pyapps.practice.Activities.MainActivity;
import  com.pyapps.practice.Constants.FirebaseConstants;
import com.pyapps.practice.Events.LoginEvent;
import com.pyapps.practice.Events.SignUpEvent;
import com.pyapps.practice.Models.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusBuilder;

import java.util.ArrayList;
import java.util.Map;

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
                        User user = getUserFromSnapShot(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    private User getUserFromSnapShot(DataSnapshot dataSnapshot) {
        User user = null;
        if (dataSnapshot != null) {
                String name = (String)dataSnapshot.child(FirebaseConstants.KEY_NAME).getValue();
                String email = (String)dataSnapshot.child(FirebaseConstants.KEY_EMAIL).getValue();;
                String about = (String)dataSnapshot.child(FirebaseConstants.KEY_ABOUT).getValue();;
                long gender = (long)dataSnapshot.child(FirebaseConstants.KEY_GENDER).getValue();;
                String height = (String)dataSnapshot.child(FirebaseConstants.KEY_HEIGHT).getValue();;
                String phone = (String)dataSnapshot.child(FirebaseConstants.KEY_PHONE).getValue();;
                long weight = (long)dataSnapshot.child(FirebaseConstants.KEY_WEIGHT).getValue();;
                ArrayList<String> sentMessages =null;
                ArrayList<String> receivedMessages=null;
                ArrayList<String> photoUrls=null;
                if (dataSnapshot.hasChild(FirebaseConstants.KEY_PHOTOURLS)) {
                    DataSnapshot photoSnapshot = dataSnapshot.child(FirebaseConstants.KEY_PHOTOURLS);
                    photoUrls = new ArrayList<String>();
                    for (DataSnapshot dataSnapshot1 : photoSnapshot.getChildren()) {
                        photoUrls.add(dataSnapshot1.getKey());
                    }
                }
                if (dataSnapshot.hasChild(FirebaseConstants.KEY_MESSAGES)) {
                    DataSnapshot messagesSnapShot = dataSnapshot.child(FirebaseConstants.KEY_MESSAGES);
                    if (messagesSnapShot.hasChild(FirebaseConstants.KEY_SENT)) {
                        DataSnapshot sentSnapshot = messagesSnapShot.child(FirebaseConstants.KEY_SENT);
                        sentMessages = new ArrayList<String>();
                        for (DataSnapshot messageSnapshot : sentSnapshot.getChildren()) {
                            sentMessages.add(messageSnapshot.getKey());
                        }
                    }
                    if (messagesSnapShot.hasChild(FirebaseConstants.KEY_RECEIVED)) {
                        DataSnapshot receivedSnapshot = messagesSnapShot.child(FirebaseConstants.KEY_RECEIVED);
                        receivedMessages = new ArrayList<String>();
                        for (DataSnapshot messageSnapshot : receivedSnapshot.getChildren()) {
                            receivedMessages.add(messageSnapshot.getKey());
                        }
                    }
                }
                user = new User(email,about,gender,height,name,phone,weight,photoUrls,sentMessages,receivedMessages);

        }

        return user;
    }




}
