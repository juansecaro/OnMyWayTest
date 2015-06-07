package com.example.juanse.secgps;

import android.os.Environment;

import com.google.android.gms.maps.model.LatLng;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Juanse on 16/05/2015.
 */
public class Memory {


    public ArrayList<Punto> FromCsv(String ruta) throws IOException {

        ArrayList<Punto> ArrayPuntos = new ArrayList<Punto>();
        String NuevaRuta = ruta +"index.csv";
        CSVReader reader = new CSVReader(new FileReader(NuevaRuta));//+archivo
        String[] nextLine;
        String Paux; //Cadena para almacenar el punto auxiliar recogido
        String text;
        String categoria;
        int fileName;


        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            Paux = nextLine[0];
            double lat = Double.parseDouble(Paux);
            Paux = nextLine[1];
            double lng = Double.parseDouble(Paux);
            LatLng coord = new LatLng(lat, lng); //Asignamos un nuevo tipo Latitud_Longitud
            text = nextLine[2];// texto recurrente
            categoria = nextLine[3];
            fileName = Integer.parseInt(nextLine[4]);


            //declaro punto e inserto
            Punto punto = new Punto(coord, categoria, fileName);
            if (categoria.equals("VIS")){
                punto.setVisitado();}
            ArrayPuntos.add(punto);

        }
        return ArrayPuntos;
    }

    public void ToCSV(ArrayList<Punto> ArrayPuntos)throws IOException{
        CSVWriter writer = new CSVWriter(new FileWriter(
                Environment.getExternalStorageDirectory() + "/omw/zipSample/index.csv"));//falta el absolute path

        String[] entries = new String[5]; //
        Iterator<Punto> iterator = ArrayPuntos.iterator();
        while (iterator.hasNext()) {
            Punto P = iterator.next();
            LatLng L = P.getCoordenadas();
            entries[0] =  String.valueOf(L.latitude);
            entries[1] =  String.valueOf(L.longitude);
            entries[2] = P.descripcion;
            entries[3] = P.categoria;
            entries[4] = P.uriAudio.substring(0,1);//Cojo solo el primer caracter, que es el numero

            writer.writeNext(entries);
        }
        writer.close();
    }

}