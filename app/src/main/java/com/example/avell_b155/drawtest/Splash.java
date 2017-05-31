package com.example.avell_b155.drawtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by AVELL-B155 on 25/07/2016.
 */
public class Splash extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //tempo de aprensetação do splash
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(Splash.this,PrimeiraTela.class);
                    startActivity(intent);

                    finish();
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
