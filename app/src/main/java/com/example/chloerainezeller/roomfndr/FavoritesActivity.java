package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    private Context myContext;
    private ListView favoriteRoomsListView;
    private DatabaseHelper myDb;
    private TextView roomName;
    private ArrayList<String>myList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_activity);
        myContext = this;

/*
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.favorites_activity,myList);
        favoriteRoomsListView = findViewById(R.id.myFavoritesListView);
        favoriteRoomsListView.setAdapter(adapter);
*/
    }
    //favoriteRoomsListView = findViewById(R.id.myFavoritesListView);
    public void goBack(View view) {
        Intent intent = new Intent(myContext, SearchActivity.class);
        startActivity(intent);
    }


    public void addToFavorites(View view) {
        Intent intent = new Intent(myContext, FavoritesActivity.class);
        startActivity(intent);

        Cursor todoCursor = myDb.getAllData();
        if (todoCursor.getCount() == 0){
            roomName.setText("No data found.");
            return;
        }
        //StringBuffer buffer = new StringBuffer();
        while (todoCursor.moveToNext()){
            myList.add(todoCursor.getString(1 )+"\n");}
        roomName.setText(myList.toString());
    }
}