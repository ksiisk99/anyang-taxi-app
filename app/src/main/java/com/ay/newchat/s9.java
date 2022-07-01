package com.ay.newchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.newchat.BuildConfig;
import com.example.newchat.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class s9 extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout linear2,linear3;
    Switch noti_switch;
    d d;
    ac account;
    Dialog logout_dialog,withdrawal_dialog;
    Button cancelbtn,confirmbtn,cancelbtn2,confirmbtn2;
    EditText password_edit;
    private Long mLastClickTime=0L;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s9);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        s=getString(R.string.h)+getString(R.string.t)+getString(R.string.t)+getString(R.string.p)
                +getString(R.string.line)+getString(R.string.a)+getString(R.string.n)+
                getString(R.string.y)+getString(R.string.a)+getString(R.string.n)+getString(R.string.g)+getString(R.string.s)+
                getString(R.string.t)+getString(R.string.a)+getString(R.string.x)+getString(R.string.i)+
                getString(R.string.c)+getString(R.string.h)+getString(R.string.a)+getString(R.string.t)+
                getString(R.string.cp)+getString(R.string.a)+getString(R.string.f)+getString(R.string.e)+
                getString(R.string.two)+getString(R.string.four)+getString(R.string.a)+getString(R.string.p)+
                getString(R.string.p)+getString(R.string.cp)+getString(R.string.o)+getString(R.string.m);


        linear2=findViewById(R.id.linear2);
        linear3=findViewById(R.id.linear3);
        noti_switch=findViewById(R.id.noti_switch);

        logout_dialog=new Dialog(s9.this);
        logout_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logout_dialog.setContentView(R.layout.d2);

        withdrawal_dialog=new Dialog(s9.this);
        withdrawal_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        withdrawal_dialog.setContentView(R.layout.d5);



        d = d.getInstance(getApplicationContext());
        account= ac.getInstance();

        if(account.getNotify()==1){
            noti_switch.setChecked(true);
        }else{
            noti_switch.setChecked(false);
        }

        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdrawal_dialog.show();
                cancelbtn2=withdrawal_dialog.findViewById(R.id.cancelbtn2);
                confirmbtn2=withdrawal_dialog.findViewById(R.id.confirmbtn2);
                password_edit=withdrawal_dialog.findViewById(R.id.password_edit);

                cancelbtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        withdrawal_dialog.dismiss();
                    }
                });

                confirmbtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(account.getRoomid()!=null){
                            Toast.makeText(getApplicationContext(),getString(R.string.kt),Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(SystemClock.elapsedRealtime()-mLastClickTime<3000) {
                            return;
                        }

                        mLastClickTime=SystemClock.elapsedRealtime();
                        String k=c.sha256(password_edit.getText().toString());
                        Retrofit retrofit=new Retrofit.Builder()
                                .baseUrl(s)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        i2 i2 =retrofit.create(i2.class);
                        Call<p8>call= i2.postWithdrawal(account.getName(),k);
                        call.enqueue(new Callback<p8>() {
                            @Override
                            public void onResponse(Call<p8> call, Response<p8> response) {
                                if(response.isSuccessful()){
                                    if(response.body().getSuccess()==1){
                                        d.logout();
                                        withdrawal_dialog_dismiss();
                                    }else{
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(),getString(R.string.kt2),Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<p8> call, Throwable t) {

                            }
                        });

                    }
                });


            }
        });



        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout_dialog.show();
                cancelbtn=logout_dialog.findViewById(R.id.cancelbtn);
                confirmbtn=logout_dialog.findViewById(R.id.confirmbtn);
                cancelbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout_dialog.dismiss();
                    }
                });
                confirmbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(account.getRoomid()!=null){
                            Toast.makeText(getApplicationContext(),getString(R.string.kt),Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(SystemClock.elapsedRealtime()-mLastClickTime<3000){
                            return;
                        }
                        mLastClickTime=SystemClock.elapsedRealtime();
                        Retrofit retrofit=new Retrofit.Builder()
                                .baseUrl(s)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        i2 i2 =retrofit.create(i2.class);
                        Call<p5> call= i2.postLogout(account.getName());
                        call.enqueue(new Callback<p5>() {
                            @Override
                            public void onResponse(Call<p5> call, Response<p5> response) {
                                if(response.isSuccessful()){
                                    if(response.body().getSuccess()==1){//로그아웃 성공
                                        d.logout();
                                        logout_dialog_dismiss();
                                    }else{
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(),"시스템 오류",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<p5> call, Throwable t) {

                            }
                        });
                    }
                });
            }
        });



        noti_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){ //알림 on
                    d.setNotify(1,account.getName());
                    account.setNotify(1);
                }else{ //알림 off
                    d.setNotify(0,account.getName());
                    account.setNotify(0);
                }
            }
        });
    }

    public void logout_dialog_dismiss(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                logout_dialog.dismiss();
                Intent intent=new Intent(s9.this, s1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void withdrawal_dialog_dismiss(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                withdrawal_dialog.dismiss();
                Toast.makeText(getApplicationContext(),getString(R.string.kt3),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(s9.this, s1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
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


}