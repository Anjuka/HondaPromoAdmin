package com.anjukakoralage.hondapromoadmin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DatabaseReference reference;
    private RecyclerView recyclerView;
    ArrayList<Profile> list;
    MyAdapter adapter;
    TextView tvTotalCount;
    TextView tvRefresh;
    ProgressDialog nDialog;
    Switch sw;
    String dateTime;
    String dateTime1;
    Profile p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        list = new ArrayList<Profile>();
        tvTotalCount = findViewById(R.id.tvTotalCount);
        tvRefresh = findViewById(R.id.tvRefresh);
        sw = findViewById(R.id.sw);

        nDialog = new ProgressDialog(MainActivity.this, R.style.MyAlertDialogStyle);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Get Data");;
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);

        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });


        reference = FirebaseDatabase.getInstance().getReference().child("user");

        nDialog.show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Profile p = snapshot.getValue(Profile.class);
                    list.add(p);
                }
                adapter = new MyAdapter(MainActivity.this, list);
                recyclerView.setAdapter(adapter);
                nDialog.dismiss();
                tvTotalCount.setText("Total Records : " + list.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
            }
        });

        sw.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {

                            list.clear();
                            tvTotalCount.setText("Register For Today");

                            Toast.makeText(MainActivity.this,
                                    "Show Today Results", Toast.LENGTH_SHORT).show();

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
                            dateTime1 = sdf.format(Calendar.getInstance().getTime());

                            reference = FirebaseDatabase.getInstance().getReference().child("user");

                            ChildEventListener childEventListener = new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                                    Profile user = dataSnapshot.getValue(Profile.class);

                                    if (dataSnapshot.hasChild("dateTime")) {

                                        if (user.getdateTime().equals(dateTime1)) {
                                            list.add(user);
                                            adapter = new MyAdapter(MainActivity.this, list);
                                            recyclerView.setAdapter(adapter);
                                        }
                                    }
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                                    //do somethings
                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {
                                    //...
                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                                    //...
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    //...
                                }
                            };
                            reference.addChildEventListener(childEventListener);
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Show All Results", Toast.LENGTH_SHORT).show();
                            recreate();

                        }
                    }
                });

    }


}
