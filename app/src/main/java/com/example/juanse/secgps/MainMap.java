package com.example.juanse.secgps;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Juanse on 17/05/2015.
 */
public class MainMap extends FragmentActivity {

    float RED = BitmapDescriptorFactory.HUE_RED; // Turístico
    float ORANGE = BitmapDescriptorFactory.HUE_ORANGE; // Histórico
    float YELLOW = BitmapDescriptorFactory.HUE_YELLOW; // Curioso
    float GREEN = BitmapDescriptorFactory.HUE_GREEN; // Gangas (Bargains)

    private GoogleMap mMap;
    String ruta = Environment.getExternalStorageDirectory() + "/omw/zipSample/";

    ArrayList<Punto> ArrayPuntos = new ArrayList<Punto>();
    Memory Mem = new Memory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmap);
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mainnmap)).getMap();

        try {
            ArrayPuntos = Mem.FromCsv(ruta);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator<Punto> iterator = ArrayPuntos.iterator();
        while (iterator.hasNext())
        {
            Punto P = iterator.next();
            switch (P.categoria) //Dibujamos según tipo de punto
            {
                case "HIS":
                        mMap.addMarker(new MarkerOptions()
                            .position(P.getCoordenadas())
                            .icon(BitmapDescriptorFactory.defaultMarker(ORANGE)));
                    break;
                case "CUR":
                         mMap.addMarker(new MarkerOptions()
                            .position(P.getCoordenadas())
                            .icon(BitmapDescriptorFactory.defaultMarker(YELLOW)));
                    break;
                case "BAR":
                          mMap.addMarker(new MarkerOptions()
                            .position(P.getCoordenadas())
                            .icon(BitmapDescriptorFactory.defaultMarker(GREEN)));
                    break;
                default:
                        mMap.addMarker(new MarkerOptions()
                            .position(P.getCoordenadas())
                            .icon(BitmapDescriptorFactory.defaultMarker(RED)));
                    break;
            }

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ArrayPuntos.get(0).getCoordenadas(), 13)); //centro el mapa en las últimas coordenadas y con zoom de 10
    }

}