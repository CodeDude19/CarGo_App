package com.yasser.iteration3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;

public class First extends AppCompatActivity {

    ImageView car,wheel;
    TextView c,ar,g;
    SharedPreferences pref;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            pref=getApplicationContext().getSharedPreferences("pref",MODE_PRIVATE);
            if(pref.getString("username",null)!=null)
            {
                finish();
                startActivity(new Intent(First.this,home.class));
                return;
            }else {
                startActivity(new Intent(First.this, Login.class));
                finish();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //handler.postDelayed(runnable,3000);
        wheel=findViewById(R.id.wheel);
        getSupportActionBar().hide();

        car=findViewById(R.id.car);
        wheel=findViewById(R.id.wheel);
        c=findViewById(R.id.C);
        ar=findViewById(R.id.ar);
        g=findViewById(R.id.g);

        Animation zoom= AnimationUtils.loadAnimation(First.this,R.anim.zoomout);
        Animation top=AnimationUtils.loadAnimation(First.this,R.anim.lefttoright);
        car.startAnimation(zoom);
        c.startAnimation(top);
        ar.startAnimation(top);
        g.startAnimation(top);
        wheel.startAnimation(top);
        handler.postDelayed(runnable,1600);
    }
}
