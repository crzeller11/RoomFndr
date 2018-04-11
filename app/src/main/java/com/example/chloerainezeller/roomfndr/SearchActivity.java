package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
        *   - Hinchcliffe Hall
        * Then maybe automatically refine the number of floors or whatever given the entry from
        * the first dropdown. So like Fowler goes to all three floors, but the JSC gives all its
        * weird room names. We can build a hashmap from the building to the available floors/rooms
        * */

        ArrayList<String> buildingOptions = new ArrayList<>();

        buildingOptions.add("");
        buildingOptions.add("Fowler");
        buildingOptions.add("Johnson");
        buildingOptions.add("Berkus/Rangeview");
        buildingOptions.add("Booth");
        buildingOptions.add("Hameetman Science Center");
        buildingOptions.add("Johnson Student Center");
        buildingOptions.add("Library");
        buildingOptions.add("Weingart");
        buildingOptions.add("Swan/Hinchcliffe");


        // FIXME: add these options using a HashMap
        ArrayList<String> floorOptions = findFloorOptions();

        ArrayAdapter<String> buildingOptionsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, buildingOptions);

        buildingSpinner.setAdapter(buildingOptionsAdapter);

        // TODO: Configure onItemClick on roomListView
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: package an intent with the room information
                Intent intent = new Intent(myContext, SearchResultActivity.class);
                // FIXME: add shit to the intent, pass to result

                // startActivity(intent);

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

    public ArrayList<String> findFloorOptions() {
        String buildingSelection = buildingSpinner.getSelectedItem().toString();
        HashMap<String, ArrayList<String>> buildingHashmap = createBuildingHashmap();

        ArrayList<String> floorOptions = buildingHashmap.get(buildingSelection);

        return floorOptions;

    }

    public HashMap<String, ArrayList<String>> createBuildingHashmap() {
        // TODO: Create a method that maps from a building to it's subsections, whether that be floors or rooms
        HashMap<String, ArrayList<String>> buildingHashmap = new HashMap<>();
        // FIXME
        return buildingHashmap;
    }
}
