package com.cmpe451.eatalyze.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.gson.Gson;

/**
 * Created by Behiye on 10/8/2016.
 */
public class Utils {
    public static void message(Context context, String title, String text, Runnable runnable){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(text);
//        builder.setCancelable(true);


        if(runnable!=null){
            runnable.run();
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO STH
                }
            });
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public static Object fromGson(String json,Class aClass){
        Gson gson=new Gson();
        return  gson.fromJson(json,aClass);
    }

    public static String toGson(Object o){
        Gson gson=new Gson();
        return gson.toJson(o);

    }
}
