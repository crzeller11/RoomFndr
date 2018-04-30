package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    private Context myContext;

    private Button favoritesButton;
    private ImageButton backButton;

    private EditText buildingEditText;
    private ListView roomListView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        myContext = this;

        final ArrayList<Reservation> dailyReservations = Reservation.getReservationsFromFile(
                "reservations.json", this);
        Calendar cal = Calendar.getInstance();
        String time = "" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
        String[] hour_min = time.split(":");
        final ArrayList<String> availableRooms = findAvailableRooms(dailyReservations, hour_min);


        favoritesButton = findViewById(R.id.favoritesButton);
        backButton = findViewById(R.id.backButtonSearch);
        buildingEditText = findViewById(R.id.buildingEditText);
        roomListView = findViewById(R.id.searchResultsListView);

        // FIXME: Refine by the current time of day

        adapter = new MyAdapter(this, availableRooms);
        roomListView.setAdapter(adapter);

        final EditText filterEditText = (EditText) findViewById(R.id.buildingEditText);

        // Add Text Change Listener to EditText
        filterEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemName = roomListView.getItemAtPosition(position);
                String selectedRoom = itemName.toString();
                Intent detailIntent = new Intent(myContext, SearchResultActivity.class);

                detailIntent.putExtra("roomName", selectedRoom);

                startActivity(detailIntent);
            }
        });



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

    // FIXME: Only based on the rooms available all day long
    public ArrayList<String> findAvailableRooms(ArrayList<Reservation> dailyReservations, String[] timestamp) {
        ArrayList<String> openRooms = new ArrayList<>();
        ArrayList<String> allRooms = allRooms();
        HashMap<String, ArrayList> roomOccupation = roomOccupation(dailyReservations);

        //int curHour = Integer.parseInt(timestamp[0]);
        //int curMinute = Integer.parseInt(timestamp[1]);
        int curHour = 17;
        int curMinute = 0;

        for (String room: allRooms) {
            ArrayList<Reservation> roomReservations = roomOccupation.get(room);
            for (int i = 0; i < roomReservations.size(); i++) {
                Reservation currentReservation = roomReservations.get(i);
                if (!currentReservation.startTime.contains(":") && !currentReservation.endTime.contains(":")) {
                    continue;
                }
                int startMin = Integer.parseInt(currentReservation.startTime.substring(3, 5));
                int endMin = Integer.parseInt(currentReservation.endTime.substring(3,5));
                int startHour;
                int endHour;
                if (currentReservation.startTime.contains("PM") && !currentReservation.startTime.contains("12")) {
                    startHour = Integer.parseInt(currentReservation.startTime.substring(0,2)) + 12;
                } else {
                    startHour = Integer.parseInt(currentReservation.startTime.substring(0, 2));
                }
                if (currentReservation.endTime.contains("PM") && !currentReservation.endTime.contains("12")) {
                    endHour = Integer.parseInt(currentReservation.endTime.substring(0, 2)) + 12;
                } else {
                    endHour = Integer.parseInt(currentReservation.endTime.substring(0, 2));
                }
                if (!isConflict(curHour, curMinute, startHour, startMin, endHour, endMin)) {
                    openRooms.add(room);
                }
            }
        }
        return openRooms;
    }

    public HashMap<String, ArrayList> roomOccupation(ArrayList<Reservation> dailyReservations) {
        HashMap<String, ArrayList> roomOccupationMap = new HashMap<>();
        ArrayList<String> allRooms = allRooms();
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

    public boolean isConflict(int curHour, int curMinute, int startHour, int startMinute, int endHour, int endMinute) {
        // the event starts after the current hour
        if (endHour >= startHour){
            if (startHour <= curHour && curHour <= endHour) {
                return true;
            } return false;

        } else {
            if (startHour <= curHour || curHour <= endHour) {
                return true;
            } return false;
        }

    }

    public ArrayList<String> allRooms() {
        ArrayList<String> allRooms = new ArrayList<>(Arrays.asList(
                "AGYM 21", "BIOS 105", "BIOS 106", "BIOS 111", "BIOS 113", "BIOS 200", "BIOS 210",
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
        return allRooms;
    }

    // Adapter Class
    public class MyAdapter extends BaseAdapter implements Filterable {

        ArrayList<String> arrayList;
        ArrayList<String> mOriginalValues; // Original Values
        LayoutInflater inflater;

        public MyAdapter(Context context, ArrayList<String> arrayList) {
            this.arrayList = arrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            TextView textView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.list_item_room, null);
                holder.textView = convertView.findViewById(R.id.roomListName);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(arrayList.get(position));
            return convertView;
        }



        // TODO: Figure out how to pass the final list of filtered things to onCreate
        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,FilterResults results) {

                    arrayList = (ArrayList<String>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<String> FilteredArrList = new ArrayList<String>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<String>(arrayList); // saves the original data in mOriginalValues
                    }

                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String data = mOriginalValues.get(i);
                            if (data.toLowerCase().startsWith(constraint.toString())) {
                                FilteredArrList.add(data);
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }


            };
            return filter;
        }
    }

}
