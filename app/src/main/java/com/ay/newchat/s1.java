package com.ay.newchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class s1 extends AppCompatActivity {
    private EditText login_id,login_password;
    private Button login_btn1,login_btn2;
    private String token;
    private ac account;
    private d d;
    private i2 i2,i3;
    private Long mLastClickTime=0L;
    private String s,s2;
    private boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        s=getString(R.string.h)+getString(R.string.t)+getString(R.string.t)+getString(R.string.p)
                +getString(R.string.line)+getString(R.string.a)+getString(R.string.n)+
                getString(R.string.y)+getString(R.string.a)+getString(R.string.n)+getString(R.string.g)+getString(R.string.s)+
                getString(R.string.t)+getString(R.string.a)+getString(R.string.x)+getString(R.string.i)+
                getString(R.string.c)+getString(R.string.h)+getString(R.string.a)+getString(R.string.t)+
                getString(R.string.cp)+getString(R.string.a)+getString(R.string.f)+getString(R.string.e)+
                getString(R.string.two)+getString(R.string.four)+getString(R.string.a)+getString(R.string.p)+
                getString(R.string.p)+getString(R.string.cp)+getString(R.string.o)+getString(R.string.m);
        s2=getString(R.string.h)+getString(R.string.t)+getString(R.string.t)+getString(R.string.p)
                +getString(R.string.line)+getString(R.string.a)+getString(R.string.n)+
                getString(R.string.y)+getString(R.string.a)+getString(R.string.n)+getString(R.string.g)+getString(R.string.s)+
                getString(R.string.t)+getString(R.string.a)+getString(R.string.x)+getString(R.string.i)+
                getString(R.string.c)+getString(R.string.h)+getString(R.string.a)+getString(R.string.t)+getString(R.string.two)+
                getString(R.string.cp)+getString(R.string.a)+getString(R.string.f)+getString(R.string.e)+
                getString(R.string.two)+getString(R.string.four)+getString(R.string.a)+getString(R.string.p)+
                getString(R.string.p)+getString(R.string.cp)+getString(R.string.o)+getString(R.string.m);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(s)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        i2 =retrofit.create(i2.class);


        Retrofit retrofit3=new Retrofit.Builder()
                .baseUrl(s2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        i3=retrofit3.create(i2.class);

        if(!isNetworkConnected()){
            //네트워크 연결이 안됨
            Toast.makeText(getApplicationContext(),getString(R.string.networkconnect),Toast.LENGTH_SHORT).show();
            finish();
        }else{
            PackageInfo pi=null;

            try {
                pi=getPackageManager().getPackageInfo(getPackageName(),0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            final int v=pi.versionCode;
            Call<p9> call=i3.postVersion(v);

            call.enqueue(new Callback<p9>() {
                @Override
                public void onResponse(Call<p9> call, Response<p9> response) {
                    if(response.isSuccessful()){

                        if(response.body().getVersion()!= v){

                            Toast.makeText(getApplicationContext(),"업데이트 후 사용 가능합니다.",Toast.LENGTH_SHORT).show();
                            Intent update=new Intent(Intent.ACTION_VIEW, Uri.parse(
                                    "https://play.google.com/store/apps/details?id=com.ay.newchat"));
                            startActivity(update);
                            flag=false;
                        }else{
                            flag=true;
                            getToken(); //여기다 스레드를 걸어야 될 것 같다.
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<p9> call, Throwable t) {

                }
            });

        }
        account = ac.getInstance();


        d = d.getInstance(getApplicationContext());
        setContentView(R.layout.s1); //자동로그인을 먼저하고 뷰를 보여주자


        login_id=findViewById(R.id.login_id);
        login_password=findViewById(R.id.login_password);
        login_btn1=findViewById(R.id.login_btn);
        login_btn2=findViewById(R.id.login_btn2);

        login_btn1.setOnClickListener(new View.OnClickListener() { //아이디 비번 입력 후 로그인 할 경우
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime()-mLastClickTime<3000 || !flag){
                    return;
                }
                mLastClickTime=SystemClock.elapsedRealtime();
                ri3 ri3 =new ri3();
                ri3.setEmail(login_id.getText().toString());
                ri3.setDate(getTime());
                ri3.setPassword(getTime2(c.sha256(login_password.getText().toString()), ri3.getDate()));
                Call<ac> call= i2.getInfo(ri3);
                call.enqueue(new Callback<ac>() {
                    @Override
                    public void onResponse(Call<ac> call, Response<ac> response) {
                        if(response.isSuccessful()) {
                            d.logout();
                            d.exitdelete();
                            if(response.body()==null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),getString(R.string.notinfo),Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }else if(Integer.parseInt(response.body().getDate())>Integer.parseInt(getTime())){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),getString(R.string.stopuser),Toast.LENGTH_SHORT).show();
                                    }
                                });

                                return;
                            }
                            account.setToken(response.body().getToken());
                            account.setName(response.body().getName());
                            account.setRoomid(response.body().getRoomid());
                            account.setDate(response.body().getDate());
                            account.setCreate(response.body().getCreate());
                            account.setNotify(1);


                            if (account.getToken().equals(token)) { //db token과 현 token과 같을 경우 로그인 시킨다.
                                if (Integer.parseInt(getTime()) < Integer.parseInt(account.getDate())) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.stopuser), Toast.LENGTH_SHORT).show();
                                }else{
                                    ContentValues contentValues=new ContentValues();
                                    contentValues.put(getString(R.string.token),response.body().getToken());
                                    contentValues.put(getString(R.string.date),response.body().getDate());
                                    contentValues.put(getString(R.string.name),response.body().getName());
                                    if(response.body().getRoomid()==null){
                                        contentValues.putNull(getString(R.string.roomid));
                                        contentValues.putNull(getString(R.string.title));
                                    }else {
                                        contentValues.put(getString(R.string.roomid), response.body().getRoomid());
                                    }
                                    contentValues.put(getString(R.string.autologin),1);
                                    contentValues.put(getString(R.string.createroom),response.body().getCreate());
                                    contentValues.put(getString(R.string.notify),1);
                                    contentValues.put(getString(R.string.lastdate),getTime());
                                    account.setConnect(true);
                                    d.insertInfo(contentValues);

                                    Intent intent=new Intent(s1.this, s6.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }else { //로그인 했는데 토큰이 바뀐 경우
                                ri2 ri2 =new ri2();
                                ri2.setPrevtoken(account.getToken());
                                ri2.setRoomid(account.getRoomid());
                                ri2.setToken(token);
                                Call<p4> call2 = i2.setToken(ri2);
                                call2.enqueue(new Callback<p4>() {
                                    @Override
                                    public void onResponse(Call<p4> call, Response<p4> response) {
                                        if(response.isSuccessful()){
                                            if(response.body().getSuccess()==1) {
                                                account.setToken(token);
                                                account.setConnect(true);

                                                ContentValues contentValues=new ContentValues();
                                                contentValues.put(getString(R.string.token),token);
                                                contentValues.put(getString(R.string.date),account.getDate());
                                                contentValues.put(getString(R.string.name),account.getName());
                                                if(account.getRoomid()==null) {
                                                    contentValues.putNull(getString(R.string.roomid));
                                                    contentValues.putNull(getString(R.string.title));
                                                }else{
                                                    contentValues.put(getString(R.string.roomid),account.getRoomid());
                                                }
                                                contentValues.put(getString(R.string.autologin),1);
                                                contentValues.put(getString(R.string.createroom),account.getCreate());
                                                contentValues.put(getString(R.string.notify),1);
                                                contentValues.put(getString(R.string.lastdate),getTime());
                                                d.insertInfo(contentValues);

                                                Intent intent=new Intent(s1.this, s6.class);
                                                startActivity(intent);
                                                finish();
                                            }else{

                                            }

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<p4> call, Throwable t) {

                                    }
                                });

                            }

                        }else{ //시스템 오류?
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"시스톔 오류",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<ac> call, Throwable t) {

                    }
                });
            }
        });


        login_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime()-mLastClickTime<1000 || !flag){
                    return;
                }
                mLastClickTime=SystemClock.elapsedRealtime();
                Intent intent=new Intent(s1.this, s2.class);
                startActivity(intent);
            }
        });
    }


    private String getTime() {
        long now = System.currentTimeMillis();
        TimeZone tz=TimeZone.getTimeZone(getString(R.string.AsiaSeoul));
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.yyMMdd), Locale.KOREA);
        dateFormat.setTimeZone(tz);
        String getTim = dateFormat.format(date);
        return String.valueOf(getTim.charAt(0)) + String.valueOf(getTim.charAt(1)) + String.valueOf(getTim.charAt(3)) + String.valueOf(getTim.charAt(4)) + String.valueOf(getTim.charAt(6)) + String.valueOf(getTim.charAt(7));
    }

    private void getToken(){

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if(task.isSuccessful()){
                            token =task.getResult();

                            autologin_system();
                        }
                    }

                });

    }

    private void autologin_system(){
        if(d.getAutologin()==1) { //자동로그인 일 경우
            final JSONObject jsonObject = d.getInfo();
            final String lasttime=getTime();
            //자동로그인은 정지 회원 조건을 줄 필요가 없다. 정지 먹으면 자동 로그인이 되기 때문이다. 인터넷 연결을 안해서 자동 로그인이 안될 경우 최근 접속 날짜가 25일이 넘어가면 db를 초기화한다
            if(Integer.parseInt(d.getLastdate())-Integer.parseInt(lasttime)>25){
                d.logout();
                d.exitdelete();
                return;
            } //최근 접속일이 25일 차이나면 강제 로그아웃 시킨다.

            if(d.getToken()!=null) {
                if (d.getToken().equals(token)) { //db token과 현 token과 같을 경우 로그인 시킨다.
                    if (Integer.parseInt(getTime()) < Integer.parseInt(d.getDate())) {
                        Toast.makeText(getApplicationContext(), getString(R.string.stopuser), Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            account.setToken(jsonObject.getString(getString(R.string.token)));
                            account.setCreate(jsonObject.getInt(getString(R.string.create)));
                            if(jsonObject.isNull(getString(R.string.roomid))){
                                account.setRoomid(null);
                                account.setTitle(null);
                            }else {
                                account.setRoomid(jsonObject.getString(getString(R.string.roomid)));
                                account.setTitle(jsonObject.getString(getString(R.string.title)));
                                account.setTotal(jsonObject.getInt(getString(R.string.total)));
                            }
                            account.setName(jsonObject.getString(getString(R.string.name)));
                            account.setNotify(jsonObject.getInt(getString(R.string.notify)));
                            account.setConnect(true); //포그라운드 상태인가? fcm을 위해
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                        Intent intent = new Intent(s1.this, s6.class);
                        //백그라운드 fcm 알림 클릭시
                        if(getIntent().getIntExtra(getString(R.string.fcm),0)==1 && account.getRoomid()!=null){
                            intent.putExtra(getString(R.string.fcm),1);
                        }
                        d.setLastdate(lasttime,account.getName());
                        startActivity(intent);
                        finish();
                    }
                } else { //자동 로그인인데 token값이 바뀔 경우
                    ri2 ri2 =new ri2();
                    try {
                        ri2.setPrevtoken(jsonObject.getString(getString(R.string.token)));
                        ri2.setRoomid(jsonObject.getString(getString(R.string.roomid)));
                        ri2.setToken(token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Call<p4> call = i2.setToken(ri2);
                    call.enqueue(new Callback<p4>() {
                        @Override
                        public void onResponse(Call<p4> call, Response<p4> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getSuccess()==1) {
                                    try {
                                        account.setToken(token);
                                        account.setCreate(jsonObject.getInt(getString(R.string.create)));
                                        if(jsonObject.isNull(getString(R.string.roomid))){
                                            account.setRoomid(null);
                                            account.setTitle(null);
                                        }else {
                                            account.setRoomid(jsonObject.getString(getString(R.string.roomid)));
                                            account.setTitle(jsonObject.getString(getString(R.string.title)));
                                        }
                                        account.setName(jsonObject.getString(getString(R.string.name)));
                                        account.setConnect(true); //포그라운드 상태인가? fcm을 위해
                                        account.setNotify(jsonObject.getInt(getString(R.string.notify)));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    d.setToken(token, account.getName());
                                    d.setLastdate(lasttime,account.getName());
                                    Intent intent = new Intent(s1.this, s6.class);
                                    startActivity(intent);
                                    finish();
                                } else {

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<p4> call, Throwable t) {

                        }
                    });
                }
            }
        }
    }
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


    private String getTime2(String pass,String date){
        char tmp;
        StringBuilder sb=new StringBuilder(pass);
        for(int i=0;i<3;i++){
            tmp=sb.charAt(date.charAt(i)-'0');
            sb.setCharAt(date.charAt(i)-'0',sb.charAt(date.charAt(5-i)-'0'));
            sb.setCharAt(date.charAt(5-i)-'0',tmp);
        }

        return sb.toString();
    }
}