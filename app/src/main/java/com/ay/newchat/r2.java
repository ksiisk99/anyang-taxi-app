package com.ay.newchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newchat.R;

import java.util.ArrayList;

public class r2 extends RecyclerView.Adapter implements i1 {
    ArrayList<p2> listrooms=new ArrayList<>();
    LayoutInflater inflater;
    i1 listener;

    r2(LayoutInflater inflater){this.inflater=inflater;}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view=inflater.inflate(R.layout.ro,parent,false);
        return new ra1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ra1 ra1 =(ra1)holder;

        ra1.title.setText(listrooms.get(position).getTitle());
        ra1.participant.setText(String.valueOf(listrooms.get(position).getParticipant())+" / "+String.valueOf(listrooms.get(position).getTotal()));

    }

    public void setOnItemClickListener(i1 listener){this.listener=listener;}

    @Override
    public void onItemClick(ra1 holder, View view, int position) {
        if(listener!=null){
            listener.onItemClick(holder,view,position);
        }
    }

    public class ra1 extends RecyclerView.ViewHolder {
        TextView title,participant;


        public ra1(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            participant=itemView.findViewById(R.id.participant);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener!=null){
                        listener.onItemClick(ra1.this,v,position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listrooms.size();
    }



    public void setList(ArrayList<p2> listrooms){
        this.listrooms=listrooms;
        notifyDataSetChanged();
    }

    public p2 getItem(int position){
        return listrooms.get(position);
    }

}
