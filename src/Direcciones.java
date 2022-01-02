/*
Nombre del fichero: Direcciones.java
Autor: Jorge Miguel Moreno Vieitez
Titulación/Grupo: 912
Fecha: 07/12/2021 - 17/12/2021

Este archivo contiene todo lo relativo a los URLs. Se ha movido a una
clase diferente para mejorar la legibilidad del proyecto.
*/

import java.awt.Desktop;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Direcciones {

    /**
     * Devuelve todas las URLs encontradas en una línea de texto,
     * incluyendo repetidas.
     * @param texto  La línea de texto a procesar
     * @return       ArrayList con las URLs encontradas
     */
    public static ArrayList<String> encontrar(String texto) {
        // Los URLs acaban cuando hay un espacio, por lo que se divide la línea en "palabras"
        ArrayList<String> palabras = new ArrayList<String>(Arrays.asList(texto.split(" ")));
        ArrayList<String> out = new ArrayList<String>();

        for (int i = 0; i < palabras.size(); i++) {
            String p = palabras.get(i);

            if (p.startsWith("http://") || p.startsWith("https://"))
                out.add(p);
        }
        
        return out;
    }

    /**
     * Devuelve todas las URLs diferentes enviadas en tweets de un usuario determinado.
     * 
     * @param filename  El nombre del archivo a procesar
     * @param username  El nombre de usuario cuyos URLs queremos obtener
     * @return          Listado de URLs encontradas en el fichero
     */
    public static ArrayList<String> porUsuario(String filename, String username) {
        ArrayList<String> out = new ArrayList<String>();
        try {
            Scanner in = new Scanner(new FileReader(filename));

            // Estadísticas
            int tweetsUsuario = 0;
            int repetidas = 0;

            in.nextLine(); //cabecera
            
            while (in.hasNext()) {
                String linea = in.nextLine();
                String usuario = linea.split(";")[2];
                if (usuario.equals(username)) {
                    tweetsUsuario++;
                    ArrayList<String> urls = encontrar(linea.split(";")[3]);

                    for (int i = 0; i < urls.size(); i++) {
                        // Si el URL ya está en la lista out, no se vuelve a añadir, sino que
                        // se aumenta el contador de URLs repetidas
                        if (out.contains(urls.get(i)))
                            repetidas++;
                        else
                            out.add(urls.get(i));
                    }
                }
            }

            in.close();

            ProcesarTweets.separador();
			System.out.println("(Tarea 7) Estadísticas del procesado de URLs por usuario:");
			System.out.printf("Nombre del usuario = %s\n", username);
			System.out.printf("Número de tweets publicados por el usuario = %d\n", tweetsUsuario);
			System.out.printf("Número de URLs diferentes encontradas = %d\n", out.size());
			System.out.printf("Número de URLs repetidas = %d\n", repetidas);
			ProcesarTweets.separador();

        } catch (IOException ioex) {
            System.out.println("Error en Direcciones.porUsuario" + ioex);
        }
        return out;
    }

    /**
     * Devuelve todas las URLs diferentes encontradas en un rango determinado de meses.
     * El rango se trata de forma cíclica, es decir, el rango 12-1 incluye a diciembre y enero.
     * 
     * @param filename  El nombre del archivo a procesar
     * @param inicio    El mes que inicia el rango
     * @param fin       El mes que finaliza el rango
     * @return          ArrayList conteniendo las URLs correspondientes
     */
    public static ArrayList<String> porMeses(String filename, int inicio, int fin) {
        ArrayList<String> out = new ArrayList<String>();
        try {
            Scanner in = new Scanner(new FileReader(filename));

            // Estadística
            int repetidas = 0;

            boolean[] meses = new boolean[12];

            // Si el primer mes es igual al segundo, el rango cubre un solo mes
            if (inicio == fin) {
                for (int i = 0; i < meses.length; i++) {
                    meses[i] = i == inicio - 1; // true cuando i está en la posición del mes de inicio
                }
            }

            // Si uno de los dos está fuera de rango, error
            else if (inicio < 1 || inicio > 12 || fin < 0 || fin > 12) {
                System.out.println("Error en Direcciones.porMeses: Rango de meses inválido");
                return out;
            }

            // En el resto de casos, se hace un bucle para ir comprobando qué meses están incluidos en el rango
            else {
                // Inicialización a false
                for (int i = 0; i < meses.length; i++) {
                    meses[i] = false;
                }

                for (int i = inicio; i != fin; i++) {
                    
                    meses[i - 1] = true;
                    
                    // Fin de año: se vuelve a enero en caso de que se haya llegado a diciembre
                    if (i == 12)
                        i = 0;
                }
            }

            in.nextLine(); //cabecera
            
            while (in.hasNext()) {
                String linea = in.nextLine();
                String fecha = linea.split(";")[0];
                int mes = Integer.parseInt(fecha.split("-")[1]);

                if (meses[mes - 1]) {
                    ArrayList<String> urls = encontrar(linea.split(";")[3]);

                    for (int i = 0; i < urls.size(); i++) {
                        // Si el URL ya está en la lista out, no se vuelve a añadir, sino que
                        // se aumenta el contador de URLs repetidas
                        if (out.contains(urls.get(i)))
                            repetidas++;
                        else
                            out.add(urls.get(i));
                    }
                }
            }

            in.close();

            ProcesarTweets.separador();
			System.out.println("(Tarea 8) Estadísticas del procesado de URLs por periodo de tiempo:");
			System.out.printf("Rango de meses = %d - %d\n", inicio, fin);
			System.out.printf("Número de URLs diferentes encontradas = %d\n", out.size());
			System.out.printf("Número de URLs repetidas = %d\n", repetidas);
			ProcesarTweets.separador();

        } catch (IOException ioex) {
            System.out.println("Error en Direcciones.porMeses" + ioex);
        }
        return out;
    }

    /**
     * Abre una dirección web en el navegador
     * <p>
     * Obtenido de la documentación oficial de Java:
     * <ul>
     * <li> https://docs.oracle.com/javase/7/docs/api/java/awt/Desktop.html </li>
     * <li> https://docs.oracle.com/javase/7/docs/api/java/net/URI.html </li> </ul>
     * ...
     * 
     * @param direccion  La dirección web a abrir
     */
    public static void abrirURL(String direccion) {
        ProcesarTweets.separador();
        System.out.println("(Tarea 9) Abriendo dirección web " + direccion);
        
        // Separar protocolo de resto de dirección como tal
        String protocolo = direccion.split("://")[0];
        direccion = direccion.split("://")[1];

        // Obtener host y archivo
        String host = direccion.split("/")[0];
        String archivo = "";

        String[] direcc = direccion.split("/");

        // Unir todos los String de direcc en archivo
        for (int i = 1; i < direcc.length; i++) {
            archivo += "/";
            archivo += direcc[i];
        }

        // Abrir URL
        try {
            URI uri = new URI(protocolo, host, archivo, null);
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(uri);
        } catch (URISyntaxException | IOException ex) {
            System.out.println("Error en Direcciones.abrirURL:" + ex);
        }
    }
}
