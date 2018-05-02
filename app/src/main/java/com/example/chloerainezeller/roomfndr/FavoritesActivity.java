package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

public class FavoritesActivity extends AppCompatActivity {
    private Context myContext;
    private ListView favoriteRoomsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_activity);
        myContext = this;

        //favoriteRoomsListView = findViewById(R.id.myFavoritesListView);

    }

    public void goBack(View view) {
        Intent intent = new Intent(myContext, SearchActivity.class);
        startActivity(intent);
    }
}
