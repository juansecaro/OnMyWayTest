package com.example.juanse.secgps;

import java.io.File;

/**
 * Created by Juanse on 16/05/2015.
 */
public class CargaEnMemoria {


    public void Carga()//conviene que sea boolean

    {
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