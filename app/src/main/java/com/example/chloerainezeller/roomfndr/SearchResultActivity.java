package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SearchResultActivity extends AppCompatActivity {

    private Context myContext;
    private TextView roomNumber;
    private ImageButton goBackButton;
    private ImageButton shareRoomButton;
    private ImageButton emailOSLButton;
    private ImageButton addToFavoritesButton;
    DatabaseHelper myDb;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.search_result_activity);
        myContext = this;
        myDb = new DatabaseHelper(this);

        roomNumber = findViewById(R.id.roomNumber);
        goBackButton = findViewById(R.id.backButtonSearchResult);
        shareRoomButton = findViewById(R.id.shareThisRoomButton);
        emailOSLButton = findViewById(R.id.emailOSLButton);
        addToFavoritesButton = findViewById(R.id.addFavoriteButton);

        addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final String roomName = this.getIntent().getExtras().getString("roomName");
        roomNumber.setText(roomName);

        shareRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                String prompt = "Hey! I'm in " + roomName;
                sendIntent.putExtra("sms_body", prompt);
                startActivity(sendIntent);

            }
        });

    }

    // back button to the search activity
    public void goToSearch(View view) {
        Intent intent = new Intent(myContext, SearchActivity.class);
        startActivity(intent);
    }

    //FA
}
