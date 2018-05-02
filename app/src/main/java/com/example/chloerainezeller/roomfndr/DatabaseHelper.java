package com.example.chloerainezeller.roomfndr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alliebrenner on 4/23/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="Favorites.db";
    public static final String TABLE_NAME = "favorites_table";

    //column names
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ROOMNAME";
    public static final String COL_3 = "BUILDING";
    public static final String COL_4 = "ROOMNUMBER";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null,1);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create table " +TABLE_NAME
        +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, ROOMNAME TEXT, BUILDING TEXT, ROOMNUMBER INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public boolean insertData(String roomname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();

        cv.put(COL_2,roomname);

        long result = db.insert(TABLE_NAME, null,cv);
        return (result!=-1);
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_NAME, null);
        return res;
    }


}
