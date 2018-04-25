package com.example.chloerainezeller.roomfndr;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class Reservation {
    public String reservationDescription;
    public String roomAssignment;
    public String reservationTime;

    public static ArrayList<Reservation> getReservationsFromFile(String filename, Context context) {
        ArrayList<Reservation> reservationList = new ArrayList<>();

        try {
            String jsonString = loadJsonFromAsset("reservations.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray reservations = json.getJSONArray("reservations");

            for (int i = 0; i < reservations.length(); i++) {
                Reservation reservation = new Reservation();
                reservation.reservationDescription = reservations.getJSONObject(i).getString("eventName");
                reservation.roomAssignment = reservations.getJSONObject(i).getString("room");
                reservation.reservationTime = reservations.getJSONObject(i).getString("time");
                reservationList.add(reservation);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reservationList;
    }

    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }






}
