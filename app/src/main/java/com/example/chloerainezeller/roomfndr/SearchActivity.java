package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

public class SearchActivity extends AppCompatActivity {

    private Context myContext;
    private Spinner buildingSpinner;
    private Spinner floorSpinner;
    private Button favoritesButton;
    private ImageButton backButton;
    private ListView roomListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        myContext = this;
        buildingSpinner = findViewById(R.id.buildingSpinner);
        floorSpinner = findViewById(R.id.floorSpinner);
        favoritesButton = findViewById(R.id.favoritesButton);
        backButton = findViewById(R.id.backButtonSearch);

        // TODO: Configure spinner options
        // TODO: Configure onItemClick
        // TODO: Decide if we want a search button, or to populate search based on spinner clicks


    }

    // goes to Favorites Activity
    public void goToFavorites(View view) {
        Intent intent = new Intent(myContext, FavoritesActivity.class);
        startActivity(intent);

    }

    // back button, goes back to MainActivity
    public void goBack(View view) {
        Intent intent = new Intent(myContext, MainActivity.class);
        startActivity(intent);
    }
}
