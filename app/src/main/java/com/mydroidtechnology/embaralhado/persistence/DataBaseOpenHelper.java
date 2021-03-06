package com.mydroidtechnology.embaralhado.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/*
 * Created by Jeferson on 20/11/2016.
 */
class DataBaseOpenHelper extends SQLiteOpenHelper {
    private static final String NAME_DATABASE = "DATABASE";
    private static final int VERSION_DATABASE = 1;
    private static DataBaseOpenHelper MYDATABASECORE = null;


    private DataBaseOpenHelper(Context ctx) {
        super(ctx, NAME_DATABASE, null, VERSION_DATABASE);
    }


    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table words(_id integer primary key autoincrement, name text not null, image BLOB not null, context_id int not null);");
        bd.execSQL("create table scores(_id integer primary key autoincrement, score double not null, image BLOB not null, user text not null, context_id int not null, answer_count double not null, answer_total double not null);");
        bd.execSQL("create table contexts(_id integer primary key autoincrement, name text not null, image BLOB not null, elements text, scores text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
        bd.execSQL("drop table words;");
        bd.execSQL("drop table scores;");
        bd.execSQL("drop table contexts;");
        onCreate(bd);
    }

    static DataBaseOpenHelper getInstanceDataBaseOpenHelper(Context ctx) {
        if (MYDATABASECORE == null) {
            MYDATABASECORE = new DataBaseOpenHelper(ctx.getApplicationContext());
        }
        return MYDATABASECORE;
    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

}
