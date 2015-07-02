package com.example.juanse.secgps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.Settings;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by Juanse on 17/05/2015.
 */
public class MainMap extends Activity {

    protected static final String TAG = "MainMap";

    float RED = BitmapDescriptorFactory.HUE_RED; // Turistico
    float ORANGE = BitmapDescriptorFactory.HUE_ORANGE; // Historico
    float YELLOW = BitmapDescriptorFactory.HUE_YELLOW; // Curioso
    float GREEN = BitmapDescriptorFactory.HUE_GREEN; // Gangas (Bargains)
    float BLUE = BitmapDescriptorFactory.HUE_BLUE; // Visitado

    private GoogleMap mMap;
    private LocationManager mManager;
    private Location mCurrentLocation;
    private Semaphore s = new Semaphore(1);

    private boolean stopActivity = true;

    String ruta = Environment.getExternalStorageDirectory() + "/omw/zipSample/";

    List<Punto> ArrayPuntos = Collections.synchronizedList(new ArrayList<Punto>());


    Memory Mem = new Memory();

    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/omw/zipSample/"+"ChochosRicos.txt");

    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


            analytics = GoogleAnalytics.getInstance(this);
            analytics.setLocalDispatchPeriod(1800);

            tracker = analytics.newTracker("Incio actividad principal");
            tracker.enableExceptionReporting(true);
            tracker.enableAdvertisingIdCollection(true);
            tracker.enableAutoActivityTracking(true);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmap);
        mManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mainnmap)).getMap();
        drawMap();


        try{
        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("1");

        bw.close();
        }
        catch (IOException e){}

        FragmentManager fm = getFragmentManager();

        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getFragmentManager().getBackStackEntryCount() == 0) finish();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Ask the user to enable GPS
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Manager");
            builder.setMessage(R.string.location_needed);
            builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Launch settings, allowing user to change
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }
            });
            builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //No location service, no Activity
                    finish();
                }
            });
            builder.create().show();
        }
        //Get a cached location, if it exists
        mCurrentLocation = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //updateDisplay();
        //Register for updates
        int minTime = 8000;// in milliseconds
        float minDistance = 0;
        mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, mListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        //Disable updates when we are not in the foreground
        // mManager.removeUpdates(mListener);
    }
    public void drawMap()
    {

        try {
            ArrayPuntos = Mem.FromCsv(ruta);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator<Punto> iterator = ArrayPuntos.iterator();
        while (iterator.hasNext()) {
            Punto P = iterator.next();
            switch (P.categoria) //Dibujamos segun tipo de punto
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
                case "VIS":
                    mMap.addMarker(new MarkerOptions()
                        .position(P.getCoordenadas())
                        .icon(BitmapDescriptorFactory.defaultMarker(BLUE)));
                    break;

                default:// TURISTICO
                    mMap.addMarker(new MarkerOptions()
                            .position(P.getCoordenadas())
                            .icon(BitmapDescriptorFactory.defaultMarker(RED)));
                    break;
            }


        }
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ArrayPuntos.get(0).getCoordenadas(), 13)); //centro el mapa en las ultimas coordenadas y con zoom de 10




    }



    /**
     * Vamos a establecer un cuadrante en base a un punto, siendo este el centro del mismo
     * <p/>
     * decimal
     * places   degrees          distance
     * -------  -------          --------
     * 0        1                111  km
     * 1        0.1              11.1 km
     * 2        0.01             1.11 km
     * 3        0.001            111  m
     * 4        0.0001           11.1 m
     * 5        0.00001          1.11 m
     * 6        0.000001         11.1 cm
     * 7        0.0000001        1.11 cm
     * 8        0.00000001       1.11 mm
     * *
     */
    public boolean IsInside(Punto P)//In meters
    {
        // 0.00001 // equivale a 1.11 m
        double factor = P.alcance * 0.00001; // numero por el que multiplicamos para calcular el alcance efectivo
        double latmax = P.getCoordenadas().latitude + factor;
        double latmin = P.getCoordenadas().latitude - factor;
        double lngmax = P.getCoordenadas().longitude + factor;
        double lngmin = P.getCoordenadas().longitude - factor;
        boolean inside = false;

        if (latmin <= mCurrentLocation.getLatitude()) {

            if (mCurrentLocation.getLatitude() <= latmax) {

                if (lngmin <= mCurrentLocation.getLongitude()) {

                    if (mCurrentLocation.getLongitude() <= lngmax) {
                        inside = true;
                    }
                }
            }
        }
        // Then, we are in the range

        return inside;
    }

    /**
     * Calcula distancias en el centro de dos puntos en conflicto
     * @param lat1 latitud
     * @param lat2
     * @param lon1 longitud
     * @param lon2
     * @param el1 altura
     * @param el2
     * @return Valor con la distancia más corta
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radio terrestre

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    //-------------Listeers
    private LocationListener mListener = new LocationListener() {
        //New location event
        @Override
        public void onLocationChanged(Location location) {

            if (readKeyFile()) {
                writeKeyFile();

                mCurrentLocation = location;  //marca de tiempo??
                Punto P;
                Punto P_2 = null;
                double distanceNow = 0.0;
                double distanceMin = 0.0;
                boolean findPoint = false;
                int cont = 0; // para ver posiciones en el array
                int contFin = 0;


                Iterator<Punto> iterator = ArrayPuntos.iterator();
                while (iterator.hasNext()) {

                    P = iterator.next();

                    if (!P.visitado) { // Si ya ha sido cargado, nada (al principio todos falsos)
                        if (IsInside(P)) {// Es nuestro punto
                            findPoint = true;
                            if (P_2 == null) {
                                distanceMin = distance(P.coordenadas.latitude, mCurrentLocation.getLatitude(), P.coordenadas.longitude, mCurrentLocation.getLongitude(), 0, 0);
                                P_2 = new Punto(P);
                                contFin = cont + 1;
                            } else {
                                distanceNow = distance(P.coordenadas.latitude, mCurrentLocation.getLatitude(), P.coordenadas.longitude, mCurrentLocation.getLongitude(), 0, 0);
                                if (distanceNow < distanceMin) {
                                    distanceMin = distanceNow;
                                    P_2 = new Punto(P);
                                    contFin = cont + 1;
                                }
                            }
                        }
                    }
                    cont++;//Para llevar control de la pos
                }

                if (findPoint) {

                    Intent i = new Intent(getApplicationContext(), Punto.class);
                    i.putExtra("uriFoto", P_2.uriFoto);
                    i.putExtra("uriAudio", P_2.uriAudio);
                    i.putExtra("descripcion", P_2.descripcion);
                /*P_2.setVisitado();
                P_2.categoria = "VIS";
                ArrayPuntos.set(contFin-1,P_2); //actualizamos el item como visitado*/


                    ArrayPuntos.get(contFin - 1).setVisitado();
                    ArrayPuntos.get(contFin - 1).categoria = "VIS";

                    try {
                        Mem.ToCSV(ArrayPuntos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Avisamos de algo nuevo
                    Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    v.vibrate(500);
                    //redibujamos el mapa con los visitados
                    mMap.clear();
                    drawMap();

                    stopActivity = false;
                    // Mostramos el punto
                    startActivity(i);

                }
                try {
                    Thread.sleep(400); //Ayuda a no sobrecargar la aplicación y dar más fluidez al mapa
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }

        protected void writeKeyFile(){
            try{
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("0");
                bw.close();
            }
            catch (IOException e){}
         }

        protected boolean readKeyFile(){
            try {
                FileReader fr = new FileReader(file.getAbsoluteFile());
                BufferedReader br = new BufferedReader(fr);
                if (br.readLine().equals("1")){
                    return true;
                }
            }
            catch (FileNotFoundException e){ return false;}
            catch (IOException e){ return false;}
            return false;
        }

        //The requested provider was disabled in settings
        @Override
        public void onProviderDisabled(String provider) {
        }

        //The requested provider was enabled in settings
        @Override
        public void onProviderEnabled(String provider) {
        }

        //Availability changes in the requested provider
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {


        }
    };


}

