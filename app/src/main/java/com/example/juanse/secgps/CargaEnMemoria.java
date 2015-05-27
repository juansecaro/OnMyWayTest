package com.example.juanse.secgps;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Juanse on 16/05/2015.
 */
public class CargaEnMemoria {


    public void Carga() throws FileNotFoundException//conviene que sea boolean

    {
        CSVReader reader = new CSVReader(new FileReader("yourfile.csv"));
        String rutaDirectorio ="";
        File f = new File(rutaDirectorio);

        if (f.exists()) { // Directorio existe
            File[] ficheros = f.listFiles();
            for (int x = 0; x < ficheros.length; x++) {
                System.out.println(ficheros[x].getName());
            }
        } else { //Directorio no existe }
        }

    }
}