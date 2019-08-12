package com.yasser.iteration3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TrackRidesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListTrack> listtracks;
    private String username;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_rides);
        pref=getApplicationContext().getSharedPreferences("pref",MODE_PRIVATE);
        username=pref.getString("username",null);
        recyclerView = findViewById(R.id.trackrecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listtracks = new ArrayList<>();
        SQLiteDatabase mydatabase = openOrCreateDatabase("CarBooking.db",MODE_PRIVATE,null);
        String trackQuery = String.format("Select * FROM '%s' ORDER BY date(traveldate) DESC;",username+"track");
        boolean empty = true;
        Cursor cur = mydatabase.rawQuery(trackQuery, null);
        if (cur != null && cur.moveToFirst()) {
            empty = (cur.getInt (0) == 0);
        }
        cur.close();
//        Toast.makeText(getApplicationContext(),empty+"",Toast.LENGTH_LONG).show();
        if(!empty==true){
            Cursor cc = mydatabase.rawQuery(trackQuery,null);
            cc.moveToFirst();
            while(!cc.isAfterLast()){
                String t,b,p,s,ty,stod;
                ty = cc.getString(0);
                s = cc.getString(2);
                b="Booking : "+cc.getString(3);
                t="Travel : "+cc.getString(4);
                p="₹"+cc.getString(5);
                stod = cc.getString(6);
                ListTrack lts = new ListTrack(t,b,s,p,ty,stod);
                listtracks.add(lts);
                cc.moveToNext();
            }
            cc.close();
            adapter = new TrackRidesAdapter(listtracks,this);
            recyclerView.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"YOU HAVE NO RIDES YET!",Toast.LENGTH_LONG).show();
        }

    }

}
