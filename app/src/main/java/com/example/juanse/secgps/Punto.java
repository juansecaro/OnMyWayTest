package com.example.juanse.secgps;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Juanse on 23/04/2015.
 */
public class Punto extends Activity { // So we can pass objects between activities
    String uriFoto;
    String uriAudio;
    String descripcion;
    int alcance;
    String categoria;
    LatLng coordenadas;
    boolean visitado;
    private ImageButton mPlay;
    private MediaPlayer mPlayer;
    String final_route = Environment.getExternalStorageDirectory().getPath() + "/omw/zipSample/";
    TextView txtCambiado;
    ImageView frontal;

    public Punto(LatLng coor, String cat, int nArchivo) {
        coordenadas = coor;
        categoria = cat;
        uriFoto = nArchivo + ".png";
        uriAudio = nArchivo + ".ogg";
        String uriDes = nArchivo + ".txt";
        descripcion = LeerTxt(uriDes);
        alcance = 10;
        visitado = false;
    }
    public Punto(){}

    public LatLng getCoordenadas(){return coordenadas;}

    public String LeerTxt(String rutaTxt) {

        final_route = final_route + rutaTxt;
        String Aux = "";
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(final_route);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null)
                Aux = Aux + linea;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            try {
                if (fr != null) {
                    fr.close();
                }

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return Aux;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.punto);
        //-- Recuperamos información específica del Intent
        Bundle extras = getIntent().getExtras();
        uriFoto = extras.getString("uriFoto");
        uriAudio = extras.getString("uriAudio");
        descripcion = extras.getString("descripcion");


        txtCambiado = (TextView)findViewById(R.id.panel);
        txtCambiado.setText(descripcion);

        frontal = (ImageView)findViewById(R.id.foto);
        Bitmap bitmap = BitmapFactory.decodeFile(final_route+uriFoto);
        frontal.setImageBitmap(bitmap);






        visitado = true; // Once it's inflate, then it's visited
        mPlay = (ImageButton) findViewById(R.id.bPlay);
        addButtonListener();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
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
    public void Audio() {


    }

    public void addButtonListener() {

        mPlay = (ImageButton) findViewById(R.id.bPlay);

        mPlay.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Toast.makeText(Punto.this, "ImageButton is working!", Toast.LENGTH_SHORT).show();
               /* mPlayer = MediaPlayer.create(Punto.this,
                        Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/omw/zipSample/1.ogg"));
                mPlayer.start();*/
                String A = final_route + uriAudio;
                mPlayer = MediaPlayer.create(Punto.this, Uri.parse(final_route + uriAudio));

                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                        finish();
                    }

                });
                mPlayer.start();
            } //

        });

    }


}
