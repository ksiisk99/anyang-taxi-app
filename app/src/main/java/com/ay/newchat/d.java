package com.ay.newchat;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class d extends SQLiteOpenHelper {
    private static int dbVersion=11;
    private static d d =null;
    private static String tbname="Chat_table";
    private static String tbname3="userlist_table";
    private static String tbname4="userinfo_table";
    private static String dbname="ay.db";
    private SQLiteDatabase db;
    private String sql;


    public static synchronized d getInstance(Context context){
        if(d ==null){
            d =new d(context.getApplicationContext());
        }
        return d;
    }

    public d(Context context){
        super(context,dbname,null,dbVersion);
        db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sql="create table if not exists "+tbname+" ("
                +"name text, "
                +"message text, "
                +"time text, "
                +"isSent integer)";

        db.execSQL(sql);


        sql="create table if not exists "+tbname3+"(" //채팅방유지리스트
                +"name text"+")";
        db.execSQL(sql);

        sql="create table if not exists "+tbname4+"("
                +"token text, "
                +"date text, "
                +"name text, "
                +"roomid text, "
                +"autologin integer, "
                +"createroom integer,"
                +"notify integer,"
                +"title text,"
                +"lastdate text,"
                +"total integer," +
                "lastposition integer)";
        db.execSQL(sql);
    }

    public int getTotal(){
        Cursor cursor=db.rawQuery("select * from "+tbname4,null);
        if(cursor.moveToFirst()){
            int total=cursor.getInt(9);
            cursor.close();
            return total;
        }
        cursor.close();
        return 0;
    }

    public int getLastpoisiton(){
        Cursor cursor=db.rawQuery("select * from "+tbname4,null);
        if(cursor.moveToFirst()){
            int lp=cursor.getInt(10);
            cursor.close();
            return lp;
        }
        cursor.close();
        return 0;
    }

    public String getLastdate(){
        Cursor cursor=db.rawQuery("select * from "+tbname4,null);
        if(cursor.moveToFirst()){
            String lastdate=cursor.getString(8);
            cursor.close();
            return lastdate;
        }
        cursor.close();
        return null;
    }

    public int getCreate(){
        Cursor cursor=db.rawQuery("select * from "+tbname4,null);
        if(cursor.moveToFirst()){
            int create=cursor.getInt(5);
            cursor.close();
            return create;
        }
        cursor.close();
        return 0;
    }

    public String getTitle(){
        Cursor cursor=db.rawQuery("select * from "+tbname4,null);
        if(cursor.moveToFirst()){
            String title=cursor.getString(7);
            cursor.close();
            return title;
        }
        cursor.close();
        return null;
    }

    public int getNotify(){
        Cursor cursor=db.rawQuery("select * from "+tbname4,null);
        if(cursor.moveToFirst()){
            int notify=cursor.getInt(6);
            cursor.close();
            return notify;
        }
        cursor.close();
        return 0;
    }

    public String getRoomid(){
        Cursor cursor=db.rawQuery("select * from "+tbname4,null);
        if(cursor.moveToFirst()){
            String roomid=cursor.getString(3);
            cursor.close();
            return roomid;
        }
        cursor.close();
        return null;
    }


    public String getDate(){
        Cursor cursor=db.rawQuery("select * from "+tbname4,null);
        if(cursor.moveToFirst()){
            String date=cursor.getString(1);
            cursor.close();
            return date;
        }
        cursor.close();
        return null;
    }

    public String getToken(){
        Cursor cursor=db.rawQuery("select * from "+tbname4,null);
        if(cursor.moveToFirst()){
            String token=cursor.getString(0);
            cursor.close();
            return token;
        }
        cursor.close();
        return null;
    }

    public String getName(){
        Cursor cursor=db.rawQuery("select * from "+tbname4,null);
        if(cursor.moveToFirst()){
            String name=cursor.getString(2);
            cursor.close();
            return name;
        }
        cursor.close();
        return null;
    }



    public int getAutologin(){
        Cursor cursor=db.rawQuery("select * from "+tbname4,null);
        if(cursor.moveToFirst()){
            int autologin=cursor.getInt(4);
            cursor.close();
            return autologin;
        }
        cursor.close();
        return 0;
    }

    public void setLastposition(int lp,String name){
        ContentValues values=new ContentValues();
        values.put("lastposition",lp);
        db.update(tbname4,values,"name = ?",new String[]{name});
    }

    public void setTitle(String title,String name){
        ContentValues values=new ContentValues();
        values.put("title",title);
        db.update(tbname4,values,"name = ?",new String[]{name});
    }


    public void setNotify(int notify, String name){
        ContentValues values=new ContentValues();
        values.put("notify",notify);
        db.update(tbname4,values,"name = ?",new String[]{name});
    }

    public void setLastdate(String date,String name){
        ContentValues values=new ContentValues();
        values.put("lastdate",date);
        db.update(tbname4,values,"name = ?",new String[]{name});
    }

    public void setTotal(int total,String name){
        ContentValues values=new ContentValues();
        values.put("total",total);
        db.update(tbname4,values,"name = ?",new String[]{name});
    }


    public void setDate(String date, String name){ //정지먹일때 유저가 자동로그인일 경우 fcm을 통해 바꿔준다.
        ContentValues values=new ContentValues();
        values.put("date",date);
        db.update(tbname4,values,"name = ?",new String[]{name});
    }

    public void setToken(String token, String name){
        ContentValues values=new ContentValues();
        values.put("token",token);
        db.update(tbname4,values,"name = ?",new String[]{name});
    }

    public void setRoomid(String roomid, String name){
        ContentValues values=new ContentValues();
        values.put("roomid",roomid);
        db.update(tbname4,values,"name = ?",new String[]{name});
    }

    public void setCreate(int create, String name){
        ContentValues values=new ContentValues();
        values.put("createroom",create);
        db.update(tbname4,values,"name = ?",new String[]{name});
    }


    public void setAutologin(int autologin, String name){
        ContentValues values=new ContentValues();
        values.put("autologin",autologin);
        db.update(tbname4,values,"name = ?",new String[]{name});
    }

    public JSONObject getInfo(){ //회원정보 받기
        Cursor cursor=db.rawQuery("select * from "+tbname4,null);

        if(cursor.moveToFirst()){
            JSONObject jsonObject=new JSONObject();

            try {
                jsonObject.put("token",cursor.getString(0));
                jsonObject.put("name",cursor.getString(2));
                jsonObject.put("roomid",cursor.getString(3));
                jsonObject.put("create",cursor.getInt(5));
                jsonObject.put("notify",cursor.getInt(6));
                jsonObject.put("title",cursor.getString(7));
                if(cursor.getInt(5)==1){
                    jsonObject.put("total",cursor.getInt(9));
                }
                cursor.close();
                return jsonObject;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void insertInfo(ContentValues contentValues){//회원정보 입력
        db.insert(tbname4,null,contentValues);
    }//아이디 비번 입력 후 로그인 할 때

    public void setName(String name){
        ContentValues values=new ContentValues();
        values.put("name",name);
        db.insert(tbname3,null,values);
    }

    public void delName(String name){
        db.delete(tbname3,"name = ?",new String[]{name});
    }

    public void insertData(ContentValues contentValues){ //db에 주고 받은채팅데이터 추가하기
        db.insert(tbname,null,contentValues);
    }


    public boolean checkData(){
        Cursor cursor=db.rawQuery("select * from "+tbname,null);
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    public List<JSONObject> setData() throws JSONException { // 앱 실행시 기존 채팅 데이터 주기
        List<JSONObject> data=new ArrayList<>();
        Cursor cursor;

        cursor=db.rawQuery("select * from "+tbname,null);

        while(cursor.moveToNext()){
            JSONObject item=new JSONObject();

            item.put("name",cursor.getString(0));
            item.put("message",cursor.getString(1));
            item.put("time",cursor.getString(2));
            item.put("isSent",cursor.getInt(3));
            data.add(item);
        }
        cursor.close();
        return data;
    }

    public ArrayList<String> getUserList(){ //userlist 주기
        ArrayList<String> list=new ArrayList<>();
        Cursor cursor;

        cursor=db.rawQuery("select * from "+tbname3,null);
        while(cursor.moveToNext()){
            list.add(cursor.getString(0));

        }
        cursor.close();
        return list;
    }

    public boolean getLastMsg(String name){
        Cursor cursor=db.rawQuery("select * from "+tbname,null);
        if(cursor.moveToLast()){
            if(cursor.getInt(3)==1 ||cursor.getInt(3)==2 ) {
                if (cursor.getString(0).equals(name)) {
                    cursor.close();
                    return true;
                } else {
                    cursor.close();
                    return false;
                }
            }else{
                cursor.close();
                return false;
            }
        }else{
            cursor.close();
            return false;
        }
    }

    public void close(){
        db.close();
    }

    public void exitdelete(){ //방 나가기
        db.execSQL("DELETE FROM "+tbname);
        db.execSQL("DELETE FROM "+tbname3);

    }

    public void logout(){
        db.execSQL("DELETE FROM "+tbname4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(dbVersion>1){
            db.execSQL("DROP TABLE IF EXISTS "+tbname);
            db.execSQL("DROP TABLE IF EXISTS "+tbname3);
            db.execSQL("DROP TABLE IF EXISTS "+tbname4);

            onCreate(db);
        }
    }

}