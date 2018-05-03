package com.example.chloerainezeller.roomfndr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
<<<<<<<
import android.widget.TextView;
=======
import android.widget.Toast;
>>>>>>>

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
                boolean isInserted= myDb.insertData(roomName.getText().toString());
                if (isInserted==true){
                    Toast.makeText(SearchResultActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SearchResultActivity.this,"Data Not Inserted",Toast.LENGTH_SHORT).show();


                }

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

        emailOSLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("vnd.android.cursor.item/email");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"oslpa@oxy.edu"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Email Subject");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "My email content");
                startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
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
