package com.ay.newchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;

import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import io.socket.client.IO;
import io.socket.client.IO.Options;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static okhttp3.OkHttpClient.*;

import com.example.newchat.R;

public class s6 extends AppCompatActivity{
    Button roombtn,matchingbtn,keepbtn,optionbtn;
    private int flag=0;
    private Socket mSocket;

    private d d;
    private ac account;
    private Long mLastClickTime=0L;
    private String s,s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s6);

        d = d.getInstance(getApplicationContext());
        account= ac.getInstance();
        if(getIntent().getIntExtra(getString(R.string.fcm),0)==1 && account.getRoomid()!=null){
            Intent intent=new Intent(s6.this, s7.class);
            intent.putExtra(getString(R.string.init),1);
            startActivity(intent);
            finish();
        }

        s2=getString(R.string.h)+getString(R.string.t)+getString(R.string.t)+getString(R.string.p)
                +getString(R.string.line)+getString(R.string.a)+getString(R.string.n)+
                getString(R.string.y)+getString(R.string.a)+getString(R.string.n)+getString(R.string.g)+getString(R.string.s)+
                getString(R.string.t)+getString(R.string.a)+getString(R.string.x)+getString(R.string.i)+
                getString(R.string.c)+getString(R.string.h)+getString(R.string.a)+getString(R.string.t)+getString(R.string.two)+
                getString(R.string.cp)+getString(R.string.a)+getString(R.string.f)+getString(R.string.e)+
                getString(R.string.two)+getString(R.string.four)+getString(R.string.a)+getString(R.string.p)+
                getString(R.string.p)+getString(R.string.cp)+getString(R.string.o)+getString(R.string.m);



        matchingbtn=findViewById(R.id.matchingbtn);
        keepbtn=findViewById(R.id.keepbtn);
        roombtn=findViewById(R.id.roombtn);
        optionbtn=findViewById(R.id.optionbtn);
        final pr pr =new pr(s6.this);


        Options options = new Options();
        Builder clientBuilder = new Builder()
                .connectTimeout(0, TimeUnit.MILLISECONDS)
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .writeTimeout(0, TimeUnit.MILLISECONDS);
        options.callFactory = clientBuilder.build();

        s=getString(R.string.h)+getString(R.string.t)+getString(R.string.t)+getString(R.string.p)
                +getString(R.string.line)+getString(R.string.a)+getString(R.string.n)+
                getString(R.string.y)+getString(R.string.a)+getString(R.string.n)+getString(R.string.g)+getString(R.string.s)+
                getString(R.string.t)+getString(R.string.a)+getString(R.string.x)+getString(R.string.i)+
                getString(R.string.c)+getString(R.string.h)+getString(R.string.a)+getString(R.string.t)+
                getString(R.string.cp)+getString(R.string.a)+getString(R.string.f)+getString(R.string.e)+
                getString(R.string.two)+getString(R.string.four)+getString(R.string.a)+getString(R.string.p)+
                getString(R.string.p)+getString(R.string.cp)+getString(R.string.o)+getString(R.string.m);


        try {
            mSocket=IO.socket(s2,options);
            mSocket.on(getString(R.string.matching), onMatching);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        keepbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime()-mLastClickTime<1000){
                    return;
                }
                mLastClickTime=SystemClock.elapsedRealtime();
                if(!isNetworkConnected()){
                    //네트워크 연결이 안됨
                    Toast.makeText(getApplicationContext(),getString(R.string.mt7),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(account.getRoomid()!=null) {
                    Intent intent = new Intent(s6.this, s7.class);
                    intent.putExtra(getString(R.string.init), 1);

                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),getString(R.string.noroom),Toast.LENGTH_SHORT).show();
                }
            }
        });



        matchingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime()-mLastClickTime<8000){
                    return;
                }
                mLastClickTime=SystemClock.elapsedRealtime();
                if(account.getRoomid()!=null){
                    Toast.makeText(getApplicationContext(),getString(R.string.haveroom),Toast.LENGTH_LONG).show();
                    return;
                }
                if(!isNetworkConnected()){
                    //네트워크 연결이 안됨
                    Toast.makeText(getApplicationContext(),getString(R.string.mt7),Toast.LENGTH_SHORT).show();
                    return;
                }
                flag=0;
                new Thread(new Runnable() {
                    @Override
                    public void run() { //프로그레스바 화면 띄우자 7초정도 매칭 ㄱ
                        mSocket.connect();
                       //mSocket.on("matching", onMatching);
                        //여기다 프로그레스바
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pr.StartProgress();
                            }
                        });

                        long start,end;
                        start=System.currentTimeMillis();
                        while(true) {

                            if(flag==1){
                                pr.dismissDialog();
                                break;
                            }else if(flag==2){
                                pr.dismissDialog();
                                break;
                            }
                            end=System.currentTimeMillis();
                            if((end-start)/1000.0>=8) {
                                pr.dismissDialog();
                                break;
                            }
                        }

                        if(flag==1){
                            mSocket.disconnect();
                            Intent intent=new Intent(s6.this, s7.class);
                            intent.putExtra(getString(R.string.init),0);
                            startActivity(intent);
                        }else{
                            mSocket.disconnect();
                        }
                    }

                }).start();
            }
        });

        roombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime()-mLastClickTime<1000){
                    return;
                }
                mLastClickTime=SystemClock.elapsedRealtime();
                if(!isNetworkConnected()){
                    //네트워크 연결이 안됨
                    Toast.makeText(getApplicationContext(),getString(R.string.mt7),Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent(s6.this, s8.class);
                startActivity(intent);
            }
        });

        optionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime()-mLastClickTime<1000){
                    return;
                }
                mLastClickTime=SystemClock.elapsedRealtime();
                Intent intent=new Intent(s6.this, s9.class);
                startActivity(intent);
            }
        });


    }
    private Emitter.Listener onMatching=new Emitter.Listener() {//실시간 매칭
        @Override
        public void call(Object... args) {
            String tmp=String.valueOf(args[0]);

            if(!tmp.equals(getString(R.string.fals))) {
                d.setRoomid(tmp,account.getName()); //chat방으로 옮기자 db는
                d.setCreate(0,account.getName());
                account.setRoomid(tmp);
                account.setCreate(0); //실시간 매칭방은 0을 지칭
                flag = 1;
            }
        }
    };

    private boolean isNetworkConnected(){
        ConnectivityManager cm=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder=new NetworkRequest.Builder();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            NetworkCapabilities nc=cm.getNetworkCapabilities(cm.getActiveNetwork());

            if(nc!=null){
                if(nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                    return true;
                }else if(nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            NetworkInfo ni=cm.getActiveNetworkInfo();
            if(ni!=null){
                if(ni.getType()==ConnectivityManager.TYPE_WIFI){
                    return true;
                }else if(ni.getType()==ConnectivityManager.TYPE_MOBILE){
                    return true;
                }else if(ni.getType()==ConnectivityManager.TYPE_WIMAX) {
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    }

    private String getTime() {
        long now = System.currentTimeMillis();
        TimeZone tz=TimeZone.getTimeZone(getString(R.string.AsiaSeoul));
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.yyMMdd), Locale.KOREA);
        dateFormat.setTimeZone(tz);
        String getTime = dateFormat.format(date);
        return String.valueOf(getTime.charAt(0)) + String.valueOf(getTime.charAt(1)) + String.valueOf(getTime.charAt(3)) + String.valueOf(getTime.charAt(4)) + String.valueOf(getTime.charAt(6)) + String.valueOf(getTime.charAt(7));
    }

}