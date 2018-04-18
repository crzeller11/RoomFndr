package com.example.chloerainezeller.roomfndr;

import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class Reservation {
    public String reservationDescription;
    public String roomAssignment;
    public String reservationTime;

    public String retrieveDocumentFromUrl() {
        /*
        * TODO:
        * Pull the HTML from the webpage
        * Somehow parse it and save it as a JSON file in assets
        * Parse the JSON file for reservations, and save as objects
        * Put the reservation objects in an arrayList
        * */

        // FIXME: Need to generate a timestamp so we can update the URL with the current date

        String title = "";
        // ArrayList of all the reservation objects, append objects once we have parsed HTML
        ArrayList<Reservation> reservationList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://mastercalendar.oxy.edu/wv34/wv_servlet/wrd/run/wv_space.DayList?spdt=20180418,spfilter=8945,lbdviewmode=list").get();
            title = doc.title();
            System.out.println(title);

        } catch(java.io.IOException e) {
            e.printStackTrace();
        }

        return title;

    }




}
