package com.example.juanse.secgps;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;

public class MainActivity extends ListActivity {

    String ruta = Environment.getExternalStorageDirectory() + "/omw/zipSample/index.csv";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] adobe_products = getResources().getStringArray(R.array.adobe_products);
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, adobe_products));

        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //select item
                Intent i;
                String selection = (String) parent.getItemAtPosition(position);
                switch (position) {
                    case 0: //Nuevo Tour //lo descargamos
                        i = new Intent(getApplicationContext(), Descarga.class);
                        startActivity(i);
                        break;
                    case 1: //Continuar // No bajamos, chequeaos los archivos existentes y ejecutamos sobre ellos

                        break;
                    case 2: //Configuraciones
                        break;
                    case 3: //Logros
                        i = new Intent(getApplicationContext(), Logros.class);
                        startActivity(i);
                        break;
                    case 4: //Novedades
                        i = new Intent(getApplicationContext(), Novedades.class);
                        startActivity(i);
                        break;


                }

                // Launching new Activity on selecting single List Item


            }
        });
    }

    /**
     *
     * @return
     */
    public boolean existIndex()

    {
        File f = new File(ruta);
        if (f.exists() && !f.isDirectory()) {

            Intent i = new Intent(getApplicationContext(), MainMap.class);
            startActivity(i);

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("");
            builder.setMessage(R.string.not_previous_route);
            builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Launch settings, allowing user to change
                    Intent i = new Intent(getApplicationContext(), Novedades.class);
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

        return true;
    }
}