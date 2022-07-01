package com.ay.newchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class s5 extends AppCompatActivity {
    private Toolbar toolbar;
    private CountDownTimer timer;
    private TextView textView;
    private long minute,second;
    private String cert_num;
    private int cert_count=0;
    private LinearLayout linear;
    private EditText name_edit, password_edit, password_edit2,email_edit,cert_edit;
    private Button overlap_btn, cert_btn, signup_btn, certcheck_btn;
    private boolean[] check=new boolean[5];
    private String tmp_password;
    private Retrofit retrofit;
    private i2 i2;
    private d d;
    private c c;
    private String s;
    private Long mLastClickTime=0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s5);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textView=findViewById(R.id.timerView);//인증번호 타이머 4분
        linear=findViewById(R.id.linear3);//인증번호layout
        name_edit=findViewById(R.id.name_edit); //닉네임
        password_edit=findViewById(R.id.password_edit); //비번 입력
        password_edit2=findViewById(R.id.password_edit2); //비번 확인
        email_edit=findViewById(R.id.email_edit); //이메일 아이디
        cert_edit=findViewById(R.id.cert_edit); //인증번호 입력
        certcheck_btn=findViewById(R.id.certcheck_btn);


        d = d.getInstance(getApplicationContext());

        s=getString(R.string.h)+getString(R.string.t)+getString(R.string.t)+getString(R.string.p)
                +getString(R.string.line)+getString(R.string.a)+getString(R.string.n)+
                getString(R.string.y)+getString(R.string.a)+getString(R.string.n)+getString(R.string.g)+getString(R.string.s)+
                getString(R.string.t)+getString(R.string.a)+getString(R.string.x)+getString(R.string.i)+
                getString(R.string.c)+getString(R.string.h)+getString(R.string.a)+getString(R.string.t)+
                getString(R.string.cp)+getString(R.string.a)+getString(R.string.f)+getString(R.string.e)+
                getString(R.string.two)+getString(R.string.four)+getString(R.string.a)+getString(R.string.p)+
                getString(R.string.p)+getString(R.string.cp)+getString(R.string.o)+getString(R.string.m);


        overlap_btn=findViewById(R.id.overlap_btn); //중복확인 버튼
        cert_btn=findViewById(R.id.cert_btn); //인증번호 요청버튼
        signup_btn=findViewById(R.id.signup_btn); //가입완료버튼

        retrofit= new Retrofit.Builder()
                .baseUrl(s)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        i2 =retrofit.create(i2.class);

        make_name();
        make_password();
        make_cert();

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check[0] && check[1] && check[2] && check[3]){
                    //fcm 토큰
                    if(SystemClock.elapsedRealtime()-mLastClickTime<2000){
                        return;
                    }
                    mLastClickTime=SystemClock.elapsedRealtime();
                    signup_btn.setEnabled(false);
                    FirebaseMessaging.getInstance().getToken()
                            .addOnCompleteListener(new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull final Task<String> task) {
                                    if(task.isSuccessful()){
                                        final ri6 user=new ri6();
                                        user.setName(name_edit.getText().toString());
                                        user.setDate(getTime());
                                        user.setPassword(getTime2(c.sha256(password_edit.getText().toString()),user.getDate()));
                                        user.setEmail_id(email_edit.getText().toString());
                                        user.setToken(task.getResult());
                                        Call<p7> call= i2.postSignup(user);
                                        call.enqueue(new Callback<p7>() {
                                            @Override
                                            public void onResponse(Call<p7> call, Response<p7> response) {
                                                if(response.isSuccessful()) {
                                                    if(response.body().getSuccess()==1){

                                                        ContentValues contentValues=new ContentValues();
                                                        contentValues.put(getString(R.string.token),user.getToken());
                                                        contentValues.put(getString(R.string.date),user.getDate());
                                                        contentValues.put(getString(R.string.name),user.getName());
                                                        contentValues.putNull(getString(R.string.roomid));
                                                        contentValues.put(getString(R.string.autologin),1);
                                                        contentValues.put(getString(R.string.createroom),0);
                                                        contentValues.put(getString(R.string.notify),1);
                                                        contentValues.putNull(getString(R.string.title));
                                                        contentValues.put(getString(R.string.lastdate),user.getDate());

                                                        d.insertInfo(contentValues);
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(getApplicationContext(),getString(R.string.ct5),Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        Intent intent=new Intent(s5.this, s1.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);
                                                        finish();
                                                    }else{
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(getApplicationContext(),getString(R.string.ct6),Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<p7> call, Throwable t) { }
                                        });
                                    }
                                }
                            });


                }else{
                    Toast.makeText(getApplicationContext(),getString(R.string.ct4),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    //닉네임
    public void make_name(){
        name_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }


            @Override
            public void afterTextChanged(Editable s) {
                check[0]=false;

                name_edit.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_cancel_24,0);
                if(s.toString().trim().length()>1){
                    overlap_btn.setClickable(true);
                }else{
                    overlap_btn.setClickable(false);
                }
            }
        });

        overlap_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime()-mLastClickTime<1000){
                    return;
                }
                mLastClickTime=SystemClock.elapsedRealtime();

                Call<p6> call= i2.overlap(name_edit.getText().toString());
                call.enqueue(new Callback<p6>() {
                    @Override
                    public void onResponse(Call<p6> call, Response<p6> response) {
                        if(response.isSuccessful()) {
                            if (response.body().getCheck() == -1) {//-1이면 중복아님
                                check[0] = true;
                                name_edit.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_check_circle_24,0);
                           }else{ //0이상이면 중복
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        check[0]=false;
                                        Toast.makeText(getApplicationContext(),getString(R.string.overlapname),Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<p6> call, Throwable t) {

                    }
                });
            }
        });
    }

    //비밀번호 설정
    public void make_password(){
        password_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>=6){
                    password_edit.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_check_circle_24,0);
                    check[1]=true;
                    tmp_password=password_edit.getText().toString();
                }else{
                    check[1]=false;
                    password_edit.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_cancel_24,0);
                }
            }
        });

        password_edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>=6 && tmp_password.equals(password_edit2.getText().toString())){
                    check[2]=true;
                    password_edit2.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_check_circle_24,0);
                }else{
                    check[2]=false;
                    password_edit2.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_cancel_24,0);
                }
            }
        });
    }

    public void make_cert(){
        timer=new CountDownTimer(240000,1000) {
            @Override
            public void onTick(long mill) {
                minute=mill/1000/60;
                second=(mill/1000)-(minute*60);
                textView.setText(minute+":"+second);
            }

            @Override
            public void onFinish() {
                cert_num="false";
                cert_count=0;
                cert_edit.setClickable(true);
                linear.setVisibility(View.INVISIBLE);
                textView.setText("");
            }
        };

        //인증번호 요청
        cert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime()-mLastClickTime<1000){
                    return;
                }
                mLastClickTime=SystemClock.elapsedRealtime();
                if(email_edit.getText().length()==0){
                    Toast.makeText(getApplicationContext(),getString(R.string.emailid),Toast.LENGTH_SHORT).show();
                    return;
                }
                cert_btn.setEnabled(false);
                cert_num=getString(R.string.fals);
                email_edit.setEnabled(false);
                check[3]=false;
                email_edit.setBackgroundColor(0);
                Call<p3> call= i2.getCert(email_edit.getText().toString());
                call.enqueue(new Callback<p3>() {
                    @Override
                    public void onResponse(Call<p3> call, Response<p3> response) {
                        if(response.isSuccessful()) {
                            if (!(response.body().getCert().equals(getString(R.string.fals)))) {
                                linear.setVisibility(View.VISIBLE);
                                timer.start();
                                cert_num = response.body().getCert();
                                } else{
                                email_edit.setEnabled(true);
                                cert_btn.setEnabled(true);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), getString(R.string.ct3), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<p3> call, Throwable t) {

                    }
                });

            }
        });

        certcheck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime()-mLastClickTime<1000){
                    return;
                }
                mLastClickTime=SystemClock.elapsedRealtime();
                String k=c.sha256(cert_edit.getText().toString());
                char tmp;
                StringBuilder sb=new StringBuilder(k);
                tmp=sb.charAt(0);
                sb.setCharAt(0,sb.charAt(1));
                sb.setCharAt(1,tmp);
                if(cert_num.equals(sb.toString())){
                    timer.cancel();
                    textView.setText("");
                    check[3]=true;
                    Toast.makeText(getApplicationContext(),getString(R.string.confirm),Toast.LENGTH_SHORT).show();
                    cert_btn.setEnabled(false);
                    linear.setVisibility(View.INVISIBLE);
                    cert_count=0;
                    email_edit.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_check_circle_24,0);
                    }else{
                    cert_count++;
                    if(cert_count==4){
                        cert_btn.setEnabled(true);
                        email_edit.setEnabled(true);
                        check[3]=false;
                        cert_num="false";
                        cert_count=0;
                        linear.setVisibility(View.INVISIBLE);
                        return;
                    }
                    Toast.makeText(getApplicationContext(),getString(R.string.ct)+String.valueOf(cert_count)+
                            getString(R.string.ct2),Toast.LENGTH_SHORT).show();
                }
            }
        });
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