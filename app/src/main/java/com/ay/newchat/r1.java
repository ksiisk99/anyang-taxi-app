package com.ay.newchat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newchat.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class r1 extends RecyclerView.Adapter {

    private static final int TYPE_SENT=0;
    private static final int TYPE_SENT2=3;
    private static final int TYPE_RECEIVED=1;
    private static final int TYPE_RECEIVED2=2;
    private static final int TYPE_ENTER=4;
    private static final int TYPE_EXIT=5;
    private LayoutInflater inflater;

    private List<JSONObject> messages=new ArrayList<>();

    public r1(LayoutInflater inflater){this.inflater=inflater;}
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch(viewType){
            case TYPE_SENT:
                view=inflater.inflate(R.layout.ci1,parent,false);
                return new SentMessageHolder(view);
            case TYPE_RECEIVED:
                view=inflater.inflate(R.layout.ci3,parent,false);
                return new ReceivedMessageHolder(view);
            case TYPE_RECEIVED2:
                view=inflater.inflate(R.layout.ci4,parent,false);
                return new Received2MessageHolder(view);
            case TYPE_SENT2:
                view=inflater.inflate(R.layout.ci2,parent,false);
                return new Sent2MessageHolder(view);
            case TYPE_ENTER:
                view=inflater.inflate(R.layout.ci5,parent,false);
                return new EnterHolder(view);
            case TYPE_EXIT:
                view=inflater.inflate(R.layout.ci6,parent,false);
                return new ExitHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        JSONObject message=messages.get(position);

        try {
            if(message.getInt("isSent")==0){
                SentMessageHolder messageHolder=(SentMessageHolder)holder;
                messageHolder.sent_time_txt.setText(message.getString("time"));
                messageHolder.messageTxt.setText(message.getString("message"));
            }else if(message.getInt("isSent")==1){
                ReceivedMessageHolder receivedMessageHolder=(ReceivedMessageHolder)holder;
                receivedMessageHolder.nameTxt.setText(message.getString("name"));
                receivedMessageHolder.receivedTxt.setText(message.getString("message"));
                receivedMessageHolder.receive_time_txt.setText(message.getString("time"));
            }else if(message.getInt("isSent")==2){
                Received2MessageHolder received2MessageHolder=(Received2MessageHolder)holder;
                received2MessageHolder.receivedTxt.setText(message.getString("message"));
                received2MessageHolder.receive2_time_txt.setText(message.getString("time"));
            }else if(message.getInt("isSent")==3){
                Sent2MessageHolder messageHolder=(Sent2MessageHolder)holder;
                messageHolder.sent_time_txt.setText(message.getString("time"));
                messageHolder.messageTxt.setText(message.getString("message"));
            }else if(message.getInt("isSent")==4){
                EnterHolder enterHolder=(EnterHolder)holder;
                enterHolder.enter.setText(message.getString("message"));
            }else{
                ExitHolder exitHolder=(ExitHolder)holder;
                exitHolder.exit.setText(message.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private class EnterHolder extends RecyclerView.ViewHolder{
        TextView enter;
        public EnterHolder(@NonNull View itemView) {
            super(itemView);
            enter=itemView.findViewById(R.id.enter);
        }
    }

    private class ExitHolder extends RecyclerView.ViewHolder{
        TextView exit;
        public ExitHolder(@NonNull View itemView) {
            super(itemView);
            exit=itemView.findViewById(R.id.exit);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder{
        TextView messageTxt,sent_time_txt;
        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);

            messageTxt=itemView.findViewById(R.id.sentMessage);
            sent_time_txt=itemView.findViewById(R.id.sent_time_txt);
        }
    }

    private class Sent2MessageHolder extends RecyclerView.ViewHolder{
        TextView messageTxt, sent_time_txt;
        public Sent2MessageHolder(@NonNull View itemView) {
            super(itemView);

            messageTxt=itemView.findViewById(R.id.sent2Message);
            sent_time_txt=itemView.findViewById(R.id.sent2_time_txt);
        }
    }


    private class ReceivedMessageHolder extends RecyclerView.ViewHolder{
        TextView nameTxt,receivedTxt,receive_time_txt;
        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt=itemView.findViewById(R.id.nameTxt);
            receivedTxt=itemView.findViewById(R.id.receiveMessage);
            receive_time_txt=itemView.findViewById(R.id.receive_time_txt);
        }
    }

    private class Received2MessageHolder extends RecyclerView.ViewHolder{
        TextView receivedTxt,receive2_time_txt;
        public Received2MessageHolder(@NonNull View itemView) {
            super(itemView);

            receivedTxt=itemView.findViewById(R.id.receiveMessage2);
            receive2_time_txt=itemView.findViewById(R.id.receive2_time_txt);
        }
    }

    @Override
    public int getItemViewType(int position) {
        JSONObject message=messages.get(position);
        try {
            if(message.getInt("isSent")==0)
                return TYPE_SENT;
            else if(message.getInt("isSent")==1){
                return TYPE_RECEIVED;
            }else if(message.getInt("isSent")==2){
                return TYPE_RECEIVED2;
            }else if(message.getInt("isSent")==3){
                return TYPE_SENT2;
            }else if(message.getInt("isSent")==4){
                return TYPE_ENTER;
            }else{
                return TYPE_EXIT;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void addItem(JSONObject jsonObject,int position){
        messages.add(jsonObject);
        notifyItemChanged(position);
    }
    public void setItems(List<JSONObject> messages){
        this.messages=messages;
        notifyDataSetChanged();
    }

    public boolean getContinue(String name) throws JSONException {
        if(messages.size()>0
         && (messages.get(messages.size()-1).getInt("isSent")==1||messages.get(messages.size()-1).getInt("isSent")==2))
        {
            if(messages.get(messages.size()-1).getString("name").equals(name)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean getContinue2() throws JSONException {
        if(messages.size()>0){
            if(messages.get(messages.size()-1).getInt("isSent")==0 || messages.get(messages.size()-1).getInt("isSent")==3){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
