package com.anjukakoralage.hondapromoadmin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anjukakoralage on 29,July,2019
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Profile> profiles;

    public MyAdapter(Context c, ArrayList<Profile> p){
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.name.setText("Name : " + profiles.get(i).getName());
        myViewHolder.age.setText("Age : " + profiles.get(i).getAge());
        myViewHolder.city.setText("City : " + profiles.get(i).getCity());
        myViewHolder.tp.setText("T.P. : " + profiles.get(i).getTp());
        myViewHolder.time.setText("Time : " + profiles.get(i).getDate());

    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,age,city,tp,time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvName);
            age = (TextView) itemView.findViewById(R.id.tvAge);
            city = (TextView) itemView.findViewById(R.id.tvCity);
            tp = (TextView) itemView.findViewById(R.id.tvTP);
            time = (TextView) itemView.findViewById(R.id.tvSavedTime );
        }
    }
}
