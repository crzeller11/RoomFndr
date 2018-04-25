package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// FRONT PAGE, HAS APP TITLE, AND FIND A ROOM/MAKE ROOM REQUEST BUTTONS
// FINISHED
public class MainActivity extends AppCompatActivity {



    public Context myContext;
    private Button findRoomButton;
    private Button makeRoomRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myContext = this;

        findRoomButton = findViewById(R.id.findRoomButton);
        makeRoomRequestButton = findViewById(R.id.roomRequestButton);


    }

    // onClick method from XML file, button leads to SearchActivity
    public void findRoom(View view) {
        Intent intent = new Intent(myContext, SearchActivity.class);
        startActivity(intent);


    }

    // onClick method from XML file, button leads to implicit intent to launch R25 reservation system
    public void roomRequest(View view) {
        String reservationUrl = "https://mastercalendar.oxy.edu/wv34/wv_servlet/wrd/run/wv_request.Request";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(reservationUrl));
        startActivity(browserIntent);
    }


}
