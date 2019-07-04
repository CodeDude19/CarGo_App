package com.yasser.iteration3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CarSelectionAdapter.OnImageClickListener,BottomSheetDialog.BottomSheetListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private int flag = 0;
    private List<ListItem> listItems;
    private String[] finalData;
    private String sdate;
    private String tdate;
    private String Sourcex,Destinationx;
    SharedPreferences seatsl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent x = getIntent();
        Sourcex = x.getStringExtra("source");
        Destinationx = x.getStringExtra("dest");
        tdate = x.getStringExtra("traveldate");
        sdate = x.getStringExtra("bookingdate");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // here i need to extract data from cursor and then make it into a 2d array
                            //vehicleid,type,totseats,time,seatsleft
        String sampleDB1[][] = {{"0","1","6","9","6"},
                                {"1","0","4","11","2"},
                                {"2","0","4","6","4"},
                                {"3","1","6","10","1"},
                                {"4","0","4","12","4"},
                                {"5","1","6","1","5"},
                                {"6","0","4","3","4"},
                                {"7","1","6","2","4"},
                                {"8","0","4","5","2"},
                                {"9","1","6","4","0"}};
        SQLiteDatabase mydatabase = openOrCreateDatabase("CarBooking.db",MODE_PRIVATE,null);
        Cursor cc = mydatabase.rawQuery("Select * FROM  vehicles",null);
        cc.moveToFirst();
        int j = 0;
        while(!cc.isAfterLast()){
            sampleDB1[j][0] = cc.getString(0);
            sampleDB1[j][1] = cc.getString(1);
            sampleDB1[j][2] = cc.getString(2);
            sampleDB1[j][3] = cc.getString(3);
            sampleDB1[j][4] = cc.getString(4);
            j++;
            cc.moveToNext();
        }
        cc.close();
        String sampleDB2[][] = {{"Gangtok","Siliguri","450","700"},
                                {"Gangtok","Bagdogra","500","750"},
                                {"Gangtok","NJP","490","730"},
                                {"Gangtok","Kalimpong","480","800"},
                                {"Gangtok","Darjeeling","500","700"},
                                {"Gangtok","Namchi","400","820"},
                                {"Gangtok","Pelling","600","850"},
                                {"Gangtok","Tsomgo","500","750"},
                                {"Gangtok","Zuluk","550","800"},
                                {"Gangtok","Jaigaon","750","850"}};
        listItems = new ArrayList<>();
        for(int i = 0;i<10;i++){
            if(Integer.parseInt(sampleDB1[i][4])>0){
                int imgid,vehicleid;
                vehicleid = Integer.parseInt(sampleDB1[i][0]);
                String price = "Price : ₹ ",tseats,aseats ="Available Seats : ",time;
                if(sampleDB1[i][1].equals("1")){
                    imgid = 1;
                    if(Sourcex.equals("Gangtok")){
                        for(int k = 0;k<10;k++){
                            if(sampleDB2[k][1].equals(Destinationx)){
                                price +=sampleDB2[k][3];
                            }
                        }
                    }else{
                        for(int k = 0;k<10;k++){
                            if(sampleDB2[k][1].equals(Sourcex)){
                                price +=sampleDB2[k][3];
                            }
                        }
                    }
                }
                else{
                    imgid = 2;
                    if(Sourcex.equals("Gangtok")){
                        for(int k = 0;k<10;k++){
                            if(sampleDB2[k][1].equals(Destinationx)){
                                price+=sampleDB2[k][2];
                            }
                        }
                    }else{
                        for(int k = 0;k<10;k++){
                            if(sampleDB2[k][1].equals(Sourcex)){
                                price+=sampleDB2[k][2];
                            }
                        }
                    }
                }
                time = sampleDB1[i][3];
                if(Integer.parseInt(time)>=8 && Integer.parseInt(time)<12) time = "Time : "+time+":00 AM";
                else time = "Time : "+time+":00 PM";
                aseats += sampleDB1[i][4];
                tseats = sampleDB1[i][2];

                ListItem listItem = new ListItem(time,aseats,price,tseats,imgid,vehicleid);
                listItems.add(listItem);
            }
        }
        adapter = new CarSelectionAdapter(listItems,this,this);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onImageClick(String[] imageData) {
        finalData = imageData;
        String seatsleft = finalData[3];
        seatsl=getApplicationContext().getSharedPreferences("seatsl",MODE_PRIVATE);
        SharedPreferences.Editor ed=seatsl.edit();
        ed.putString("SEATS_LEFT",seatsleft);
        ed.putString("TOTAL_SEATS",finalData[2]);
        ed.commit();
//      Toast.makeText(getApplicationContext(),"You Clicked Vehicle price : "+ data,Toast.LENGTH_LONG).show();
        BottomSheetDialog bottomSheet = new BottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(),"bottom_sheet_layout");
    }

    @Override
    public void onButtonCLicked(String text) {
        finalData[5] = text; // img number of car 1-0
        Intent i = new Intent(MainActivity.this,PassengerDetails.class);
        Bundle b = new Bundle();
        b.putSerializable("data_array",finalData);
        b.putString("bookingDate",sdate);
        b.putString("travelData",tdate);
//        Toast.makeText(getApplicationContext(),"sysdate = "+sdate+" \ntravel ="+tdate,Toast.LENGTH_LONG).show();
        b.putString("SourceTravel",Sourcex);
        b.putString("DestinationTravel",Destinationx);
        i.putExtras(b);
        startActivity(i);
        finish();
    }
}
