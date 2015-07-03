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

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * Created by Juanse on 27/06/2015.
 */

    public class Descarga extends Activity {

        private  final String DL_ID = "downloadId";
        private SharedPreferences prefs;
        private DownloadManager dm;
        private ImageView imageView;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            imageView = new ImageView(this);
            setContentView(R.layout.descargando);
            prefs =  PreferenceManager.getDefaultSharedPreferences(this);
            dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        }
        @Override
        public void onResume() {
            super.onResume();

            int a;
            if(!prefs.contains(DL_ID)) {
                //Start the download
                Uri resource = Uri.parse("http://www.adeter.org/zipSample.zip");
                DownloadManager.Request request =
                        new DownloadManager.Request(resource);
                //Set allowed connections to process download
                request.setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_MOBILE
                                | DownloadManager.Request.NETWORK_WIFI);
                request.setDestinationInExternalPublicDir("/omw", "zipSample.zip");
                request.setAllowedOverRoaming(false);
                //Display in the notification bar
                request.setTitle("Download Sample");
                long id = dm.enqueue(request);
                //Save the unique id
                prefs.edit().putLong(DL_ID, id).commit();
            } else {
                //Download already started, check status
                try {
                    queryDownloadStatus();
                } catch (ZipException e) {
                    e.printStackTrace();
                }
            }
            registerReceiver(receiver, new IntentFilter(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
        @Override
        public void onPause() {
            super.onPause();
            unregisterReceiver(receiver);
        }
        private BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    queryDownloadStatus();
                } catch (ZipException e) {
                    e.printStackTrace();
                }
            }
        };
        private void queryDownloadStatus() throws ZipException {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(prefs.getLong(DL_ID, 0));
            Cursor c = dm.query(query);
            if(c.moveToFirst()) {
                int status = c.getInt(
                        c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                switch(status) {
                    case DownloadManager.STATUS_PAUSED: break;
                    case DownloadManager.STATUS_PENDING: break;
                    case DownloadManager.STATUS_RUNNING: break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        //Done, display the image
                        String ZipFileLocation = "/sdcard/omw/zipSample.zip";
                        String unzipLocation = "/sdcard/omw/";

                        // Initiate ZipFile object with the path/name of the zip file.
                        ZipFile zipFile = new ZipFile(ZipFileLocation);
                        // Extracts all files to the path specified
                        zipFile.extractAll(unzipLocation);
                        // Call the map main activity to draw the points
                        Intent i = new Intent(getApplicationContext(), Tour.class);
                        startActivity(i);
                        this.finish();


                        break;
                    case DownloadManager.STATUS_FAILED:
                        //Clear the download and try again later
                        dm.remove(prefs.getLong(DL_ID, 0));
                        prefs.edit().clear().commit();
                        break;
                }
            }
        }
    }
