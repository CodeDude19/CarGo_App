package com.yasser.iteration3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private  EditText name,username,pass,phone,email;
    private  TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.NameText1);
        username=findViewById(R.id.UsernameText1);
        pass=findViewById(R.id.PasswordText1);
        phone=findViewById(R.id.PhoneText1);
        email=findViewById(R.id.EmailText1);
        register=findViewById(R.id.ButtonTextRegister);

        final SQLiteDatabase db=openOrCreateDatabase("cargo.db",MODE_PRIVATE,null);
        String q="CREATE TABLE IF NOT EXISTS users(username varchar primary key,password varchar)";
        db.execSQL(q);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nam=name.getText().toString();
                String unam=username.getText().toString();
                String pas=pass.getText().toString();
                String ph=phone.getText().toString();
                String em=email.getText().toString();

                String q="Select * from users where username='"+unam+"';";
                Cursor c=db.rawQuery(q,null);

                if(nam.isEmpty()||unam.isEmpty()||pas.isEmpty()||ph.isEmpty()||em.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Enter All The Fields",Toast.LENGTH_LONG).show();
                    return;
                }

                if(c.getCount()>0)
                {
                    Toast.makeText(getApplicationContext(),"Username Already Exists",Toast.LENGTH_LONG).show();
                    username.setText("");
                    pass.setText("");
                    return;
                }

                if(ph.length()!=10&&ph.charAt(0)<6)
                {
                    Toast.makeText(getApplicationContext(),"Invalid Phone Number",Toast.LENGTH_LONG).show();
                    phone.setText("");
                    return;
                }

                int flag=0;
                for(int i=0;i<em.length();i++)
                {
                    if(em.charAt(i)=='@')
                        flag++;
                    if(em.charAt(i)=='.')
                        flag++;
                }
                if(flag!=2)
                {
                    Toast.makeText(getApplicationContext(),"Invalid Email",Toast.LENGTH_LONG).show();
                    email.setText("");
                    return;
                }

                q="Insert INTO users values('"+unam+"','"+pas+"','"+nam+"');";
                db.execSQL(q);
                c.close();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });


    }
}

