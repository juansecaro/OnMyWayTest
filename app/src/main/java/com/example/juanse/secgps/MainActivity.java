package com.example.juanse.secgps;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ListView list = new ListView(this);
        setContentView(R.layout.activity_main);//list
        // CustomAdapter adapter = new CustomAdapter(this,R.layout.custom_row,R.id.line1,
        //       new String[] {"Nuevo","Continuar","Logros","Configuración","Novedades"});
        //ListView listView = (ListView) findViewById(R.id.lista);

        // list.setAdapter(adapter);
        String[] adobe_products = getResources().getStringArray(R.array.adobe_products);
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, adobe_products));

        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //select item
                Intent i;
                String selection =  (String)parent.getItemAtPosition(position);
                switch (position)
                {
                    case 0: //Nuevo Tour
                        i = new Intent(getApplicationContext(), DescargaCiudad.class);
                        startActivity(i);
                        break;
                    case 1: //Continuar // No bajamos, chequeaos los archivos existentes y ejecutamos sobre ellos
                        break;
                    case 2: //Logros
                        break;
                    case 3: //Configuraciones
                        break;
                    case 4: //Perfil
                        break;
                    case 5: //Novedades
                        i = new Intent(getApplicationContext(), Novedades.class);
                        startActivity(i);
                        break;
                    case 6: //Ayuda
                        break;

                }

                // Launching new Activity on selecting single List Item




            }
        });
    }



    }
