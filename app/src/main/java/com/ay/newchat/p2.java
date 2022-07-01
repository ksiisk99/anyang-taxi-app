package com.ay.newchat;

import com.google.gson.annotations.SerializedName;

public class p2 {
    @SerializedName("time")
    private int time;
    @SerializedName("roomid")
    private String roomid;
    @SerializedName("total")
    private int total;
    @SerializedName("title")
    private String title;
    @SerializedName("participant")
    private int participant;

    public int getParticipant() {
        return participant;
    }

    public void setParticipant(int participant) {
        this.participant = participant;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitile(String title) {
        this.title = title;
    }
}
