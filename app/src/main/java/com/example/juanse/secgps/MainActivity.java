package com.example.juanse.secgps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


//---------------------



public class MainActivity extends Activity {



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView list = new ListView(this);
        setContentView(R.layout.activity_main);//list
        CustomAdapter adapter = new CustomAdapter(this,R.layout.custom_row,R.id.line1,new String[] {"Nuevo","Continuar","Logros","Configuración","Novedades"});
        list.setAdapter(adapter);
    }

    private static class CustomAdapter extends ArrayAdapter<String> {
        public CustomAdapter(Context context, int layout, int resId,String[] items) {
        //Call through to ArrayAdapter implementation
            super(context, layout, resId, items);
        }
        @Override
        public View getView(int position, View convertView,ViewGroup parent) {

            View row = convertView;
            //Inflate a new row if one isn't recycled
            if(row == null) {
                row = LayoutInflater.from(getContext()).inflate(R.layout.custom_row, parent, false);
            }
            String item = getItem(position);
            ImageView left = (ImageView)row.findViewById(R.id.leftimage);
            ImageView right = (ImageView)row.findViewById(R.id.rightimage);
            TextView text = (TextView)row.findViewById(R.id.line1);
            left.setImageResource(R.drawable.left_arrow);
            right.setImageResource(R.drawable.right_arrow);
            text.setText(item);
            return row;
        }
    }

    /*
    private LocationManager mManager;
    private Location mCurrentLocation;

    TextView latitud;
    TextView longitud;
    TextView precision;
    TextView ttiempo;

    Date date1;
    Date date2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





//Declaro para interfaz
         latitud = (TextView)findViewById(R.id.latitud);
         longitud = (TextView)findViewById(R.id.longitud);
         precision = (TextView)findViewById(R.id.precision);
         ttiempo = (TextView)findViewById(R.id.ttiempo);

        // Declaro para funcionalidad
        mManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        date1 = new Date();
        date2 = new Date();
        Log.d("*************fechas", "ok");
        ttiempo.setText("Hola");
        Log.d("******************text","ok");

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!mManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Ask the user to enable GPS
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Manager");
            builder.setMessage(
                    "We would like to use your location, "
                            + "but GPS is currently disabled.\n"
                            + "Would you like to change these settings "
                            + "now?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Launch settings, allowing user to change
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,int which) {
                    //No location service, no Activity
                    finish();
                }
            });
            builder.create().show();
        }
        //Get a cached location, if it exists
        mCurrentLocation = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateDisplay();
        //Register for updates
        int minTime = 5000;
        float minDistance = 0;
        mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,minTime,minDistance,mListener);
    }
    @Override
    public void onPause() {
        super.onPause();
        //Disable updates when we are not in the foreground
        // mManager.removeUpdates(mListener);
    }
    private void updateDisplay() {
        date2 = Calendar.getInstance().getTime();
        if(mCurrentLocation == null) {

             latitud.setText("Latitud: (sin_datos)");
             longitud.setText("Longitud: (sin_datos)");
             precision.setText("Precision: (sin_datos)");
        }
        else {
              date1 = Calendar.getInstance().getTime();

              latitud.setText("Latitud: " + String.valueOf(mCurrentLocation.getLatitude()));
              longitud.setText("Longitud: " + String.valueOf(mCurrentLocation.getLongitude()));
              precision.setText("Precision: " + String.valueOf(mCurrentLocation.getAccuracy()));
              Log.i("", String.valueOf(mCurrentLocation.getLatitude() + " - " + String.valueOf(mCurrentLocation.getLongitude())));

        }
        ttiempo.setText("Hace" + ((date2.getTime()-date1.getTime())/1000) + "segundos" );
    }


    //-------------Listeers
    private LocationListener mListener = new LocationListener() {
        //New location event
        @Override
        public void onLocationChanged(Location location) {
            mCurrentLocation = location;
            updateDisplay();
        }
        //The requested provider was disabled in settings
        @Override
        public void onProviderDisabled(String provider) { }
        //The requested provider was enabled in settings
        @Override
        public void onProviderEnabled(String provider) { }
        //Availability changes in the requested provider
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }
    };


*/
}
