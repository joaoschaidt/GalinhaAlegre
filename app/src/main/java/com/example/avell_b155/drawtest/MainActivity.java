package com.example.avell_b155.drawtest;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.app.Activity;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
// REMOVER IMPORTS QUE NAO ESTAO SENDO USADOS!!
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


public class MainActivity extends Activity {

    private long cont;
    private float xcordenate;
    private float ycordenate;
    private ImageView galinha;
    private Handler handler = new Handler();
    private RelativeLayout layout;
    private TextView timer;
    private TextView texto;
    private SoundPool soundPool;
    private Calendar cal = Calendar.getInstance();
    private SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
    private ProgressBar barra;
    private static MainActivity MainActivity;
    private FirstTimer f;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barra=(ProgressBar)findViewById(R.id.progressBar);
        texto=(TextView)findViewById(R.id.texto);
        final Animation animScaleUp = AnimationUtils.loadAnimation(this, R.anim.scale);
        final Random randomico=new Random();
        timer=(TextView)findViewById(R.id.timer);
        layout=(RelativeLayout)findViewById(R.id.secondlayout);

        MainActivity=this;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);

        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
/*
        double wi=(double)screenWidth/displayMetrics.xdpi;
        double hi=(double)screenHeight/displayMetrics.ydpi;

        final double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);

        final double screenInches = Math.sqrt(x+y);
*/

        final int tamanhoX = (int) getResources().getDimension(R.dimen._60sdp);
        final int tamanhoY = (int) getResources().getDimension(R.dimen._40sdp);

        //setando imagem e ajustando tamanho da galinha
        galinha= new ImageView(MainActivity.this);
        galinha.setImageResource(R.mipmap.galinha_nova);
        galinha.setScaleType(ImageView.ScaleType.FIT_CENTER);
        galinha.setScaleX(2);
        galinha.setScaleY(2);
        galinha.setX(screenWidth/2);
        galinha.setY(screenHeight/2);
        layout.addView(galinha);

        soundPool=buildSoundPool();

        //setando o timer
        f=new FirstTimer(25000,1000);
        f.start();

        barra.setMax(20);

        layout.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    layout.removeView(galinha);

                    //a cada clique é gerado um numero aleatório usando o máximo como as medidas da tela
                    xcordenate =randomico.nextInt(screenWidth);
                    ycordenate =randomico.nextInt(screenHeight);

                    //criando um componente imageview via codigo
                    final ImageView ovo = new ImageView(MainActivity.this);
                    ovo.setImageResource(R.mipmap.ovo);

                    //evitando posicionamento ruim
                    if((screenWidth-xcordenate)<=tamanhoX){
                        xcordenate=screenWidth-tamanhoX;
                    }
                    if((screenHeight-ycordenate)<=tamanhoY){
                        ycordenate=screenHeight-tamanhoY;
                    }

                    //dando a animação à galinha
                    final ObjectAnimator translateAnim= ObjectAnimator.ofFloat(galinha, "translationY", ycordenate,ycordenate-170);
                    translateAnim.setDuration(180);
                    translateAnim.setRepeatMode(Animation.REVERSE);
                    translateAnim.setRepeatCount(1);
                    translateAnim.addListener(upListener);

                    RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

                    vp.leftMargin = Math.round(xcordenate);
                    vp.topMargin = Math.round(ycordenate);

                    //adicionando via codigo os ovos botados
                    layout.addView(ovo,vp);
                    layout.addView(galinha);

                    galinha.setX(xcordenate);
                    galinha.setY(ycordenate);

                    translateAnim.start();

                    //dando som à ação
                    soundID = soundPool.load(MainActivity.this, R.raw.pum, 1);
                    soundPool.play(soundID,(float)0.5,(float)0.5,10,1,(float)1.0);

                    //incrementando pontos na barra
                    cont++;
                    barra.incrementProgressBy(1);
                    texto.setText(" "+String.format("%03d",cont)+" ");
                    setCont(cont++);


                    if(barra.getProgress()==barra.getMax()){
                        barra.setMax((int) (barra.getProgress()*1.5));
                        barra.setProgress(0);
                        f.cancel();
                        f.start();
                    }
                    //TODO se barra completar o tempo irá reiniciar

                    // animação do ovo
                    final RotateAnimation rotate = new RotateAnimation(3, -3, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.2f);
                    rotate.setDuration(100);
                    rotate.setInterpolator(new LinearInterpolator());
                    rotate.setRepeatCount(1);
                    rotate.setFillEnabled(true);
                    rotate.setRepeatMode(ValueAnimator.REVERSE);

                    ovo.startAnimation(animScaleUp);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            ovo.startAnimation(rotate);
                        }
                    }, 200);
                }
                return true;
            }
        });


    }


    //sobrescrevendo as imagens durante a animação
    private Animator.AnimatorListener upListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            galinha.setImageResource(R.mipmap.galinha_baixo);
        }
        @Override
        public void onAnimationRepeat(Animator animation) {
            galinha.setImageResource(R.mipmap.galinha_topo);
        }
        @Override
        public void onAnimationEnd(Animator animation) {
            galinha.setImageResource(R.mipmap.galinha_nova);
        }
        @Override
        public void onAnimationCancel(Animator animation) {
            galinha.setImageResource(R.mipmap.galinha_topo);
        }
    };


    public long getCont() { return cont;  }

    public void setCont(long cont) {
        this.cont = cont;
    }

    //TODO pegar o cont e mandar para o menu


    private boolean loaded;
    private float volume;
    private float actVolume;
    private float maxVolume;
    private AudioManager audioManager;
    private int soundID;

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



    public void buildBeforeAPI21() {

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actVolume / maxVolume;

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });
    }

    private static boolean som;
    private static long segundos;

    //sobrescrevendo o funcionamento do timer, formatando a aprensentação do tempo
    public class FirstTimer extends CountDownTimer{

        public FirstTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onTick(long millisUntilFinished) {
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, (int) ((millisUntilFinished) / 1000));
            segundos=millisUntilFinished;
            timer.setText("" +s.format(cal.getTime()) );
        }

        @Override
        public void onFinish() {
            layout.setClickable(false);

            PrimeiraTela primeiraTela = PrimeiraTela.getInstance();
            som=primeiraTela.getSom();
            primeiraTela.finish();
            PrimeiraTela.getInstance().finish();

            startActivity(new Intent(MainActivity.this,Menu.class));

        }

    }

    public boolean getSom() {
        return som;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    public static MainActivity getInstance(){
        return   MainActivity;
    }

    public void onPause() {
        super.onPause();
        f.cancel();
        f.onFinish();

    }
    public void onStop() {
        super.onStop();
        f.cancel();

    }


}
