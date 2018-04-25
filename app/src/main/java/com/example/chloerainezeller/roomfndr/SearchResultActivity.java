package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class SearchResultActivity extends AppCompatActivity {

    private Context myContext;
    private ImageButton goBackButton;
    private ImageButton shareRoomButton;
    private ImageButton emailOSLButton;
    private ImageButton addToFavoritesButton;
    DatabaseHelper myDb;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.search_result_activity);
        myContext = this;
        myDb = new DatabaseHelper(this);


        goBackButton = findViewById(R.id.backButtonSearchResult);
        shareRoomButton = findViewById(R.id.shareThisRoomButton);
        emailOSLButton = findViewById(R.id.emailOSLButton);
        addToFavoritesButton = findViewById(R.id.addFavoriteButton);

        addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    // back button to the search activity
    public void goToSearch(View view) {
        Intent intent = new Intent(myContext, SearchActivity.class);
        startActivity(intent);
    }

    //FA
}
