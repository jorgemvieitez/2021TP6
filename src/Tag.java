/*
Nombre del fichero: Tag.java
Autor: Jorge Miguel Moreno Vieitez
Titulación/Grupo: 912
Fecha: 07/12/2021 - 17/12/2021
*/

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Registro que almacena el valor textual de un tag y el número de veces que ha
 * aparecido en un texto
 */
public class Tag {
    private String texto;
    private int ocurrencias;

    // Constructor

    /**
     * @param texto       Contenido del tag, incluyendo la almohadilla
     * @param ocurrencias Cantidad de veces que ha aparecido el tag en un texto
     */
    public Tag(String texto, int ocurrencias) {
        this.texto = texto;
        this.ocurrencias = ocurrencias;
    }

    // Getters y setters

    /**
     * Setter del valor Tag.texto
     * 
     * @param t Valor que se le quiere dar a texto
     */
    public void setTexto(String t) {
        texto = t;
    }

    /**
     * Getter del valor Tag.texto
     * 
     * @return Valor de texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Setter del valor Tag.ocurrencias
     * 
     * @param o Valor que se le quiere dar a ocurrencias
     */
    public void setOcurrencias(int o) {
        ocurrencias = o;
    }

    /**
     * Getter del valor Tag.ocurrencias
     * 
     * @return Valor de ocurrencias
     */
    public int getOcurrencias() {
        return ocurrencias;
    }

    // Métodos

    /**
     * Añade cierta cantidad de ocurrencias al Tag
     * 
     * @param cantidad La cantidad de ocurrencias a añadir
     */
    public void sumarOcurrencias(int cantidad) {
        ocurrencias += cantidad;
    }

    /**
     * Devuelve un String que representa el Tag y su número de ocurrencias
     * 
     * @return El tag expresado en formato de texto
     */
    public String toString() {
        return String.format("%s - %d ocurrencias", texto, ocurrencias);
    }

    // Métodos estáticos

    /**
     * Devuelve un vector de Tags (con ocurrencias listadas) a partir de una lista
     * de tags y el nombre
     * del archivo en el que buscar
     * <p>
     * Advertencia: función devuelve null si hay un IOException
     * 
     * @param filename Nombre del fichero de tweets filtrados
     * @param tags     Listado de tags diferentes encontrados en el fichero
     * @return Vector de tags y sus ocurrencias, o puntero null en caso de
     *         IOException
     */
    public static Tag[] ocurrenciaTagsFichero(String filename, ArrayList<String> tags) {
        try {
            Scanner in = new Scanner(new FileReader(filename));
            in.nextLine(); // Cabecera

            // Crear vector de tags a partir del arraylist, inicializándolo con 0
            // ocurrencias cada uno
            Tag[] out = new Tag[tags.size()];
            for (int i = 0; i < tags.size(); i++) {
                out[i] = new Tag(tags.get(i), 0);
            }

            while (in.hasNext()) {
                // Obtener los tags que hay en cada línea
                String linea = in.nextLine();
                String texto = linea.split(";")[3];
                String[] tagsLinea = ProcesarTweets.extraccionTagLinea(texto);

                // Por cada tag en la línea...
                for (int i = 0; i < tagsLinea.length; i++) {

                    // ...comprueba qué tag es y añade una ocurrencia
                    for (int j = 0; j < out.length; j++) {
                        if (tagsLinea[i].equalsIgnoreCase(out[j].getTexto())) {
                            out[j].sumarOcurrencias(1);
                            break; // Se usa aquí un break para mantener la simplicidad del for
                                   // y evitar realizar un número excesivo de iteraciones
                        }
                    }
                }
            }

            in.close();
            return out;

        } catch (IOException e) {
            System.out.println("Error en ocurrenciaTagsFichero: " + e);
            return null;
        }
    }

    /**
     * Ordena los tags del vector de entrada en función del número de ocurrencias,
     * de mayor a menor
     * 
     * @param tags Vector de tags sin ordenar
     * @return Vector de tags ordenado
     */
    public static Tag[] ordenarTagsPorOcurrencias(Tag[] tags) {
        boolean cambiosHechos; // Necesario para saber si hace falta repetir la iteración
        do {
            cambiosHechos = false;
            for (int i = 0; i < tags.length - 1; i++) {
                // Si un elemento y el siguiente están desordenados...
                if (tags[i].getOcurrencias() < tags[i + 1].getOcurrencias()) {
                    // Cambiar orden de elementos
                    Tag temp = tags[i];
                    tags[i] = tags[i + 1];
                    tags[i + 1] = temp;
                    cambiosHechos = true;
                }
            }
            // Aún al estar todo ordenado, el algoritmo necesita hacer una iteración extra
            // en la que no reordene ninguna pareja para comprobarlo
        } while (cambiosHechos);

        return tags;
    }

    /**
     * Escribe por pantalla los n tags más populares en el vector dado
     * 
     * @param tags Vector ordenado de tags
     * @param n    Número de tags que mostrar
     */
    public static void escribeTagPopulares(Tag[] tags, int n) {
        System.out.printf("(Tarea 6) Los %d tags más populares son:\n", n);

        for (int i = 0; i < n; i++) {
            System.out.println(" - " + tags[i].toString());
        }
    }
}