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
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// ACTIVITY THAT LETS USERS SEARCH A ROOM, AND ADD IT TO THEIR ACTIVITIES
public class FavoriteAddActivity extends AppCompatActivity {
    private Context myContext;
    private EditText favoritesSearch;
    private ImageButton backButton;
    private ListView roomListView;
    private MyAdapter adapter;

    dbHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_favorites_activity);
        myContext = this;
        myDb = new dbHelper(this);
        roomListView = findViewById(R.id.favoritesListView);
        backButton = findViewById(R.id.imageButtonGoBackFavs);

        ArrayList<String> allRooms = SearchActivity.allRooms();

        adapter = new FavoriteAddActivity.MyAdapter(this, allRooms);
        roomListView.setAdapter(adapter);



        final EditText filterEditText = findViewById(R.id.editTextFavorites);

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
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object itemName = roomListView.getItemAtPosition(position);
                String selectedRoom = itemName.toString();
                boolean isInserted = myDb.insertData(selectedRoom);
                if (isInserted) {
                    Toast.makeText(FavoriteAddActivity.this, "Saved to favorites!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FavoriteAddActivity.this, "Not saved to favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

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

            FavoriteAddActivity.MyAdapter.ViewHolder holder = null;

            if (convertView == null) {

                holder = new FavoriteAddActivity.MyAdapter.ViewHolder();
                convertView = inflater.inflate(R.layout.list_item_room, null);
                holder.textView = convertView.findViewById(R.id.roomListName);
                convertView.setTag(holder);
            } else {
                holder = (FavoriteAddActivity.MyAdapter.ViewHolder) convertView.getTag();
            }
            holder.textView.setText(arrayList.get(position));
            return convertView;
        }


        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    arrayList = (ArrayList<String>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    ArrayList<String> FilteredArrList = new ArrayList<String>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<String>(arrayList);
                    }

                    if (constraint == null || constraint.length() == 0) {
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
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
            return filter;
        }
    }

    public void favoritesGoBack(View view) {
        Intent intent = new Intent(myContext, SearchActivity.class);
        startActivity(intent);
    }
}

