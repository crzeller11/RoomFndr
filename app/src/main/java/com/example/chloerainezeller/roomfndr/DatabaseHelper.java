package com.example.chloerainezeller.roomfndr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="Favorites.db";
    public static final String TABLE_NAME = "favorites_table";
    public static final int DATABASE_VERSION = 1;

    //column names
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ROOMNAME";




    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS TABLE_NAME( _id INTEGER PRIMARY KEY AUTOINCREMENT, ROOMNAME TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public boolean insertData(String roomname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_2,roomname);
        long result = db.insert(TABLE_NAME, null,cv);
        return (result != -1);
    }


    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor todoCursor =  db.rawQuery("SELECT id as _id, ID, ROOMNAME FROM " + TABLE_NAME, null,null);
        return todoCursor;



    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }




}

