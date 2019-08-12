package com.yasser.iteration3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Login extends AppCompatActivity {

    EditText ed1,ed2;
    TextView registration,login;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registration=findViewById(R.id.RegistrationLink);
        login=findViewById(R.id.ButtonTextLogin);
        ed1=findViewById(R.id.UsernameText);
        ed2=findViewById(R.id.PasswordText);
        pref=getApplicationContext().getSharedPreferences("pref",MODE_PRIVATE);

        final SharedPreferences.Editor ed=pref.edit();
        final SQLiteDatabase db=openOrCreateDatabase("cargo.db",MODE_PRIVATE,null);
        String q="CREATE TABLE IF NOT EXISTS users(username varchar primary key,password varchar,name varchar)";
        db.execSQL(q);



        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=ed1.getText().toString();
                String pass=ed2.getText().toString();
                String q;

                q = "Select * from users where username='" + user + "';";

                Cursor c = db.rawQuery(q, null);
                if(c.getCount()==0) {
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                    ed1.setText("");
                    ed2.setText("");
                    return;
                }

                c.moveToFirst();
                if(user.length()==0||pass.length()==0){
                    Toast.makeText(getApplicationContext(),"Enter All The Fields",Toast.LENGTH_LONG).show();
                    ed1.setText("");
                    ed2.setText("");
                    return;
                }

                if(user.equals(c.getString(0))&&pass.equals(c.getString(1)))
                {
                    ed.putString("username",user);
                    ed.putString("name",c.getString(2));
                    ed.commit();
                    c.close();
                    Intent i=new Intent(new Intent(getApplicationContext(),home.class));
                    startActivity(i);
                    finish();
                }

            }
        });
    }
}
