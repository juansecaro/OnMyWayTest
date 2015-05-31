package com.example.juanse.secgps;

import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
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
public class MainMap extends FragmentActivity  implements
        ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<Status> {

    float RED = BitmapDescriptorFactory.HUE_RED; // Turístico
    float ORANGE = BitmapDescriptorFactory.HUE_ORANGE; // Histórico
    float YELLOW = BitmapDescriptorFactory.HUE_YELLOW; // Curioso
    float GREEN = BitmapDescriptorFactory.HUE_GREEN; // Gangas (Bargains)

    private GoogleMap mMap;

    String ruta = Environment.getExternalStorageDirectory() + "/omw/zipSample/";

    ArrayList<Punto> ArrayPuntos = new ArrayList<Punto>();
    Memory Mem = new Memory();

    protected static final String TAG = "creating-and-monitoring-geofences";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * The list of geofences used in this sample.
     */
    protected ArrayList<Geofence> mGeofenceList;

    /**
     * Used to keep track of whether geofences were added.
     */
    private boolean mGeofencesAdded;

    /**
     * Used when requesting to add or remove geofences.
     */
    private PendingIntent mGeofencePendingIntent;

    /**
     * Used to persist application state about whether geofences were added.
     */
    private SharedPreferences mSharedPreferences;

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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ArrayPuntos.get(0).getCoordenadas(), 13)); //centro el mapa en las últimas coordenadas y con zoom de 10
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(Status status) {

    }
}