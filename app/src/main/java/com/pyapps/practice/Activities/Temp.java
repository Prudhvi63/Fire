package com.pyapps.practice.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pyapps.practice.Events.LoginEvent;
import com.pyapps.practice.Events.MessageReceivedEvent;
import com.pyapps.practice.Models.Message;
import com.pyapps.practice.Models.Post;
import com.pyapps.practice.R;
import com.pyapps.practice.RecyclerViewAdpater;
import com.pyapps.practice.Services.DataService;
import com.pyapps.practice.Services.TempService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class Temp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataService.getInstance().logInUser("prudhvi@c.com","secret123");
    }

    @Subscribe(threadMode= ThreadMode.POSTING)
    public void onLoginEvent(LoginEvent event)
    {
        if(event!=null && event.getUid()!=null)
        {
            Object o = event.getSender();
            Log.i("onLoginEvent","fired from"+o==null?"NA":o.getClass().getName());
            ArrayList<Post> posts = new ArrayList<Post>();
            posts.add(new Post("",""));
            TempService.increaseSize(posts);
            RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.recycler);
            RecyclerViewAdpater adapter = new RecyclerViewAdpater(posts,this);
            RecyclerView.LayoutManager  mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapter);
            //DataService.getInstance().sendMessage(new Message("Hello World!",DataService.getInstance().getCurrentUserUID(),"UID2"));
            //readData();
            //DataService.getInstance().getCurrentUserProfile();
        }else
        {
            Toast.makeText(getApplicationContext(),"Sign In Failed",Toast.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void onMessageReceived(MessageReceivedEvent event){
        if(event!=null && event.getMessage() != null){
            Object o = event.getSender();
            Log.i("onMessageReceivedEvent","fired from"+o==null?"NA":o.getClass().getName());

        }

    }

    public  void readData()
    {
        Log.w("LogIn","Sign in Success");
        Toast.makeText(getApplicationContext(),"Sign In Successful",Toast.LENGTH_LONG).show();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference databaseReference = db.getReference();
        DatabaseReference usersReference = databaseReference.child("users");
        usersReference.child(UID).setValue("sdsdsds");
        usersReference.child("user2").setValue("tttetet");
        usersReference.child("user3").setValue("reererer");
        usersReference.child("user4").setValue("rererergf");
        usersReference.child("user5").setValue("rere434334");

        DatabaseReference userRef = usersReference.child("user1");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
                String s = value!=null?value.toString():"";
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Data is Changed",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        usersReference.child("user1").setValue("Tyere");

    }

    @Subscribe
    public  void  messageReceived(MessageReceivedEvent e)
    {
        Message msg = e.getMessage();
        


    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
