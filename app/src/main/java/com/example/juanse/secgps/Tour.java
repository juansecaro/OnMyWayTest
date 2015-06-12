package com.example.juanse.secgps;

import android.app.Activity;
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
    private CheckBox ch5;
    private Button btn;
    private ToggleButton tog1;
    private ToggleButton tog2;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour);




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
        ch5 = (CheckBox)findViewById(R.id.checkBox5);
        btn = (Button)findViewById(R.id.btn);
        tog1 = (ToggleButton)findViewById(R.id.tog1);
        tog2 = (ToggleButton)findViewById(R.id.tog2);

       /* ch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ch5.isChecked())
                {
                    ch1.


                }


            }
        });*/


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

        btn.setOnClickListener(new View.OnClickListener() {

            //Run when button is clicked
            @Override
            public void onClick(View v) {



            }
        });

    }


}


