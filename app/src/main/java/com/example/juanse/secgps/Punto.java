package com.example.juanse.secgps;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Juanse on 23/04/2015.
 */
public class Punto extends Activity {
    String uriFoto;
    String uriAudio;
    String descripcion;
    int alcance;
    String categoria;
    LatLng coordenadas;
    boolean visitado;
    private ImageButton mPlay;
    private MediaPlayer mPlayer;

    public Punto(LatLng coor, String cat, int nArchivo) {
        coordenadas = coor;
        categoria = cat;
        uriFoto = nArchivo + ".png";
        uriAudio = nArchivo + ".ogg";
        String uriDes = nArchivo + "txt";
        descripcion = LeerTxt(uriDes);
        alcance = 10;
    }
    public Punto(){}

    public LatLng getCoordenadas(){return coordenadas;}

    public String LeerTxt(String rutaTxt) {
        String Aux = "";
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(rutaTxt);
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
                mPlayer = MediaPlayer.create(Punto.this,
                        Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/omw/zipSample/1.ogg"));
                mPlayer.start();

            } //

        });

    }


}
