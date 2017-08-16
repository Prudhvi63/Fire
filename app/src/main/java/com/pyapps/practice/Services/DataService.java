package com.pyapps.practice.Services;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pyapps.practice.Constants.FirebaseKeyConstants;
import com.pyapps.practice.Events.LoginEvent;
import com.pyapps.practice.Events.MessageReceivedEvent;
import com.pyapps.practice.Events.SignUpEvent;
import com.pyapps.practice.Models.Message;

import org.greenrobot.eventbus.EventBus;

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
    DatabaseReference m_UsersRef;
    DatabaseReference m_MessagesRef;
    DatabaseReference m_SentRef;
    DatabaseReference m_ReceivedRef;
    DatabaseReference m_ConversationRef;
    DatabaseReference m_UserConvRef;
    DatabaseReference m_ConvBetweenRef;

    private DataService() {
        m_Auth = FirebaseAuth.getInstance();
        m_Db = FirebaseDatabase.getInstance();
        if (m_Db != null) {
            m_MainRef = m_Db.getReference();
            m_UsersRef = m_MainRef.child(FirebaseKeyConstants.USERS);
            m_MessagesRef = m_MainRef.child(FirebaseKeyConstants.MESSAGES);
            m_SentRef = m_MainRef.child(FirebaseKeyConstants.SENT);
            m_ReceivedRef = m_MainRef.child(FirebaseKeyConstants.RECEIVED);
            m_ConversationRef = m_MainRef.child(FirebaseKeyConstants.CONVERSATIONS);
            m_UserConvRef = m_MainRef.child(FirebaseKeyConstants.USER_CONVERSATIONS);
            m_ConvBetweenRef = m_MainRef.child(FirebaseKeyConstants.CONVERSATIONS_BTWN_USERS);
            setReceivedMessagesListenser();
        }
    }

    private void setReceivedMessagesListenser() {
        this.m_ReceivedRef.child(getCurrentUserUID()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message msg = (Message)dataSnapshot.getValue();
                MessageReceivedEvent event = new MessageReceivedEvent(this,msg);
                EventBus.getDefault().post(event);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






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
                return m_MainRef.child(FirebaseKeyConstants.USERS);
            }
        }
        return null;
    }

    public DatabaseReference getMessages() {
        if (m_MainRef != null) {
            return m_MainRef.child(FirebaseKeyConstants.MESSAGES);
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
            DatabaseReference usersRef = m_MainRef.child(FirebaseKeyConstants.USERS);
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
        DatabaseReference ref =dbRef.child(FirebaseKeyConstants.USERS);

    }

    public com.pyapps.practice.Models.users.User getUserUserFormSnapShot1(DataSnapshot dataSnapshot){
        com.pyapps.practice.Models.users.User user=null;
        if(dataSnapshot !=null)
        {
            user = dataSnapshot.getValue(com.pyapps.practice.Models.users.User.class);
        }
        return  user;
    }

    public void sendMessage(final Message message)
    {
        final String messageID = m_MessagesRef.push().getKey();
        m_MessagesRef.child(messageID).setValue(message);
        m_SentRef.child(getCurrentUserUID()).child(messageID).setValue(true);
        m_ReceivedRef.child(message.getTo()).child(messageID).setValue(true);
        final String conversationBetweenUsersKey = getConvBetweenUsersKey(message.getFrom(),message.getTo());
        m_ConvBetweenRef.child(conversationBetweenUsersKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String conversationID = null;
                if(dataSnapshot.exists()){
                    conversationID = dataSnapshot.getValue().toString();
                }
                else
                {
                    conversationID = m_ConversationRef.push().getKey();
                }
                m_ConversationRef.child(conversationID).child(messageID).setValue(true);
                m_ConvBetweenRef.child(conversationBetweenUsersKey).setValue(conversationID);
                m_UserConvRef.child(message.getFrom()).child(conversationID).setValue(true);
                m_UserConvRef.child(message.getTo()).child(conversationID).setValue(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String getConvBetweenUsersKey(String from, String to) {
        String result = null;
        if(from!=null && to!=null){
            if(from.compareTo(to) >=0){
                result = from.concat(to);
            }
            else
            {
                result = to.concat(from);
            }
        }
        return result;
    }


}
