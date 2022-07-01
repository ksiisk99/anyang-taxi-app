package com.ay.newchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.newchat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class s8 extends AppCompatActivity {
    r2 r2;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ArrayList<p2> roomlist=new ArrayList<>();
    ArrayList<p2> tmp_roomlist=new ArrayList<>();
    ac account;
    Dialog createroom_dialog;
    Button dialog_exitbtn,participant_minus,participant_plus,createroom_btn,nextbtn,prevbtn;
    TextView participant_screen,prevpage,nextpage;
    TimePicker timePicker;
    int limit_participant=2, hour, minute;
    private String s;
    String room_time;
    Retrofit retrofit;
    i2 i2;
    int prev=1,next=1;
    d d;
    private Long mLastClickTime=0L;
    private boolean flag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s8);

        d = d.getInstance(getApplicationContext());

        createroom_dialog=new Dialog(s8.this);
        createroom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        createroom_dialog.setContentView(R.layout.d1);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        account= ac.getInstance();

        r2 =new r2(getLayoutInflater());
        recyclerView=findViewById(R.id.roomlist_recyclerview);
        recyclerView.setAdapter(r2);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new rp(this));

        s=getString(R.string.h)+getString(R.string.t)+getString(R.string.t)+getString(R.string.p)
                +getString(R.string.line)+getString(R.string.a)+getString(R.string.n)+
                getString(R.string.y)+getString(R.string.a)+getString(R.string.n)+getString(R.string.g)+getString(R.string.s)+
                getString(R.string.t)+getString(R.string.a)+getString(R.string.x)+getString(R.string.i)+
                getString(R.string.c)+getString(R.string.h)+getString(R.string.a)+getString(R.string.t)+
                getString(R.string.cp)+getString(R.string.a)+getString(R.string.f)+getString(R.string.e)+
                getString(R.string.two)+getString(R.string.four)+getString(R.string.a)+getString(R.string.p)+
                getString(R.string.p)+getString(R.string.cp)+getString(R.string.o)+getString(R.string.m);

        nextbtn=findViewById(R.id.nextbtn);
        prevbtn=findViewById(R.id.prevbtn);
        prevpage=findViewById(R.id.prevpage);
        nextpage=findViewById(R.id.nextpage);



        retrofit=new Retrofit.Builder()
                .baseUrl(s)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        i2 =retrofit.create(i2.class);


                Call<ArrayList<p2>> call= i2.getRoomlist();
                call.enqueue(new Callback<ArrayList<p2>>() {
                    @Override
                    public void onResponse(Call<ArrayList<p2>> call, Response<ArrayList<p2>> response) {
                        if(response.isSuccessful()){
                            roomlist=response.body();
                            flag=true;
                            if(roomlist.size()!=0) {
                                next=(roomlist.size()-1)/8+1;
                                nextpage.setText(String.valueOf(next));
                                if(next>=2){
                                    for(int i=0;i<8;i++){
                                        tmp_roomlist.add(roomlist.get(i));
                                    }
                                    r2.setList(tmp_roomlist);
                                }else{
                                    r2.setList(roomlist);
                                }

                            }
                        }else{

                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<p2>> call, Throwable t) {

                        finish();
                    }
                });


        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prev<next){
                    tmp_roomlist.clear();
                    prev++;
                    for(int i=(8*prev-8);i<8*prev && i<roomlist.size();i++){
                        tmp_roomlist.add(roomlist.get(i));
                    }
                    prevpage.setText(String.valueOf(prev));
                    r2.setList(tmp_roomlist);
                }
            }
        });

        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prev>1){
                    tmp_roomlist.clear();
                    prev--;
                    for(int i=(8*prev-8);i<8*prev;i++){
                        tmp_roomlist.add(roomlist.get(i));
                    }
                    prevpage.setText(String.valueOf(prev));
                    r2.setList(tmp_roomlist);
                }
            }
        });

        r2.setOnItemClickListener(new i1() {
            @Override
            public void onItemClick(r2.ra1 holder, View view, int position) {

                if(SystemClock.elapsedRealtime()-mLastClickTime<2000 || flag==false){
                    return;
                }
                mLastClickTime=SystemClock.elapsedRealtime();

                p2 p2 = r2.getItem(position);

                if(account.getRoomid()!=null){
                    if(account.getRoomid().equals(p2.getRoomid())){
                        Intent intent = new Intent(s8.this, s7.class);
                        intent.putExtra(getString(R.string.init), 1);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), getString(R.string.haveroom), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else if(d.getRoomid()!=null){
                    Toast.makeText(getApplicationContext(),getString(R.string.haveroom),Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if (p2.getParticipant() >= p2.getTotal()) { //정원이 꽉참
                        //이걸 굳이 구현할 필요가 있을까? 그냥 서버에서 확인해도 되지 않을까? 일단 패스
                    }
                    account.setCreate(1);
                    account.setRoomid(p2.getRoomid());
                    Intent intent = new Intent(s8.this, s7.class);
                    intent.putExtra(getString(R.string.init), 0);
                    intent.putExtra(getString(R.string.roomcreate),1);
                    account.setTitle(p2.getTitle());
                    account.setTotal(p2.getTotal());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_makeroom:
                if(account.getRoomid()!=null){
                    Toast.makeText(getApplicationContext(),getString(R.string.haveroom),Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(SystemClock.elapsedRealtime()-mLastClickTime<1000){
                    return false;
                }
                mLastClickTime=SystemClock.elapsedRealtime();
                createroom_dialog.show();
                limit_participant=2;
                createroom_btn=createroom_dialog.findViewById(R.id.createroom_btn);
                dialog_exitbtn=createroom_dialog.findViewById(R.id.dialog_exitbtn);
                participant_minus=createroom_dialog.findViewById(R.id.participant_minus);
                participant_plus=createroom_dialog.findViewById(R.id.participant_plus);
                participant_screen=createroom_dialog.findViewById(R.id.participant_screen);
                timePicker=createroom_dialog.findViewById(R.id.timepicker);
                participant_screen.setText("2");

                participant_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(limit_participant-1>=2){
                            limit_participant--;
                            participant_screen.setText(String.valueOf(limit_participant));
                        }
                    }
                });
                participant_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(limit_participant+1<=4){
                            limit_participant++;
                            participant_screen.setText(String.valueOf(limit_participant));
                        }
                    }
                });
                dialog_exitbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createroom_dialog.dismiss();
                    }
                });
                createroom_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(SystemClock.elapsedRealtime()-mLastClickTime<1000){
                            return;
                        }
                        mLastClickTime=SystemClock.elapsedRealtime();

                        room_time="";
                        hour=timePicker.getHour();
                        minute=timePicker.getMinute();
                        if(hour<10){
                            room_time="0";
                        }
                        room_time+=String.valueOf(hour);
                        if(minute<10){
                            room_time+="0";
                        }
                        room_time+=String.valueOf(minute);


                        String tmp=getTime();
                        String tmp_time;

                        if(Integer.parseInt(room_time)<=Integer.parseInt(String.valueOf(tmp.charAt(5))+String.valueOf(tmp.charAt(6))+String.valueOf(tmp.charAt(7))+ String.valueOf(tmp.charAt(8)))){
                            Toast.makeText(getApplicationContext(),getString(R.string.timeset),Toast.LENGTH_SHORT).show();
                        }else{
                            final ri4 body=new ri4();
                            body.setTotal(limit_participant);
                            tmp_time=String.valueOf(tmp.charAt(0))+String.valueOf(tmp.charAt(1))+String.valueOf(tmp.charAt(2))+String.valueOf(tmp.charAt(3))+String.valueOf(tmp.charAt(4))+room_time;
                            body.setTitle(String.valueOf(room_time.charAt(0))+String.valueOf(room_time.charAt(1))+" : "+String.valueOf(room_time.charAt(2))+String.valueOf(room_time.charAt(3)));
                            body.setTime(tmp_time);

                            Call<p1> call= i2.createRoom(body);
                            call.enqueue(new Callback<p1>() {
                                @Override
                                public void onResponse(Call<p1> call, Response<p1> response) {
                                    if(response.isSuccessful()) {
                                        account.setRoomid(response.body().getRoomid());
                                        account.setCreate(response.body().getCreate());
                                        account.setTitle(body.getTitle());
                                        account.setTotal(limit_participant);
                                        Intent intent = new Intent(s8.this, s7.class);
                                        intent.putExtra(getString(R.string.init),0);
                                        //intent.putExtra("total",limit_participant);
                                        //intent.putExtra("roomcreate",1);
                                        startActivity(intent);
                                        createroom_dialog.dismiss();
                                        finish();
                                    }else{

                                    }
                                }

                                @Override
                                public void onFailure(Call<p1> call, Throwable t) {

                                }
                            });
                        }
                    }
                });
                return true;
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private String getTime() {
        long now = System.currentTimeMillis();
        TimeZone tz=TimeZone.getTimeZone(getString(R.string.AsiaSeoul));
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.yyMMddHHmm), Locale.KOREA);
        dateFormat.setTimeZone(tz);
        String getTime = dateFormat.format(date);

        return String.valueOf(getTime.charAt(1)) + String.valueOf(getTime.charAt(3)) + String.valueOf(getTime.charAt(4))
                + String.valueOf(getTime.charAt(6))+ String.valueOf(getTime.charAt(7))+ String.valueOf(getTime.charAt(9))
                + String.valueOf(getTime.charAt(10))+ String.valueOf(getTime.charAt(12))+ String.valueOf(getTime.charAt(13));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLastClickTime=SystemClock.elapsedRealtime();
    }
}