package com.example.juanse.secgps;

import com.google.android.gms.maps.model.LatLng;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
            ArrayPuntos.add(punto);

        }
        return ArrayPuntos;
    }

    public boolean ToCSV(ArrayList<Punto> Array){return true;}

}