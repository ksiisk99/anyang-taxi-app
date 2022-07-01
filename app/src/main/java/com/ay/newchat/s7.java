package com.ay.newchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.newchat.R;

import org.json.JSONException;
import org.json.JSONObject;


import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.OkHttpClient;


public class s7 extends AppCompatActivity {
    private Dialog report_dialog;
    private EditText editText,report_name,report_content,report_time;
    private Button btn,report_cancel,report_send;
    private RecyclerView recyclerView, recyclerView2;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private View drawerView;
    private r1 r1;
    private r3 r3;
    private Socket mSocket;
    private boolean flag=false;
    private boolean match=false;
    private boolean canmessageexit=false;
    private ac account;
    private d d;
    private String time;
    private int init; //앱 시작시 기존 채팅 데이터 set
    private List<JSONObject> data=new ArrayList<>();
    private ArrayList<String> userlist=new ArrayList<>();
    private LinearLayoutManager lm;
    private Long mLastClickTime=0L, mLastClickTime2=0L;
    private String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s7);

        s=getString(R.string.h)+getString(R.string.t)+getString(R.string.t)+getString(R.string.p)
                +getString(R.string.line)+getString(R.string.a)+getString(R.string.n)+
                getString(R.string.y)+getString(R.string.a)+getString(R.string.n)+getString(R.string.g)+getString(R.string.s)+
                getString(R.string.t)+getString(R.string.a)+getString(R.string.x)+getString(R.string.i)+
                getString(R.string.c)+getString(R.string.h)+getString(R.string.a)+getString(R.string.t)+
                getString(R.string.cp)+getString(R.string.a)+getString(R.string.f)+getString(R.string.e)+
                getString(R.string.two)+getString(R.string.four)+getString(R.string.a)+getString(R.string.p)+
                getString(R.string.p)+getString(R.string.cp)+getString(R.string.o)+getString(R.string.m);

        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();//이전 알림 전체 삭제?

        account= ac.getInstance();
        IO.Options options = new IO.Options();
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(0, TimeUnit.MILLISECONDS)
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .writeTimeout(0, TimeUnit.MILLISECONDS);
        options.callFactory = clientBuilder.build();

        report_dialog=new Dialog(s7.this);
        report_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        report_dialog.setContentView(R.layout.d4);
        editText=findViewById(R.id.editText);
        btn=findViewById(R.id.btn);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView2=findViewById(R.id.userlist_recyclerview);
        drawerLayout=findViewById(R.id.drawer_layout);
        drawerView=(View)findViewById(R.id.drawerView);
        drawerLayout.addDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        r1 =new r1(getLayoutInflater());
        r3 =new r3(getLayoutInflater());

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setAdapter(r1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(r3);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        lm=(LinearLayoutManager) recyclerView.getLayoutManager();

        try {
            mSocket=IO.socket(s,options);

            mSocket.on(getString(R.string.message), onNewMessage);//채팅메시지
            mSocket.on(getString(R.string.userenter), onEnterUser);//유저 방 접속
            mSocket.on(getString(R.string.userexit), onExitUser);//유저 방 나가기
            mSocket.on(getString(R.string.userlist),onUserList);
            mSocket.on(getString(R.string.successreport), new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),getString(R.string.mt5),Toast.LENGTH_SHORT).show();
                            report_dialog.dismiss();
                        }
                    });
                }
            });
            mSocket.on(getString(R.string.cantenter),onCantEnter);
            mSocket.on(getString(R.string.onSuccessExit),onSuccessExit);
            mSocket.on(getString(R.string.garbage),onGarbage);
            mSocket.on(getString(R.string.canmessageexit),onCanMessageExit);
        } catch (URISyntaxException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),getString(R.string.mt7),Toast.LENGTH_SHORT).show();
                }
            });
            finish();
            e.printStackTrace();
        }

        d = d.getInstance(getApplicationContext());

        init=getIntent().getExtras().getInt(getString(R.string.init));

        if(init==1){ //기존 데이터 불러오기
            match=true;//방이 있으니 새로운 소켓에 접속 안해도 됨 그래서 match를 true

            if(d.checkData()){ //데이터가 있으면 데이터 불러오기
                try {
                    data= d.setData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                r1.setItems(data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int k= d.getLastpoisiton();
                        if(k>=0) {
                            recyclerView.scrollToPosition(k);
                        }
                        if(data.size()-1>k){
                            Toast.makeText(getApplicationContext(),"여기까지 읽었습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            userlist= d.getUserList(); //초기 유저리스트 가져오기
            if(account.getCreate()==1) {
                getSupportActionBar().setTitle(account.getTitle() + " (" + String.valueOf(userlist.size()) +
                        "/" + String.valueOf(account.getTotal()) + ")");
            }else{
                getSupportActionBar().setTitle(getString(R.string.mt6));
            }
            mSocket.connect();


            //account.setChat_connect(true);//채팅에 연결돼있다는 것을 알려줌
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put(getString(R.string.id),account.getRoomid());
                jsonObject.put(getString(R.string.token),account.getToken());

                mSocket.emit(getString(R.string.keepenter),jsonObject);
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),getString(R.string.mt7),Toast.LENGTH_SHORT).show();
                finish();
                e.printStackTrace();
            }
        }


        if(!match) {
            mSocket.connect();
            /*account.setChat_connect(true);
            dbHelper.setRoomid(account.getRoomid(),account.getName());
            dbHelper.setCreate(account.getCreate(),account.getName());*/
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(getString(R.string.id), account.getRoomid());
                jsonObject.put(getString(R.string.token),account.getToken());
                jsonObject.put(getString(R.string.name),account.getName());
                jsonObject.put(getString(R.string.create),account.getCreate());
                jsonObject.put(getString(R.string.total),account.getTotal());
                jsonObject.put(getString(R.string.roomcreate),getIntent().getExtras().getInt(getString(R.string.roomcreate)));
                jsonObject.put("cdate",getTime2());
                mSocket.emit(getString(R.string.enter), jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            match=true;
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isNetworkConnected()){
                    //네트워크 연결이 안됨
                    Toast.makeText(getApplicationContext(),getString(R.string.mt7),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(canmessageexit==false){ //채팅방에 접속 해놓고 네트워크 연결 꺼놨다가 바로 키고 채팅 보낼려는 경우를 막자
                    return;
                }
                if(SystemClock.elapsedRealtime()-mLastClickTime<400){
                    return;
                }
                mLastClickTime=SystemClock.elapsedRealtime();

                flag=true;
                final JSONObject jsonObject=new JSONObject();
                time=getTime();

                try {
                    jsonObject.put(getString(R.string.name),account.getName());
                    jsonObject.put(getString(R.string.message),editText.getText().toString().trim());
                    jsonObject.put(getString(R.string.time),time);
                    jsonObject.put(getString(R.string.token),account.getToken());
                    jsonObject.put(getString(R.string.id),account.getRoomid());

                    //보낸 채팅데이터 db에 담기
                    final ContentValues values=new ContentValues(); //채팅연결 된 상태에서 백그라운드 됐어도 데이터는 주기
                    values.put(getString(R.string.name),account.getName());
                    values.put(getString(R.string.message),editText.getText().toString().trim());
                    values.put(getString(R.string.time),time);
                    editText.setText(null);

                    //소켓전송
                    mSocket.emit(getString(R.string.message),jsonObject);

                    if(r1.getContinue2()) {
                        jsonObject.put(getString(R.string.isSent), 3);
                        values.put(getString(R.string.isSent),3);
                    }else{
                        jsonObject.put(getString(R.string.isSent), 0);
                        values.put(getString(R.string.isSent),0);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            r1.addItem(jsonObject,r1.getItemCount()-1);
                            d.insertData(values);
                            recyclerView.scrollToPosition(r1.getItemCount()-1);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editText.getText().toString().trim().length()>0 && userlist.size()>1){
                    btn.setEnabled(true);
                }else{
                    btn.setEnabled(false);
                }
            }
        });
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

    private Emitter.Listener onGarbage=new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            d.exitdelete();
            d.logout();
            Log.d("bye", null);
            finish();
        }
    };

    private Emitter.Listener onNewMessage=new Emitter.Listener() {
        @Override
        public void call(final Object... args) { //다른유저에게 채팅받을때
            if(flag){
                flag=false;
                return;
            }
            time=getTime();
            final JSONObject jsonObject=(JSONObject)args[0];

            try {
                ContentValues values=new ContentValues(); //받은 데이터 db에 담기
                Log.d("fucking",jsonObject.getString(getString(R.string.name)));
                values.put(getString(R.string.message),jsonObject.getString(getString(R.string.message)));
                values.put(getString(R.string.name), jsonObject.getString(getString(R.string.name)));
                values.put(getString(R.string.time),time);

                if(r1.getContinue(jsonObject.getString(getString(R.string.name)))){
                    values.put(getString(R.string.isSent), 2);
                    jsonObject.put(getString(R.string.isSent), 2);
                }else{
                    values.put(getString(R.string.isSent), 1);
                    jsonObject.put(getString(R.string.isSent), 1);
                }
                jsonObject.put(getString(R.string.time),time);
                d.insertData(values);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    r1.addItem(jsonObject,r1.getItemCount()-1);
                    if(lm.findLastVisibleItemPosition()==(r1.getItemCount()-2)) {
                        recyclerView.smoothScrollToPosition(r1.getItemCount() - 1);
                    }else{
                        Toast.makeText(getApplicationContext(),"새 메시지가 도착했습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };

    private Emitter.Listener onEnterUser=new Emitter.Listener() {//입장한 유저 추가하기
        @Override
        public void call(Object... args) { //입장한 유저 추가
            JSONObject jsonObject= (JSONObject) args[0];
            final JSONObject data = new JSONObject();

            try {
                ContentValues values=new ContentValues();
                values.put(getString(R.string.message),jsonObject.getString(getString(R.string.name))+getString(R.string.mt));
                values.put(getString(R.string.isSent),4);
                d.insertData(values);
                d.setName(jsonObject.getString(getString(R.string.name)));
                userlist.add(jsonObject.getString(getString(R.string.name)));
                data.put(getString(R.string.message),jsonObject.getString(getString(R.string.name))+ getString(R.string.mt));
                data.put(getString(R.string.isSent),4);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        r1.addItem(data,r1.getItemCount()-1);
                        r3.setItems(userlist);
                        if(lm.findLastVisibleItemPosition()==(r1.getItemCount()-2)) {
                            recyclerView.smoothScrollToPosition(r1.getItemCount() - 1);
                        }
                        if(account.getCreate()==1) {
                            getSupportActionBar().setTitle(account.getTitle() + " (" + String.valueOf(userlist.size()) +
                                    "/" + String.valueOf(account.getTotal()) + ")");
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener onExitUser=new Emitter.Listener() {//퇴장한 유저 제거하기
        @Override
        public void call(Object... args) {
            final JSONObject jsonObject= (JSONObject) args[0];
            final JSONObject data=new JSONObject();
            try {
                ContentValues values=new ContentValues();
                values.put(getString(R.string.message),jsonObject.getString(getString(R.string.name))+getString(R.string.mt2));
                values.put(getString(R.string.isSent),5);
                d.insertData(values);
                d.delName(jsonObject.getString(getString(R.string.name)));
                if(userlist.indexOf(jsonObject.getString(getString(R.string.name)))>-1) {
                    userlist.remove(userlist.indexOf(jsonObject.getString(getString(R.string.name))));
                }
                data.put(getString(R.string.message),jsonObject.getString(getString(R.string.name))+ getString(R.string.mt2));
                data.put(getString(R.string.isSent),5);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        r1.addItem(data,r1.getItemCount()-1);
                        r3.setItems(userlist);
                        if(lm.findLastVisibleItemPosition()==(r1.getItemCount()-2)) {
                            recyclerView.smoothScrollToPosition(r1.getItemCount() - 1);
                        }
                        if(account.getCreate()==1) {
                            getSupportActionBar().setTitle(account.getTitle() + " (" + String.valueOf(userlist.size()) +
                                    "/" + String.valueOf(account.getTotal()) + ")");
                        }
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener onUserList=new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final JSONObject jsonObject=(JSONObject)args[0];
            //account.setChat_connect(true);
            d.setRoomid(account.getRoomid(),account.getName());
            d.setCreate(account.getCreate(),account.getName());
            if(account.getCreate()==1){
                d.setTitle(account.getTitle(),account.getName());
                d.setTotal(account.getTotal(),account.getName());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getSupportActionBar().setTitle(account.getTitle()+" ("+String.valueOf(jsonObject.length())+
                                "/"+String.valueOf(account.getTotal())+")");
                    }
                });
            }else{
                d.setTitle(getString(R.string.mt6),account.getName());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getSupportActionBar().setTitle(getString(R.string.mt6));
                    }
                });
            }
            for(int i=0;i<jsonObject.length();i++){
                try {
                    userlist.add(jsonObject.getString(String.valueOf(i)));
                    d.setName(jsonObject.getString(String.valueOf(i)));
                    canmessageexit=true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    };
    private Emitter.Listener onCantEnter=new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            int cant=(int)args[0];
            if(cant==1){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),getString(R.string.mt3),Toast.LENGTH_SHORT).show();
                    }
                });
                    }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),getString(R.string.mt4),Toast.LENGTH_SHORT).show();
                    }
                });
            }
            account.setRoomid(null);
            account.setCreate(0); //실시간 매칭방인가 생성방인가?

            mSocket.disconnect();
            //account.setChat_connect(false);//현재 채팅이 연결됬는가?
            finish();
        }
    };

    private Emitter.Listener onSuccessExit=new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if((int)args[0]==1){
                d.setRoomid(null,account.getName());
                d.setCreate(0,account.getName());
                userlist.clear();
                account.setRoomid(null);
                //account.setChat_connect(false);
                d.exitdelete();
                finish();
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),getString(R.string.mt7),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

    private Emitter.Listener onCanMessageExit=new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if((int)args[0]==1){canmessageexit=true;
                final int k= d.getLastpoisiton();
                if(k>=0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(k);
                        }
                    });

                }
            }
        }
    };



    private String getTime() {
        long now = System.currentTimeMillis();
        TimeZone tz=TimeZone.getTimeZone(getString(R.string.AsiaSeoul));
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.HHmm), Locale.KOREA);
        dateFormat.setTimeZone(tz);
        String getTime = dateFormat.format(date);
        String hh=String.valueOf(getTime.charAt(0))+String.valueOf(getTime.charAt(1));
        String mm=String.valueOf(getTime.charAt(2))+String.valueOf(getTime.charAt(3));
        if(Integer.parseInt(hh)>=12){
            return "오후 "+hh+":"+mm;
        }else{
            return "오전 "+hh+":"+mm;
        }
    }

    private String getTime2() {
        long now = System.currentTimeMillis();
        TimeZone tz=TimeZone.getTimeZone(getString(R.string.AsiaSeoul));
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.yyMMdd), Locale.KOREA);
        dateFormat.setTimeZone(tz);
        String getTime = dateFormat.format(date);
        return String.valueOf(getTime.charAt(0)) + String.valueOf(getTime.charAt(1)) + String.valueOf(getTime.charAt(3)) + String.valueOf(getTime.charAt(4)) + String.valueOf(getTime.charAt(6)) + String.valueOf(getTime.charAt(7));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_report:
                if(SystemClock.elapsedRealtime()-mLastClickTime<1000 || canmessageexit==false){
                    return false;
                }
                mLastClickTime=SystemClock.elapsedRealtime();
                report_dialog.show();
                report_name=report_dialog.findViewById(R.id.report_name);
                report_time=report_dialog.findViewById(R.id.report_time);
                report_content=report_dialog.findViewById(R.id.report_content);
                report_send=report_dialog.findViewById(R.id.report_send);
                report_cancel=report_dialog.findViewById(R.id.report_cancel);
                report_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(SystemClock.elapsedRealtime()-mLastClickTime<2000){
                            return;
                        }
                        mLastClickTime=SystemClock.elapsedRealtime();
                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put(getString(R.string.reporter),account.getName());
                            jsonObject.put(getString(R.string.name),report_name.getText().toString());
                            jsonObject.put(getString(R.string.time),report_time.getText().toString());
                            jsonObject.put(getString(R.string.content),report_content.getText().toString());
                            jsonObject.put(getString(R.string.room),account.getRoomid());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mSocket.emit(getString(R.string.report),jsonObject);
                    }
                });
                report_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        report_dialog.dismiss();
                    }
                });
                return true;
            case R.id.menu_exit: //방나가기
                if(SystemClock.elapsedRealtime()-mLastClickTime2<2000){
                    return false;
                }
                mLastClickTime2=SystemClock.elapsedRealtime();
                if(canmessageexit==false){
                    return false;
                }else{
                }

                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put(getString(R.string.id),account.getRoomid());
                    jsonObject.put(getString(R.string.token),account.getToken());
                    jsonObject.put(getString(R.string.name),account.getName());
                    jsonObject.put(getString(R.string.create),account.getCreate()); //생성방인지 매칭방인지
                    mSocket.emit(getString(R.string.exit),jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.menu_userlist: //네비게이션 만들자
                if(SystemClock.elapsedRealtime()-mLastClickTime<1000){
                    return false;
                }
                mLastClickTime=SystemClock.elapsedRealtime();
                drawerLayout.openDrawer(drawerView);
                r3.setItems(userlist);
                return true;
        }
        return false;
    }

    DrawerLayout.DrawerListener listener=new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    @Override
    protected void onDestroy() {//채팅서버는 연결이 끊겼지만 방목록엔 남아있어 fcm을 보낼수 있다
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off(getString(R.string.message),onNewMessage);
        mSocket.off(getString(R.string.userenter),onEnterUser);
        mSocket.off(getString(R.string.userexit),onExitUser);
        mSocket.off(getString(R.string.userlist),onUserList);
        mSocket.off(getString(R.string.successreport),onCantEnter);
        mSocket.off(getString(R.string.onSuccessExit),onSuccessExit);
        mSocket.off(getString(R.string.garbage),onGarbage);
    }

    @Override
    protected void onStop() {
        super.onStop();
        d.setLastposition(r1.getItemCount()-1,account.getName());
        //account.setChat_connect(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}