package com.example.chloerainezeller.roomfndr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class dbHelper extends SQLiteOpenHelper{

    // name the table and columns
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FIRSTNAME";

    public dbHelper(Context context){
        // calling the constructor from the parent class
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // creating the table with the columns and their data type
        // SQL statement
        sqLiteDatabase.execSQL("create table " + TABLE_NAME
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRSTNAME TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // i -> old version, i1 -> new version
        // if the table exists, you delete the table and then create a new one
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    // insert row
    // returns true if the data has been successfully inserted into the table
    public boolean insertData(String firstname){

        // get the database you want to insert into
        SQLiteDatabase db = this.getWritableDatabase();

        // get the content values
        ContentValues cv = new ContentValues();
        // put the value into each column
        cv.put(COL_2, firstname);

        // call insert method
        long result = db.insert(TABLE_NAME, null, cv);
        // if it is correct, return true
        return (result != -1);

        // else false

    }

    // update row
    public boolean updateData(String id, String firstname){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1, id);
        cv.put(COL_2, firstname);


        db.update(TABLE_NAME, cv, "ID = ?", new String[]{id});
        return true;
    }
    // show rows

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

}
