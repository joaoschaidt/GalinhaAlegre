package com.example.avell_b155.drawtest;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * Created by AVELL-B155 on 06/07/2016.
 */
public class Instruction extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        final TextView Jogar=(TextView)findViewById(R.id.jogar);
        final ImageButton voltar=(ImageButton)findViewById(R.id.voltar);

        //abre a activity principal do jogo
        Jogar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Jogar.setEnabled(false);
                final MediaPlayer mediaPlayer=MediaPlayer.create(Instruction.this, R.raw.ligando);
                mediaPlayer.start();
                startActivity(new Intent(Instruction.this,MainActivity.class));
                finish();

            }

        });
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                voltar.setEnabled(false);//TODO BOTAR UM SOM AQUI
                finish();


            }

        });
        //TODO FAZER COMPATILIBIDADE DE LAYOUTS,TABLETS NAO FICA LEGAL
    }

}
