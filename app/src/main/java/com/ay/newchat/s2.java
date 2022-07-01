package com.ay.newchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.newchat.R;

public class s2 extends AppCompatActivity {
    LinearLayout linear2,linear3;
    CheckBox tos_btn1,tos_btn2,tos_btn3;
    Button next_btn;
    boolean[] check=new boolean[3];
    Toolbar toolbar;
    private Long mLastClickTime=0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s2);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        linear2=findViewById(R.id.linear2);
        linear3=findViewById(R.id.linear3);
        tos_btn1=findViewById(R.id.tos_btn1);
        tos_btn2=findViewById(R.id.tos_btn2);
        tos_btn3=findViewById(R.id.tos_btn3);
        next_btn=findViewById(R.id.next_btn);

        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(s2.this, s3.class);
                startActivity(intent);
            }
        });

        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(s2.this, s4.class);
                startActivity(intent);
            }
        });

        tos_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!check[1] && !check[2]) {
                    check[1] = true;
                    check[2] = true;
                    tos_btn2.setChecked(true);
                    tos_btn3.setChecked(true);
                    enable_next_btn();
                }else if(!check[1] && check[2]){
                    check[1]=true;
                    tos_btn2.setChecked(true);
                    enable_next_btn();
                }else if(check[1] && !check[2]){
                    check[2]=true;
                    tos_btn3.setChecked(true);
                    enable_next_btn();
                }else if(check[1] && check[2]){
                    check[0]=false;
                    check[1]=false;
                    check[2]=false;
                    tos_btn1.setChecked(false);
                    tos_btn2.setChecked(false);
                    tos_btn3.setChecked(false);
                    next_btn.setEnabled(false);
                }
            }
        });

        tos_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check[1]){
                    check[0]=false;
                    check[1]=false;
                    tos_btn1.setChecked(false);
                    tos_btn2.setChecked(false);
                    next_btn.setEnabled(false);
                }else{
                    check[1]=true;
                    tos_btn2.setChecked(true);
                    enable_next_btn();
                }
            }
        });

        tos_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check[2]){
                    check[0]=false;
                    check[2]=false;
                    tos_btn1.setChecked(false);
                    tos_btn3.setChecked(false);
                    next_btn.setEnabled(false);
                    enable_next_btn();
                }else{
                    check[2]=true;
                    tos_btn3.setChecked(true);
                    enable_next_btn();
                }

            }
        });
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime()-mLastClickTime<1000){
                    return;
                }
                mLastClickTime=SystemClock.elapsedRealtime();
                Intent intent=new Intent(s2.this, s5.class);
                startActivity(intent);
            }
        });
    }

    private void enable_next_btn(){
        if(check[1]&&check[2]){
            check[0]=true;
            tos_btn1.setChecked(true);
            next_btn.setEnabled(true);
        }
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