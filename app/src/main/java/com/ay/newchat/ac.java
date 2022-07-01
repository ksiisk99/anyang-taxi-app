package com.ay.newchat;

import com.google.gson.annotations.SerializedName;

public class ac {

    private static final ac instance=new ac();

    private ac(){}

    public static ac getInstance(){
        return instance;
    }

    @SerializedName("token")
    private String token;
    @SerializedName("roomid")
    private String roomid;
    @SerializedName("name")
    private String name;
    @SerializedName("date")
    private String date;
    @SerializedName("create")
    private int create;

    private boolean connect=false;
    private String title;
    private int notify=0;
    private int total=0;

    public void setTotal(int total){
        this.total=total;
    }

    public int getTotal(){
        return total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNotify() { return notify; }

    public void setNotify(int notify) { this.notify = notify; }


    public int getCreate() {return create; }

    public void setCreate(int create) { this.create = create; }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) { this.date = date; }

    public void setConnect(boolean connect){ this.connect=connect; }

    public String getToken() {
        return token;
    }

    public String getRoomid() {
        return roomid;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public boolean getConnect(){
        return connect;
    }


    @Override
    public String toString(){
        return "StartCheck{"+
                "token='"+token+'\''+
                ", roomid='"+roomid+'\''+
                ",name='"+name+'\''+
                ",date='"+date+'\''+
                '}';
    }

}
