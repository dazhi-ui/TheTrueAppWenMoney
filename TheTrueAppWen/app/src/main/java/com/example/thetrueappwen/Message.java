package com.example.thetrueappwen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class Message implements Serializable {
    public int id;
    public String username,usermoney,userevent,userkind,userdata,usertime,userchoice;
    public void Message(String username,String usermoney,String userevent,String userkind,String userdata,String usertime,String userchoice)
    {
        this.usermoney = usermoney;
        this.userevent = userevent;
        this.username = username;
        this.userchoice = userchoice;
        this.userdata = userdata;
        this.userkind = userkind;
        this.usertime = usertime;
    }

    public void Message(int id,String username,String usermoney,String userevent,String userkind,String userdata,String usertime,String userchoice)
    {
        this.id=id;
        this.usermoney = usermoney;
        this.userevent = userevent;
        this.username = username;
        this.userchoice = userchoice;
        this.userdata = userdata;
        this.userkind = userkind;
        this.usertime = usertime;
    }
    public void setUsermoney(String usermoney) {
        this.usermoney = usermoney;
    }

    public void setUserevent(String userevent) {
        this.userevent = userevent;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserchoice(String userchoice) {
        this.userchoice = userchoice;
    }

    public void setUserdata(String userdata) {
        this.userdata = userdata;
    }

    public void setUserkind(String userkind) {
        this.userkind = userkind;
    }

    public void setUsertime(String usertime) {
        this.usertime = usertime;
    }

    public String getUserevent() {
        return userevent;
    }

    public String getUsermoney() {
        return usermoney;
    }

    public String getUserchoice() {
        return userchoice;
    }

    public String getUsername() {
        return username;
    }

    public String getUserdata() {
        return userdata;
    }

    public String getUserkind() {
        return userkind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsertime() {
        return usertime;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}