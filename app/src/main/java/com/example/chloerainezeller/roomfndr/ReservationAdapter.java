package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class ReservationAdapter extends BaseAdapter {
//public class ReservationAdapter extends BaseAdapter implements Filterable {

    private Context myContext;
    private ArrayList<Reservation> myReservationList;
    private LayoutInflater myInflater;

    //private ArrayList<Reservation> originalData;
    //private ArrayList<Reservation> filteredData;

    public ReservationAdapter(Context myContext, ArrayList<Reservation> myReservationList) {
        this.myContext = myContext;
        this.myReservationList = myReservationList;
        myInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //this.originalData = myReservationList;
        //this.filteredData = myReservationList;
    }

    @Override
    public int getCount() { return myReservationList.size(); }

    @Override
    public Object getItem(int position) { return myReservationList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = myInflater.inflate(R.layout.list_item_room, parent, false);
            holder = new ViewHolder();
            holder.roomNumberTextView = convertView.findViewById(R.id.roomListName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TextView roomNumberTextView = holder.roomNumberTextView;

        Reservation reservation = (Reservation) getItem(position);

        roomNumberTextView.setText(reservation.roomAssignment);
        roomNumberTextView.setTextColor(ContextCompat.getColor(myContext, R.color.colorPrimaryDark));

        return convertView;
    }

    private static class ViewHolder {
        public TextView roomNumberTextView;
    }

}
