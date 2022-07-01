package com.ay.newchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.newchat.R;

public class pr {

    private Activity activity;
    private AlertDialog alertDialog;


    pr(Activity mActivity){
        activity=mActivity;
    }

    void StartProgress(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.d3, null));

        builder.setCancelable(false);

        alertDialog=builder.create();
        alertDialog.show();
    }

    void dismissDialog(){
        alertDialog.dismiss();
    }
}
