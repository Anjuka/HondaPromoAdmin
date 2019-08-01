package com.anjukakoralage.hondapromoadmin;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.time.Year;
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
    Button btnDate;
    String dateTime;
    String dateTime1;
    private TextView tvNoData;
    Profile p;
    DatePickerDialog datePickerDialog;
    int year,month,dayOfMonth;
    Calendar calendar;
    String Month, Dayyy;
    String finDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        list = new ArrayList<Profile>();
        tvTotalCount = findViewById(R.id.tvTotalCount);
        tvNoData = findViewById(R.id.tvNoData);
        btnDate = findViewById(R.id.btnDate);

        recyclerView.setVisibility(View.GONE);
        btnDate.setVisibility(View.VISIBLE);


        nDialog = new ProgressDialog(MainActivity.this, R.style.MyAlertDialogStyle);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Get Data");;
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);

        /* Starting with all register data */

        /*reference = FirebaseDatabase.getInstance().getReference().child("user");

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
        });*/

        /* End of Normal Load */

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerView.stopScroll();
                list.clear();

                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth  = calendar.get(Calendar.DAY_OF_MONTH);

                recyclerView.setVisibility(View.VISIBLE);
                tvNoData.setVisibility(View.GONE);

                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        if(month < 10){

                            Month = "0" + (month + 1);
                        }
                        if(dayOfMonth < 10){

                            Dayyy  = "0" + dayOfMonth ;
                        }
                        else {
                            Dayyy = String.valueOf(dayOfMonth);
                        }
                        finDate = (year + " " + Month + " " + Dayyy);
                        btnDate.setText(finDate);

                        nDialog.show();

                        reference = FirebaseDatabase.getInstance().getReference().child("user");

                        ChildEventListener childEventListener = new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                                Profile user = dataSnapshot.getValue(Profile.class);

                                if (dataSnapshot.hasChild("dateTime")) {

                                    if (user.getdateTime().equals(finDate) ){
                                        tvNoData.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        list.add(user);
                                        adapter = new MyAdapter(MainActivity.this, list);
                                        recyclerView.setAdapter(adapter);
                                        tvTotalCount.setText("Total Records : " + list.size());
                                    }

                                }
                                nDialog.hide();
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
                    }
                },year,month,dayOfMonth);

                datePickerDialog.show();

                //list.clear();
            }
        });


    }


}
