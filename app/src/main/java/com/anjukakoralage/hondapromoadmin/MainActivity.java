package com.anjukakoralage.hondapromoadmin;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseReference reference;
    private RecyclerView recyclerView;
    ArrayList<Profile> list;
    MyAdapter adapter;
    TextView tvTotalCount;
    TextView tvRefresh;
    ProgressDialog nDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        list = new ArrayList<Profile>();
        tvTotalCount = findViewById(R.id.tvTotalCount);
        tvRefresh = findViewById(R.id.tvRefresh);

        nDialog = new ProgressDialog(MainActivity.this, R.style.MyAlertDialogStyle  );
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Get Data");;
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);

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
                tvTotalCount.setText("Total Records " + list.size());
                nDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
            }
        });

        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

    }


}
