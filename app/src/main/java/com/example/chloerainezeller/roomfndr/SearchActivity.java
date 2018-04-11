package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

public class SearchActivity extends AppCompatActivity {

    private Context myContext;
    private Spinner buildingSpinner;
    private Spinner floorSpinner;
    private Button favoritesButton;
    private Button searchButton;
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
        searchButton = findViewById(R.id.searchButton);
        roomListView = findViewById(R.id.searchResultsListView);

        // TODO: Configure spinner options
        /*
        * Seems like we should only feature some of the buildings on campus in our search:
        *   - Fowler
        *   - Johnson
        *   - Berkus/Rangeview (stored under both names of fucking course)
        *   - Booth
        *   - Hameetman Science Center
        *   - Johnson Student Center
        *   - Library
        *   - Weingart
        * Then maybe automatically refine the number of floors or whatever given the entry from
        * the first dropdown. So like Fowler goes to all three floors, but the JSC gives all its
        * weird room names. We can build a hashmap from the building to the available floors/rooms
        * */



        // TODO: Configure onItemClick on roomListView
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: package an intent with the room information

            }
        });
        // TODO: Configure search button method below


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

    // search button, searches all available rooms
    public void search(View view) {
        // FIXME: populate the list view with the search results, going to need an adapter
    }
}
