package com.example.juanse.secgps;

import android.os.Environment;

import com.google.android.gms.maps.model.LatLng;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Juanse on 16/05/2015.
 */
public class CargaEnMemoria {

    String ruta = Environment.getExternalStorageDirectory()+"/omw/";
    ArrayList<Punto> ArrayPuntos = new ArrayList<Punto>();

    public ArrayList<Punto> FromCsv (String ruta) throws IOException {

        CSVReader reader = new CSVReader(new FileReader(ruta));//+archivo
        String[] nextLine;
        String Paux; //Cadena para almacenar el punto auxiliar recogido
        String text;
        String categoria;
        String fileName;

        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            Paux=nextLine[0];
            double lat = Double.parseDouble(Paux);
            Paux=nextLine[1];
            double lng = Double.parseDouble(Paux);
            LatLng coord = new LatLng(lat,lng); //Asignamos un nuevo tipo Latitud_Longitud
            text=nextLine[2];// texto recurrente
            categoria=nextLine[3];
            fileName=nextLine[4];
            //declaro punto e inserto
            Punto punto = new Punto(coord,categoria,Integer.parseInt(fileName));
            ArrayPuntos.add(Integer.parseInt(fileName),punto);
        }
    return  ArrayPuntos;
    }
}