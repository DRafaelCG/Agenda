package com.mavi.agenda.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.mavi.agenda.R;

public class SplashActivity extends AppCompatActivity{

    protected boolean active = true;
    protected int splashTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ajustarPantalla();
        setContentView(R.layout.activity_splash);
        lanzaSplash();
    }

    private void lanzaSplash(){
        /*Ejecutamos una tarea en segundo plano para hacer un efecto en la forma que se muestra la imagen*/
        Thread splashThread = new Thread(){
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (active && waited < splashTime){
                        sleep(100);
                        if (active){
                            waited += 100;
                        }
                    }
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }finally {
                    openApp();
                }
            }
        };
        splashThread.start();
    }

    private void ajustarPantalla(){
        /*Ajutamos las propiedades de la pantalla para que se vea la pantalla completa y no se pueda girar en esta actividad*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void openApp(){
        /*Iniciamos la actividad de Agenda despues de terminar la animacion*/
        Intent inicio = new Intent(SplashActivity.this, AgendaActivity.class);
        startActivity(inicio);
        finish();
    }
}