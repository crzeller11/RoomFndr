package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class SearchActivity extends AppCompatActivity {

    private Context myContext;

    private Button favoritesButton;
    private ImageButton backButton;

    private EditText buildingEditText;
    private EditText floorEditText;
    private Button searchButton;
    private ListView roomListView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        System.out.println("REACHING THIS ACTIVITY!");

        myContext = this;
        favoritesButton = findViewById(R.id.favoritesButton);
        backButton = findViewById(R.id.backButtonSearch);
        buildingEditText = findViewById(R.id.buildingEditText);
        floorEditText = findViewById(R.id.floorEditText);
        searchButton = findViewById(R.id.searchButton);
        roomListView = findViewById(R.id.searchResultsListView);

        // a list of all the reservations for the day
        ArrayList<Reservation> dailyReservations = Reservation.getReservationsFromFile(
                "reservations.json", this);



        //ArrayList<String> searchResults = findSearchResults();

        // FIXME: Consider an EditText Option to do the search
        //ReservationAdapter adapter = new ResultAdapter(this, searchResults);


        /*
        // TODO: Configure onItemClick on roomListView
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reservation selectedReservation = reservation

                //Intent intent = new Intent(myContext, SearchResultActivity.class);

                // startActivity(intent);

            }
        });

        // TODO: Configure search button method below
        */

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


    public HashMap<String, ArrayList> roomOccupation(ArrayList<Reservation> dailyReservations) {
        HashMap<String, ArrayList> roomOccupationMap = new HashMap<>();
        ArrayList<String> allRooms = new ArrayList<>(Arrays.asList(
                "BIOS 105", "BIOS 106", "BIOS 111", "BIOS 113", "BIOS 200", "BIOS 210",
                "BIOS 309", "BIOS 311", "BOOTH 118", "BOOTH 119", "BOOTH 204", "BOOTH 208",
                "BOOTH 223A", "BOOTH 223B", "BOOTH 224", "BOOTH 226", "FOWLER 108", "FOWLER 110",
                "FOWLER 111", "FOWLER 112", "FOWLER 113", "FOWLER 201", "FOWLER 202", "FOWLER 207",
                "FOWLER 208", "FOWLER 209", "FOWLER 210", "FOWLER 301", "FOWLER 302", "FOWLER 307",
                "FOWLER 309", "FOWLER 310", "FOWLER 311 (MATH REFERENCE ROOM)", "HERRICK LOWER",
                "HERRICK UPPER", "HSC 004", "HSC 015", "HSC 102", "HSC 104", "HSC 124", "HSC 202",
                "HSC 203", "HSC 210", "HSC 225", "JOHN 100", "JOHN 103", "JOHN 104", "JOHN 105",
                "JOHN 106", "JOHN 200", "JOHN 203", "JOHN 301", "JOHN 302", "JOHN 303",
                "JOHN 313 - SKYBOX", "JOHN 314 - SKYBOX", "JOHN 315", "JSC BENGAL ROOM",
                "JSC COMMONS", "JSC GRESHAM", "JSC MORRISON", "JSC SALSBURY", "JSC SALSBURY-YOUNG",
                "JSC STUDENMUND CONFERENCE ROOM", "KECK 208", "KECK 216", "LIB - AHMANSON READING ROOM",
                "LIB 116", "LIB 163", "LIB 220", "LIB 251", "LIB 258 - JEFFERS", "LIB 260", "LIB 3",
                "LIB 355", "LIB BRAUN", "LIB BROWN 219", "LIB BROWN 219 OUTSIDE", "LIB CAE LAB",
                "LIB CUMBERLAND ROOM", "LIB OMAC LAB", "LIB PC1", "MOSHER 1", "MOSHER 2", "MOSHER 3",
                "NORRIS 101", "NORRIS 105", "NORRIS 106", "NORRIS 110", "NORRIS 200", "NORRIS 202",
                "NORRIS 203", "NORRIS 204","PAULEY HALL - MLK LOUNGE", "PAULEY PATIO",
                "PRESIDENT'S MITCHELL GARDEN", "PRESIDENT'S PATIO", "PRESIDENT'S ROSE GDN",
                "R.V. 201", "R.V. 206", "R.V. 217", "RANGEV 232", "RANGEV 242", "RANGEV 243",
                "RANGEV 243B", "RANGEV 244", "SWAN 227", "SWAN N 200", "SWAN SOUTH 119", "SYCAMORE GLEN",
                "THE CHOI AUDITORIUM", "VARELAS INNOVATION LAB", "WEIN 10", "WEIN 110", "WEIN 116",
                "WEIN 117", "WEIN 209", "WEIN 210", "WEIN LAWN", "WEINGART - N. GALLERY RM. 103",
                "WEINGART E. GALLERY RM. 100"));

        for (int i = 0; i < allRooms.size(); i++) {
            String currentRoom = allRooms.get(i);
            ArrayList<Reservation> roomsReservations = new ArrayList<>();
            for (int j = 0; j < dailyReservations.size(); j++) {
                Reservation currentReservation = dailyReservations.get(j);
                if (currentReservation.roomAssignment.equals(currentRoom)) {
                    roomsReservations.add(currentReservation);
                }
            }
            roomOccupationMap.put(currentRoom, roomsReservations);
        }

        return roomOccupationMap;
    }

    /*
    // search button, searches all available rooms
    public void searchReservations(View view) {
        String roomSelection = buildingEditText.getText().toString();
        String floorSelection = floorEditText.getText().toString();

        HashMap<String, String> buildingHashMap = generateBuildingMap();
        // Probably need a hashmap from edit text entries to the building code it has
    

    }
*/


}
