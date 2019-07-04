package com.yasser.iteration3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt extends AppCompatActivity {

    TextView name1,phone1,source1,destination1,seats1,total1,proceed;
    private String bookingdate,traveldate,source,destination,usernam;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        Intent i11 = getIntent();
        Bundle n = i11.getExtras();
        final String[] dataAgain = (String[]) n.getSerializable("data_array");
        bookingdate = (String) n.getString("bookingDate");
        traveldate = (String) n.getString("travelData");
        source = (String) n.getString("SourceTravel");
        destination = (String) n.getString("DestinationTravel");
        //final data is now dataAgain
        String name = n.getString("name");
        String phone = n.getString("phone");
        final String seatsTaken = dataAgain[5];
        name1 = findViewById(R.id.name1);
        phone1 = findViewById(R.id.phone1);
        source1 = findViewById(R.id.source1);
        destination1 = findViewById(R.id.destination1);
        seats1 = findViewById(R.id.seats1);
        total1 = findViewById(R.id.total1);
        proceed = findViewById(R.id.proceed);


        pref=getApplicationContext().getSharedPreferences("pref",MODE_PRIVATE);
        usernam=pref.getString("username",null);
        name1.setText(name1.getText()+ " "+ name);
        phone1.setText(phone1.getText()+ " "+ phone);
        seats1.setText(seats1.getText()+" "+ seatsTaken);
        source1.setText(source1.getText()+" "+source);
        destination1.setText(destination1.getText()+" "+destination);
        int tot = Integer.parseInt(seatsTaken);
        String s = dataAgain[1].substring(dataAgain[1].length()-3,dataAgain[1].length());
        int tot1 = Integer.parseInt(s);
        final int tot2 = tot*tot1;
        total1.setText(total1.getText() + " " + String.valueOf(tot2));
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf= new SimpleDateFormat(traveldate);
                String date = sdf.format(new Date());
                SQLiteDatabase mydatabase = openOrCreateDatabase("CarBooking.db",MODE_PRIVATE,null);
//                Toast.makeText(getApplicationContext(),"The fucking Date is : "+date,Toast.LENGTH_LONG).show();
                String strr = source+" to "+destination;
                String temp = "INSERT INTO "+usernam+"track VALUES('"+dataAgain[4]+"','"+dataAgain[6]+"','"+seatsTaken+"','"+bookingdate+"','"+date+"','"+tot2+"','"+strr+"');";
                mydatabase.execSQL(temp);
                // Here Update the vehicles table :::::::::
                Cursor cc = mydatabase.rawQuery("Select * FROM  vehicles",null);
                cc.moveToFirst();
                while(!cc.isAfterLast()){
                    if(Integer.parseInt(dataAgain[6]) == Integer.parseInt(cc.getString(0) )){
                        int nano = Integer.parseInt(cc.getString(4));
                        int tempy = nano - Integer.parseInt(seatsTaken);
                        String updater = String.format("UPDATE vehicles set seatsleft = '%s' where vehicleid = '%s';",tempy,dataAgain[6]);
                        mydatabase.execSQL(updater);
                        break;
                    }
                    cc.moveToNext();
                }
                cc.close();
                Intent i = new Intent(getApplicationContext(),Finish_Activity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
