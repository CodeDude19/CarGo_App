package com.yasser.iteration3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
public class home extends AppCompatActivity {

    //Database
    String []citiess={"Select Source","Gangtok","Siliguri","Bagdogra","NJP","Kalimpong","Darjeeling","Namchi","Pelling","Tsomgo","Zuluk","Jaigaon"};
    //Views
    TextView tv;
    Spinner sp1,sp2;
    Calendar c;
    DatePickerDialog dpd;
    Button im;
    //For Spinner
    ArrayList <String> list1,list2,list3;
    ArrayAdapter<String> aa1,aa2;
    //Passing to other activity
    String source,dest;
    int[] sysdate=new int[3];
    int[] traveldate=new int[3];
    //DatabaseOperations
    private String username,name;
    SharedPreferences pref;
    Button b;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                checkdialog();
                return true;

            case R.id.rides:
//                Toast.makeText(getApplicationContext(),"Wait",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),TrackRidesActivity.class));
//                finish();
                return true;
        }
        return false;
    }

    private void checkdialog() {
        AlertDialog.Builder logout=new AlertDialog.Builder(home.this);
        logout.setMessage("Are You Sure?");
        logout.setCancelable(true);

        logout.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences.Editor ed=pref.edit();
                ed.clear();
                ed.commit();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
        logout.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog a=logout.create();
        a.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toast.makeText(home.this,"Hello!",Toast.LENGTH_LONG).show();
        im=findViewById(R.id.calendar);
        tv=findViewById(R.id.tv);
        b=findViewById(R.id.booknow);
        sp1=findViewById(R.id.sourcespin);
        sp2=findViewById(R.id.destinationspin);
        Intent newi = getIntent();
        //Creating Dynamic Table

//        Toast.makeText(getApplicationContext(),"Successfull!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",Toast.LENGTH_LONG).show();
        list1=new ArrayList<String>();
        list2=new ArrayList<String>();
        list2.add("Select Destination");
        Collections.addAll(list1, citiess);
        pref=getApplicationContext().getSharedPreferences("pref",MODE_PRIVATE);

        username=pref.getString("username",null);
        name=pref.getString("name",null);

        int flag=0;
        for(int i=0;i<name.length();i++)
        {
            if(name.charAt(i)==' ')
                flag++;
        }

        String ActionBartitle;
        if(flag>0)
        {
            int indexofName = name.indexOf(' ');
            ActionBartitle = name.substring(0,indexofName);
        }
        else
        {
            ActionBartitle=name;
        }

        getSupportActionBar().setTitle("Welcome " + ActionBartitle);

        // the following db is for users past order tracker
        SQLiteDatabase mydatabase = openOrCreateDatabase("CarBooking.db",MODE_PRIVATE,null);
        String createtabletrack = String.format("CREATE TABLE IF NOT EXISTS '%s'( vehicletype VARCHAR,vehicleid VARCHAR,seats VARCHAR,bookingdate VARCHAR,traveldate date,price VARCHAR,StoD VARCHAR);",username+"track");
        mydatabase.execSQL(createtabletrack);
        // next table for vehicles tracking of available or not
        String createtableVehicles = "CREATE TABLE IF NOT EXISTS vehicles(vehicleid VARCHAR,type VARCHAR,totseats VARHCAR,time VARCHAR,seatsleft VARCHAR);";
        mydatabase.execSQL(createtableVehicles);
        /// checking if its already been made!
        boolean empty = true;
        Cursor cur = mydatabase.rawQuery("SELECT COUNT(*) FROM vehicles", null);
        if (cur != null && cur.moveToFirst()) {
            empty = (cur.getInt (0) == 0);
        }
        cur.close();
        if(empty==true){
            int sampleDB1[][] = {{1,1,6,9,6},
                                {2,0,4,11,4},
                                {10,0,4,6,4},
                                {3,1,6,10,6},
                                {4,0,4,12,4},
                                {5,1,6,1,6},
                                {6,0,4,3,4},
                                {7,1,6,2,6},
                                {8,0,4,5,4},
                                {9,1,6,4,6}};
            for(int i=0;i<10;i++){
                String temp = "INSERT INTO vehicles VALUES('"+sampleDB1[i][0]+"','"+sampleDB1[i][1]+"','"+sampleDB1[i][2]+"','"+sampleDB1[i][3]+"','"+sampleDB1[i][4]+"' );";
                mydatabase.execSQL(temp);
            }
        }
        aa1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list1);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp1.setAdapter(aa1);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                source=adapterView.getSelectedItem().toString();
                if(source.equals("Gangtok")) {
                    if (list2.size() <= 2) {
                        if (list2.size() == 1) {
                            for (int a = 2; a < citiess.length; a++) {
                                list2.add(citiess[a]);
                            }
                        } else {
                            list2.remove(1);
                            for (int a = 2; a < citiess.length; a++) {
                                list2.add(citiess[a]);
                            }
                        }
                    }
                }
                else if(!source.equals("Select Source"))
                {
                    if(list2.size()==1)
                        list2.add("Gangtok");
//                    else
//                    {
//                        for(int a=1;a<list2.size();a++)
//                        {
//                            list2.remove(a);
//                        }
//                        list2.add("Gangtok");
//                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        aa2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list2);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp2.setAdapter(aa2);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dest=adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c=Calendar.getInstance();
                int day=c.get(Calendar.DAY_OF_MONTH);
                int month=c.get(Calendar.MONTH);
                int year=c.get(Calendar.YEAR);
                sysdate[0]=day;sysdate[1]=month;sysdate[2]=year;

                dpd=new DatePickerDialog(home.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int fyear, int fmonth, int fday) {
                        traveldate[0]=fday;traveldate[1]=fmonth;traveldate[2]=fyear;
                        tv.setText(""+fday+"/"+(fmonth+1)+"/"+fyear);
                    }
                },year,month,day);
                dpd.show();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String systemdat=sysdate[2]+"-";
                String traveldat=traveldate[2]+"-";

                if(sysdate[1]<=9)
                    systemdat+=(("0"+(sysdate[1]+1)+"-"));
                else
                    systemdat+=((sysdate[1]+1)+"-");

                if(sysdate[0]<=9)
                    systemdat+=("0"+sysdate[0]);
                else
                    systemdat+=(sysdate[0]);

                if(traveldate[1]<=9)
                    traveldat+=(("0"+(traveldate[1]+1))+"-");
                else
                    traveldat+=(((traveldate[1]+1))+"-");


                if(traveldate[0]<=9)
                    traveldat+=("0"+traveldate[0]);
                else
                    traveldat+=(traveldate[0]);
//                Toast.makeText(getApplicationContext(),"The fucking Date is : "+systemdat+"\n"+traveldat,Toast.LENGTH_LONG).show();
                if((!source.equals("Select Source"))&&(!dest.equals("Select Destination"))&&(!traveldat.equals("0-01-00"))){
                    Intent i=new Intent(home.this,MainActivity.class);
                    Bundle b=new Bundle();
                    b.putString("source",source);
                    b.putString("dest",dest);
                    b.putString("bookingdate",systemdat);
                    b.putString("traveldate",traveldat);
                    i.putExtras(b);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please fill all Fields!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        moveTaskToBack(true);
    }
}

