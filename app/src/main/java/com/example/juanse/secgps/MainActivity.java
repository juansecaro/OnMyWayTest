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
                String selection =  (String)parent.getItemAtPosition(position);
                // Launching new Activity on selecting single List Item
                    Intent i = new Intent(getApplicationContext(), Novedades.class);
                    startActivity(i);



            }
        });
    }



    }
