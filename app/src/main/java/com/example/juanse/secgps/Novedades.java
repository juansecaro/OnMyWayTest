package com.example.juanse.secgps;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
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
    private ProgressBar mProgress;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novedades);

        String news="";
        mProgress = (ProgressBar)findViewById(R.id.mProgressBar);
        textPrompt = (TextView)findViewById(R.id.textprompt);
        textMsg = (TextView)findViewById(R.id.textmsg);

        TextFromURL TUrl = new TextFromURL();
        TUrl.execute(news);


        textPrompt.setText(news);

    }

    //---
    public class TextFromURL extends AsyncTask<String,Integer,String>
    {
        @Override
        protected void onPreExecute(){
        }
        @Override
        protected String doInBackground(String...params){

            String result = "";
            try {
                URL url = new URL("http://www.adeter.org/omw/novedades_es.txt");

                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = null;

                while ((line = in.readLine()) != null) {
                    //get lines
                    result+=line;
                }
                in.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onProgressUpdate(Integer...values){

            mProgress.setProgress(values[0]);
        }
       /* @Override
        protected void onPostExecute(){


        }*/
    }
}
