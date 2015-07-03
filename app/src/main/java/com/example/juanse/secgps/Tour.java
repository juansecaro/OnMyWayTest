package com.example.juanse.secgps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ToggleButton;

/**
 * Created by Juanse on 08/06/2015.
 */
public class Tour extends Activity {

    private CheckBox ch1;
    private CheckBox ch2;
    private CheckBox ch3;
    private CheckBox ch4;

    private Button btn;
    private ToggleButton tog1;
    private ToggleButton tog2;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour);

        btn = (Button)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainMap.class);
                startActivity(i);
            }
        });


    }

    /**
     * Listeners para todos los elementos de UI.
     *
     * Detectamos cualquier cambio y actualizamos los datos que mandamos en en el intent final con las configuraciones
     *
     */
    public void addListenerOnButton() {

        ch1 = (CheckBox)findViewById(R.id.checkBox1);
        ch2 = (CheckBox)findViewById(R.id.checkBox2);
        ch3 = (CheckBox)findViewById(R.id.checkBox3);
        ch4 = (CheckBox)findViewById(R.id.checkBox4);

        tog1 = (ToggleButton)findViewById(R.id.tog1);
        tog2 = (ToggleButton)findViewById(R.id.tog2);




        tog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tog1.isChecked()) return;

            }
        });
        tog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tog2.isChecked()) return;

            }
        });



    }

}


