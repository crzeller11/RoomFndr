package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    private Context myContext;
    private ListView favoriteRoomsListView;
    private TextView roomName;
    private Adapter myAdapter;
    private DatabaseHelper myDb;
    ArrayList myList = new ArrayList();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_list_item);
        myContext = this;


        myDb = new DatabaseHelper(this);


        //roomName=findViewById(R.id.roomNumberListItem);

        Cursor todoCursor = myDb.getAllData();
        if (todoCursor.getCount() == 0){
            roomName.setText("No data found.");
            return;
        }
        //StringBuffer buffer = new StringBuffer();
        while (todoCursor.moveToNext()){
            myList.add(todoCursor.getString(2 )+"\n");}

        // TODO: fix null object reference on adapter
        /*
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.favorites_activity,myList);
        favoriteRoomsListView = findViewById(R.id.myFavoritesListView);
        favoriteRoomsListView.setAdapter(adapter);
*/
            System.out.println(myList);
        }

    public void goBack(View view) {
        Intent intent = new Intent(myContext, SearchActivity.class);
        startActivity(intent);}
    }




        //   roomName.setText(myList.toString());










