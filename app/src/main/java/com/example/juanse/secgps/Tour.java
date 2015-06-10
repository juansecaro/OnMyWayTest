package com.example.juanse.secgps;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
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
        ch1 = (CheckBox)findViewById(R.id.checkBox1);
        ch2 = (CheckBox)findViewById(R.id.checkBox2);
        ch3 = (CheckBox)findViewById(R.id.checkBox3);
        ch4 = (CheckBox)findViewById(R.id.checkBox4);
        ch5 = (CheckBox)findViewById(R.id.checkBox5);
        btn = (Button)findViewById(R.id.btn);
        tog1 = (ToggleButton)findViewById(R.id.tog1);
        tog2 = (ToggleButton)findViewById(R.id.tog2);



    }
    public void addListenerOnButton() {

        Che = (CheckBox) findViewById(R.id.chkIos);
        chkAndroid = (CheckBox) findViewById(R.id.chkAndroid);
        chkWindows = (CheckBox) findViewById(R.id.chkWindows);
        btnDisplay = (Button) findViewById(R.id.btnDisplay);

        btnDisplay.setOnClickListener(new OnClickListener() {

            //Run when button is clicked
            @Override
            public void onClick(View v) {

                StringBuffer result = new StringBuffer();
                result.append("IPhone check : ").append(chkIos.isChecked());
                result.append("\nAndroid check : ").append(chkAndroid.isChecked());
                result.append("\nWindows Mobile check :").append(chkWindows.isChecked());

                Toast.makeText(MyAndroidAppActivity.this, result.toString(),
                        Toast.LENGTH_LONG).show();

            }
        });

    }


}


