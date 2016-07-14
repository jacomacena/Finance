package com.jcdev.finance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.jcdev.finance.Authentication.Login;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread timer = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }

        };

        timer.start();
    }

    @Override
    public void onStop(){
        super.onStop();
        finish();
    }
}
