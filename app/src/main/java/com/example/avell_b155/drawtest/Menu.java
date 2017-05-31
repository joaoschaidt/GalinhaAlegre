package com.example.avell_b155.drawtest;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Menu extends Activity {

    private static Menu Menu;
    private static boolean som;
    private AdView mAdView;
    private Handler handler=new Handler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final TextView score=(TextView)findViewById(R.id.score);
        final ImageButton restart=(ImageButton)findViewById(R.id.Restart);
        final ImageButton avaliar=(ImageButton)findViewById(R.id.imageButton2);
        final ImageButton home=(ImageButton)findViewById(R.id.home);
        mAdView = (AdView) findViewById(R.id.adView);

        //propaganda
        final AdRequest adRequest = new AdRequest.Builder().build();
        handler.postDelayed(new Runnable() {
            public void run() {
            mAdView.loadAd(adRequest);
            }
        }, 100);

        Menu=this;
        final MainActivity mainActivity=MainActivity.getInstance();

        //TODO BOTAR SOM DE RELOGIO, COMO DESPERTADOR TOCANDO,QUANDO O MENU APARECER

        //setando pontuação
        score.setText("Pontos: "+  mainActivity.getCont());

        //dando tempo para fechar e recomeçar o jogo
        handler.postDelayed(new Runnable() {
            public void run() {
        restart.setOnClickListener(new View.OnClickListener() {
            public  void onClick(View v) {
                final MediaPlayer mediaPlayer=MediaPlayer.create(Menu.this, R.raw.ligando);
                mediaPlayer.start();
                finish();
                mainActivity.finish();
                MainActivity.getInstance().finish();
                startActivity(new Intent(Menu.this,MainActivity.class));

            }});
            }
        }, 2000);

        handler.postDelayed(new Runnable() {
            public void run() {
        home.setOnClickListener(new View.OnClickListener() {
            public  void onClick(View v) {

                som=mainActivity.getSom();
                mainActivity.finish();
                MainActivity.getInstance().finish();

                startActivity(new Intent(Menu.this,PrimeiraTela.class));

            }});
            }
        }, 2000);

        //tempo para setar a função do botão de avaliar evitando que o usuario clique sem querer
        handler.postDelayed(new Runnable() {
            public void run() {
                avaliar.setOnClickListener(new View.OnClickListener() {
                    public  void onClick(View v) {

                        Uri uri = Uri.parse("market://details?id=" + Menu.this.getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" +  Menu.this.getPackageName())));
                        }


                    }});
            }
        }, 2000);
    }

    //evitando erros caso haja um comportamento inesperado
    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    public static Menu getInstance(){
        return   Menu;
    }

    public boolean getSom() {
        return som;
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}