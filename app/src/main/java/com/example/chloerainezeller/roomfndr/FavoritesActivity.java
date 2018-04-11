package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class FavoritesActivity extends AppCompatActivity {

    private Context myContext;
    private ListView favoriteRoomsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_activity);

        myContext = this;
        favoriteRoomsListView = findViewById(R.id.myFavoritesListView);



    }
}
