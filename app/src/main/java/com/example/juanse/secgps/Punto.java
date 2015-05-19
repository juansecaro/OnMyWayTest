package com.example.juanse.secgps;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Juanse on 23/04/2015.
 */
public class Punto extends Activity {
    String uriFoto;
    String descripcion;
    String uriAudio;
    float latitud;
    float longitud;
    boolean visitado;
    private ImageButton mPlay;
    private MediaPlayer mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.punto);
        mPlay = (ImageButton)findViewById(R.id.bPlay);
        addButtonListener();
        /*mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if(mPlayer == null) {
                        try {
                            mPlayer = MediaPlayer.create(Punto.this,
                                    Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/omw/zipSample/1.ogg"));
                            mPlayer.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;
                    }
                }

            }
        });*/

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPlayer != null) {
            mPlayer.release();
        }
    }

    //OnCompletionListener Methods



  /*  public cargarDatosEnPantalla()
    {
        // Imagen

        // Descripción

        // Sonido


    }*/
    public void Audio()
    {



    }
    public void addButtonListener() {

        mPlay = (ImageButton) findViewById(R.id.bPlay);

        mPlay.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Toast.makeText(Punto.this, "ImageButton is working!", Toast.LENGTH_SHORT).show();
                mPlayer = MediaPlayer.create(Punto.this,
                        Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/omw/zipSample/1.ogg"));
                mPlayer.start();

            } //

        });

    }



}
