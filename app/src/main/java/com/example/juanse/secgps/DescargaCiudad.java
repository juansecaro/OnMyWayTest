package com.example.juanse.secgps;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.opencsv.CSVReader;

import net.lingala.zip4j.core.ZipFile;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class DescargaCiudad extends Activity {
    private static final String DL_ID = "downloadId";
    private SharedPreferences prefs;
    private DownloadManager dm;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = new ImageView(this);
        setContentView(imageView);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!prefs.contains(DL_ID)) {
//Start the download
            Uri resource = Uri.parse("http://www.adeter.org/zipSample.zip");
            DownloadManager.Request request = new DownloadManager.Request(resource);
//Set allowed connections to process download
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                    | DownloadManager.Request.NETWORK_WIFI);
            request.setAllowedOverRoaming(false);
//Finding a place to be saved

            request.setDestinationInExternalPublicDir("/omw", "zipSample1.zip");

//Display in the notification bar
            request.setTitle("Download Sample");
            long id = dm.enqueue(request);
//Save the unique id
            prefs.edit().putLong(DL_ID, id).commit();
        } else {
            //Download already started, check status
            queryDownloadStatus();
        }
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            queryDownloadStatus();
        }
    };

    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(prefs.getLong(DL_ID, 0));
        Cursor c = dm.query(query);

        if (c.moveToFirst()) {
            int status = c.getInt(
                    c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                case DownloadManager.STATUS_PENDING:
                case DownloadManager.STATUS_RUNNING:
//Do nothing, still in progress
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
//Done, display the image
                    try {
                        //bajado con e
                        String ZipFileLocation = "/sdcard/omw/zipSample1.zip";
                        String unzipLocation = "/sdcard/omw/";


                        // Initiate ZipFile object with the path/name of the zip file.
                        ZipFile zipFile = new ZipFile(ZipFileLocation);
                        // Extracts all files to the path specified
                        zipFile.extractAll(unzipLocation);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case DownloadManager.STATUS_FAILED:
//Clear the download and try again later
                    dm.remove(prefs.getLong(DL_ID, 0));
                    prefs.edit().clear().commit();
                    break;
            }
        }
    }
public void csv(String S) throws FileNotFoundException {
    CSVReader reader = new CSVReader(new FileReader(S));

}


}