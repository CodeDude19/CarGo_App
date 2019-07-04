package com.yasser.iteration3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class PassengerDetails extends AppCompatActivity {

    EditText name,phone;
    RadioGroup sex;
    RadioButton male,female,selectedsex;
    Button button;
    private String[] live_data;
    private String Systdate;
    private String Traveldate;
    private String Source;
    private String Destination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_details);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        live_data = (String[]) b.getSerializable("data_array");
        Systdate = (String) b.getString("bookingDate");
        Traveldate = (String) b.getString("travelData");
        Source = (String) b.getString("SourceTravel");
        Destination =(String) b.getString("DestinationTravel");
        // final data now live_data

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        sex = findViewById(R.id.sex);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);

        button = findViewById(R.id.buttonSub1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String Name = name.getText().toString();
                 String Phone = phone.getText().toString();

                if(Name.length() > 0 && Phone.length() > 0&&((male.isChecked()||female.isChecked()))) {
                    Intent i = new Intent(PassengerDetails.this,Receipt.class);
                    int sexid=sex.getCheckedRadioButtonId();
                    selectedsex=findViewById(sexid);
                    String sexy=selectedsex.getText().toString();
                    Bundle passenger = new Bundle();
                    passenger.putSerializable("data_array", live_data);
                    passenger.putString("name", Name);
                    passenger.putString("phone", Phone);
                    passenger.putString("sex", sexy);
                    passenger.putString("bookingDate",Systdate);
                    passenger.putString("travelData",Traveldate);
//                  Toast.makeText(getApplicationContext(),"sysdate = "+Systdate+" \ntravel ="+Traveldate,Toast.LENGTH_LONG).show();
                    passenger.putString("SourceTravel",Source);
                    passenger.putString("DestinationTravel",Destination);
                    i.putExtras(passenger);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please enter all Fields!",Toast.LENGTH_LONG).show();
                }
            }});



    }
}
