package com.ay.newchat;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newchat.R;

import java.util.ArrayList;

public class r3 extends RecyclerView.Adapter {
    ArrayList<String> list=new ArrayList<>();
    private LayoutInflater inflater;
    ac account= ac.getInstance();


    public r3(LayoutInflater inflater){this.inflater=inflater;}
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view=inflater.inflate(R.layout.cu2,parent,false);
        if(view==null)return null;
        return new ua1(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ua1 ua1 =(ua1)holder;
        ua1.textView.setText(list.get(position));
        if(account.getName().equals(list.get(position))){
            ua1.textView.setTextColor(Color.parseColor("#8DFF09"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ua1 extends RecyclerView.ViewHolder{
        TextView textView;
        public ua1(@NonNull View itemView) {
            super(itemView);
            if(itemView!=null)
                textView=itemView.findViewById(R.id.userlist_item);
        }
    }

    public void setItems(ArrayList<String> user){
        list=user;
        notifyDataSetChanged();
    }
}
