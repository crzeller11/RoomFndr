package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class FavoritesActivity extends AppCompatActivity {
    private Context myContext;
    private ListView favoriteRoomsListView;
    private TextView availability;
    private MyAdapter adapter;
    private ImageButton addNewFav;

    dbHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_activity);
        myDb = new dbHelper(this);
        myContext = this;

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0){
            Toast.makeText(FavoritesActivity.this,"No saved favorites.",Toast.LENGTH_SHORT).show();
            System.out.println("No data found");
            return;
        }

        final ArrayList<String> myList = new ArrayList<>();
        while (res.moveToNext()){
            myList.add(res.getString(1 )+"\n");
        }

        final ArrayList<String> idList = new ArrayList<>();
        while(res.moveToNext()){
            idList.add(res.getString(0)+"\n");
        }


        favoriteRoomsListView = findViewById(R.id.myFavoritesListView);
        availability = findViewById(R.id.availabilityListItem);

        adapter = new FavoritesActivity.MyAdapter(this, myList);
        favoriteRoomsListView.setAdapter(adapter);

        addNewFav=findViewById(R.id.addNewFavoriteRoom);
        addNewFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext,FavoriteAddActivity.class);
                startActivity(intent);
            }
        });
    }


    public void goBack(View view) {
        Intent intent = new Intent(myContext, SearchActivity.class);
        startActivity(intent);
    }


    public void addToFavorites(View view) {
        Intent intent = new Intent(myContext, FavoritesActivity.class);
        startActivity(intent);

    }


    public class MyAdapter extends BaseAdapter {
        private Context myContext;
        private ArrayList<String> arrayList;
        private LayoutInflater inflater;

        public MyAdapter(Context context, ArrayList<String> arrayList) {

            this.myContext = context;
            this.arrayList = arrayList;
            inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.favorites_list_item, parent, false);

                holder = new ViewHolder();

                holder.roomTextView = convertView.findViewById(R.id.roomNumberListItem);
                holder.availabilityTextView = convertView.findViewById(R.id.availabilityListItem);

                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
                TextView roomTextView = holder.roomTextView;
                TextView availabilityTextView = holder.availabilityTextView;

                Object curItem = getItem(position);
                String room = curItem.toString();

                roomTextView.setText(room);

                final  ArrayList<Reservation> dailyReservations = Reservation.getReservationsFromFile(
                        "reservations.json", myContext);
                Calendar cal = Calendar.getInstance();
                String time = "" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
                String[] hour_min = time.split(":");
                final  ArrayList<String> availableRooms = SearchActivity.findAvailableRooms(dailyReservations, hour_min);

                boolean occupied = false;

                for (int i = 0; i < availableRooms.size(); i++) {
                    if (availableRooms.get(i).equals(room)) {
                        occupied = true;
                        break;
                    }
                }

                if (occupied) {
                    availabilityTextView.setText("Not Available Now");
                } else {
                    availabilityTextView.setText("Available Now");
                }

            }

            return convertView;
        }

    }


    private static class ViewHolder {
        public TextView roomTextView;
        public TextView availabilityTextView;
    }

}