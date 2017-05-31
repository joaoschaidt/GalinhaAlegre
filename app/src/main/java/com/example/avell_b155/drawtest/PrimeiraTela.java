package com.example.avell_b155.drawtest;



import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;


public class PrimeiraTela extends Activity {

    private Button botaoInstrucoes;
    private Button botaoIniciar;
    private Button botao;
    private Button botao2;
    private Button botao3;
    private Button botao4;
    private Button botao5;
    private Button botao6;
    private Button botao7;
    private Button botao8;
    private Button botao9;
    private Button botao10;
    private ImageButton botao11;
    private Animation rotation;
    private Animation rotationIniciar;
    private SoundPool soundPool;
    private ImageButton lixeira;
    private Vibrator v;
    private boolean som;

    private static PrimeiraTela primeiraTela;
    private boolean loaded;
    private float volume;
    private float actVolume;
    private float maxVolume;
    private AudioManager audioManager;
    private int soundID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeiratela);

        //capturando componentes da tela
        botaoInstrucoes=(Button)findViewById(R.id.Instrucoes);
        botaoIniciar=(Button)findViewById(R.id.button12);
        botao=(Button)findViewById(R.id.button);
        botao2=(Button)findViewById(R.id.button2);
        botao3=(Button)findViewById(R.id.button3);
        botao4=(Button)findViewById(R.id.button4);
        botao5=(Button)findViewById(R.id.button5);
        botao6=(Button)findViewById(R.id.button6);
        botao7=(Button)findViewById(R.id.button7);
        botao8=(Button)findViewById(R.id.button8);
        botao9=(Button)findViewById(R.id.button9);
        botao10=(Button)findViewById(R.id.button10);
        botao11=(ImageButton)findViewById(R.id.button11);
        lixeira=(ImageButton)findViewById(R.id.lixeira);

        //dando possibilidade do usuário mutar o som
        //alterar para shared preferences
        if(Menu.getInstance()!=null){
            Menu menu=Menu.getInstance();
            som=menu.getSom();
            menu.finish();

            if (menu.getSom()==false) {
                botao11.setImageResource(R.drawable.ic_volume_off_white_48dp);
                som=false;
            }else{
                botao11.setImageResource(R.drawable.ic_volume_up_white_48dp);
                som=true;
            }
        }else{
            botao11.setImageResource(R.drawable.ic_volume_up_white_48dp);
            som=true;
        }

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (v.hasVibrator()) {
            Log.v("Can Vibrate", "YES");
        } else {
            Log.v("Can Vibrate", "NO");
        }
        primeiraTela=this;

        final String PREFS_NAME = "MyPrefsFile";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {

            startActivity(new Intent(this,Instruction.class));
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }

        //animação de rotação do botão iniciar
        rotation= AnimationUtils.loadAnimation(this,R.anim.rotate);
        rotationIniciar= AnimationUtils.loadAnimation(this,R.anim.rotate_iniciar);

        botaoIniciar.startAnimation(rotationIniciar);


        lixeira.setScaleY(2f);
        lixeira.setScaleX(2f);

        soundPool=buildSoundPool();

        //setando ação dos botões interativos
        botaoIniciar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { buttonIniciar(); }});
        botaoInstrucoes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { buttonInstrucoes(); }});
        botao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { button1();  }});
        botao2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { button2();  }});
        botao3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { button3();  }});
        botao4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { button4();  }});
        botao5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { button5();  }});
        botao6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { button6();  }});
        botao7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { button7();  }});
        botao8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { button8();  }});
        botao9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { button9();  }});
        botao10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { button10();  }});
        botao11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { button11();  }});
        lixeira.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { lixeiraButton();  }});

    }

    public void buttonIniciar(){
        //abre activity do principal do jogo seguido de um som
        soundID = soundPool.load(this, R.raw.ligando, 1);
        soundPool.play(soundID,(float)0.5,(float)0.5,10,1,(float)1.0);

        v.vibrate(50);
        startActivity(new Intent(this,MainActivity.class));
    }

    public void buttonInstrucoes(){
        //abre activity de instrucoes seguindas de um som de abertura
        soundID = soundPool.load(this,  R.raw.desligando, 1);
        soundPool.play(soundID,(float)0.5,(float)0.5,10,1,(float)1.0);

        botaoInstrucoes.startAnimation(rotation);
        startActivity(new Intent(this,Instruction.class));
        v.vibrate(50);

    }

    public void lixeiraButton(){

        soundID = soundPool.load(this, R.raw.sound_6, 1);
        soundPool.play(soundID,(float)0.5,(float)0.5,10,1,(float)1.0);

        v.vibrate(50);
    }

    public void button1(){
        soundID = soundPool.load(this, R.raw.bip_bip, 1);
        soundPool.play(soundID,(float)0.5,(float)0.5,10,1,(float)1.0);
        botao.startAnimation(rotation);
        v.vibrate(50);
    }
    public void button2(){
        soundID = soundPool.load(this, R.raw.bip, 1);
        soundPool.play(soundID,(float)0.5,(float)0.5,10,1,(float)1.0);
        botao2.startAnimation(rotation);
        v.vibrate(50);

    }
    public void button3(){
        soundID = soundPool.load(this, R.raw.input, 1);
        soundPool.play(soundID,(float)0.5,(float)0.5,10,1,(float)1.0);
        botao3.startAnimation(rotation);
        v.vibrate(50);

    }
    public void button4(){
        soundID = soundPool.load(this, R.raw.clique3, 1);
        soundPool.play(soundID,(float)0.5,(float)0.5,10,1,(float)1.0);
        botao4.startAnimation(rotation);
        v.vibrate(50);

    }
    public void button5(){
        soundID = soundPool.load(this, R.raw.bip, 1);
        soundPool.play(soundID,(float)0.5,(float)0.5,10,1,(float)1.0);
        botao5.startAnimation(rotation);
        v.vibrate(50);

    }
    public void button6(){
        soundID = soundPool.load(this, R.raw.clique3, 1);
        soundPool.play(soundID,(float)0.5,(float)0.5,10,1,(float)1.0);
        botao6.startAnimation(rotation);
        v.vibrate(50);

    }
    public void button7(){
        soundID = soundPool.load(this, R.raw.clique4, 1);
        soundPool.play(soundID,(float)0.5,(float)0.5,10,1,(float)1.0);
        botao7.startAnimation(rotation);
        v.vibrate(50);

    }
    public void button8(){
        soundID = soundPool.load(this, R.raw.clique, 1);
        soundPool.play(soundID,(float)0.5,(float)0.5,10,1,(float)1.0);
        botao8.startAnimation(rotation);
        v.vibrate(50);
    }
    public void button9(){
        soundID = soundPool.load(this, R.raw.clique2, 1);
        soundPool.play(soundID,(float)0.5,(float)0.5,10,1,(float)1.0);
        botao9.startAnimation(rotation);
        v.vibrate(50);

    }
    public void button10(){
        soundID = soundPool.load(this, R.raw.input, 1);
        soundPool.play(soundID,(float)0.5,(float)0.5,10,1,(float)1.0);
        botao10.startAnimation(rotation);
        v.vibrate(50);

    }
    public void button11() {
        soundID = soundPool.load(this, R.raw.clique2, 1);
        soundPool.play(soundID, (float) 0.5, (float) 0.5, 10, 1, (float) 1.0);
        botao11.startAnimation(rotation);
        v.vibrate(50);
        if (som==true) {
            botao11.setImageResource(R.drawable.ic_volume_off_white_48dp);
            som=false;
        } else {
            botao11.setImageResource(R.drawable.ic_volume_up_white_48dp);
            som=true;
        }
    }

    public boolean getSom() {
        return som;
    }

    //trabalhando com sons no jogo
    private SoundPool buildSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(25)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            buildBeforeAPI21();
        }
        return soundPool;
    }


    //usado apenas se o lvl api android do celular do usuario for menor que 21
    public void buildBeforeAPI21() {

        //pega os componentes para manipular
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actVolume / maxVolume;

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //repetição rápida do som
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });


    }
    public static PrimeiraTela getInstance(){
        return   primeiraTela;
    }

    public void onPause() {
        super.onPause();

    }
    public void onResume() {
        super.onResume();

        onRestart();
    }

}
