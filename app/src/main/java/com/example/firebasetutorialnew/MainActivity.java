package com.example.firebasetutorialnew;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebasetutorialnew.model.User;
import com.example.firebasetutorialnew.model.UserAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


//without stopping the app, if i run it again, then we will get the output of onChildAdded
//because, the listener attached to it was not destroyed
//on running it for the second time without stopping the previous state, we get the
//output twice, because, there are two event listeners attached to it now.


public class MainActivity extends AppCompatActivity {


    private EditText nameEdittext, ageEdittext;
    private Button runCodeButton;
    private static final String  TAG = "my tag";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;
    private List<User> mDatalist;

    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("users");
        mDatalist = new ArrayList<>();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUserAdapter = new UserAdapter(this,mDatalist);
        mRecyclerView.setAdapter(mUserAdapter);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                user.setUid(dataSnapshot.getKey());
                Log.d(TAG,"onChildAdded : Name "+ user.getName());
                Log.d(TAG,"onChildAdded : Age "+ user.getAge());
                Log.d(TAG,"onChildAdded : Age "+ dataSnapshot.getKey());


                mDatalist.add(user);
                mUserAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mRef.addChildEventListener(childEventListener);



        runCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdittext.getText().toString();
                String key = mRef.push().getKey();
                try{
                    int age = Integer.parseInt(ageEdittext.getText().toString());
                    User user = new User(name,age);

                    mRef.child(key).setValue(user);
                    Toast.makeText(MainActivity.this,"data inserted...",Toast.LENGTH_SHORT).show();

                }
                catch(NumberFormatException e){
                    Log.d(TAG,"exception occured: invalid int : ");
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRef.removeEventListener(childEventListener);
    }
    private void initViews(){
        runCodeButton = this.findViewById(R.id.button);
        nameEdittext = this.findViewById(R.id.name_edit_text);
        ageEdittext = this.findViewById(R.id.age_edit_text);
        mRecyclerView = this.findViewById(R.id.recycler_view);

    }
}
