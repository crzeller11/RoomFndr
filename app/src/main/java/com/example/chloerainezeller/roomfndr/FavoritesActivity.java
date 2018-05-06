package com.example.chloerainezeller.roomfndr;

import android.app.Activity;
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


        final  ArrayList<Reservation> dailyReservations = Reservation.getReservationsFromFile(
                "reservations.json", this);
        Calendar cal = Calendar.getInstance();
        String time = "" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
        String[] hour_min = time.split(":");
        final  ArrayList<String> availableRooms = SearchActivity.findAvailableRooms(dailyReservations, hour_min);


        Intent intent=getIntent();
        ArrayList rooms = intent.getStringArrayListExtra("room_list");
        System.out.println(rooms);

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0){
            System.out.println("No data found");
            return;
        }

        final ArrayList<String>myList= new ArrayList<>();
        while (res.moveToNext()){
            myList.add(res.getString(1 )+"\n");
        }

        final ArrayList<String>idList= new ArrayList<>();
        while(res.moveToNext()){
            idList.add(res.getString(0)+"\n");
        }


        favoriteRoomsListView = findViewById(R.id.myFavoritesListView);
        availability=findViewById(R.id.availabilityListItem);

        adapter = new FavoritesActivity.MyAdapter(this,myList);
        favoriteRoomsListView.setAdapter(adapter);

        favoriteRoomsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemName = favoriteRoomsListView.getItemAtPosition(position);
                String selectedRoom = itemName.toString();

                Intent detailIntent = new Intent(myContext, SearchResultActivity.class);

                detailIntent.putExtra("roomName", selectedRoom);

                if (myList.contains(selectedRoom)) {
                detailIntent.putExtra("availability","Available");}
                else{
                detailIntent.putExtra("availability","Not Available");}
                startActivity(detailIntent);
            }
        });


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

        ArrayList<String> arrayList;
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

            FavoritesActivity.MyAdapter.ViewHolder holder = null;

            if (convertView == null) {

                holder = new FavoritesActivity.MyAdapter.ViewHolder();
                convertView = inflater.inflate(R.layout.favorites_list_item, null);
                holder.textView = convertView.findViewById(R.id.roomNumberListItem);
                convertView.setTag(holder);
            } else {
                holder = (FavoritesActivity.MyAdapter.ViewHolder) convertView.getTag();
            }
            holder.textView.setText(arrayList.get(position));
            return convertView;
        }



    }

}