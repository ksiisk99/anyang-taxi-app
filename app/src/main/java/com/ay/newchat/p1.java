package com.ay.newchat;


import com.google.gson.annotations.SerializedName;

public class p1 {
    @SerializedName("roomid")
    private String roomid;
    @SerializedName("create")
    private int create;

    public int getCreate() {
        return create;
    }

    public void setCreate(int create) {
        this.create = create;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }
}
