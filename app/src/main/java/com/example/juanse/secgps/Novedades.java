package com.example.juanse.secgps;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Juanse on 05/05/2015.
 */

public class Novedades extends Activity {

    TextView textMsg, textPrompt;
    final String textSource = "http://www.adeter.org/omw/novedades_es.txt";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novedades);
        textPrompt = (TextView)findViewById(R.id.textprompt);
        textMsg = (TextView)findViewById(R.id.textmsg);

        textPrompt.setText("Wait...");

        URL textUrl;
        try {
            textUrl = new URL(textSource);
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(textUrl.openStream()));
            String StringBuffer;
            String stringText = "";
            while ((StringBuffer = bufferReader.readLine()) != null) {
                stringText += StringBuffer;
            }
            bufferReader.close();
            textMsg.setText(stringText);
        } catch (MalformedURLException e) {

            e.printStackTrace();
            textMsg.setText(e.toString());
        } catch (IOException e) {

            e.printStackTrace();
            textMsg.setText(e.toString());
        }

        textPrompt.setText("Finished!");

    }

}
