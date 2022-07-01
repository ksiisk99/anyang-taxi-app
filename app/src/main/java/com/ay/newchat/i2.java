package com.ay.newchat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;


class ri1 {
    private String name;
    private String token;
    private String ssaid;
    private String phone;

    public void setName(String name) {
        this.name = name;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setSsaid(String ssaid) {
        this.ssaid = ssaid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

class ri4 {
    private String time;
    private int total;
    private String title;

    public void setTime(String time) {
        this.time = time;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle(){return title;}
}


class ri6 {
    private String name,password,email,token,ssaid,date;

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail_id(String email) {
        this.email = email;
    }

    public void setToken(String token){
        this.token=token;
    }

    public void setSsaid(String ssaid){
        this.ssaid=ssaid;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public String getSsaid() {
        return ssaid;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
}

class ri2 {
    private String prevtoken,token,roomid;

    public void setPrevtoken(String prevtoken) {
        this.prevtoken = prevtoken;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }
}

class ri5 {
    private String name,token,roomid;
    private int create;

    public void setName(String name) {
        this.name = name;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public void setCreate(int create){
        this.create=create;
    }
}

class ri3 {
    private String email,password,date;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDate() {
        return date;
    }
}


public interface i2 {

    @POST("login")
    Call<ac> getInfo(@Body ri3 body);

    @POST("changeToken/")
    Call<p4> setToken(@Body ri2 body);

    @POST("regist")
    Call<ac> postData(@Body ri1 body);

    @POST("roomlist/")
    Call<ArrayList<p2>> getRoomlist();

    @POST("create_room")
    Call<p1> createRoom(@Body ri4 body);

    @POST("overlap")
    Call<p6> overlap(@Query("name") String name);

    @POST("cert/")
    Call<p3> getCert(@Query("email") String email);

    @POST("signup")
    Call<p7> postSignup(@Body ri6 body);

    @POST("logout")
    Call<p5> postLogout(@Query("name") String name);

    @POST("withdrawal")
    Call<p8> postWithdrawal(@Query("name") String name, @Query("password") String password);

    @POST("version")
    Call<p9> postVersion(@Query("version") int version);
}
