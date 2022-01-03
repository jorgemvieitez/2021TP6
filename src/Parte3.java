import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Parte3 {
    /**
     * Escribe por pantalla los tags más utilizados en un mes concreto a partir
     * de la información almacenada en una colección de ficheros de tweets
     * 
     * @param files Vector de nombres de ficheros (los cuales contienen tweets)
     * @param month Mes de referencia
     */
    public static void analisisTagMensual(String[] files, int month) {
        // Constantes, para poder alterar estos valores con facilidad en caso de ser
        // necesario
        final String CABECERA = "Fecha;Cliente;Autor;Tweet";
        final String ARCHIVO_TEMPORAL = "tmp_file.txt";

        try {
            // Se crea un archivo temporal para unir todos los tweets que se van a emplear;
            // es decir, todos los que pertenezcan al mes indicado
            PrintWriter temp = new PrintWriter(new FileWriter(ARCHIVO_TEMPORAL));
            temp.println(CABECERA);

            for (int f = 0; f < files.length; f++) {
                String file = files[f];
                System.out.println("Archivo = " + file);
                int twMes = 0;

                Scanner fScanner = new Scanner(new FileReader(file));
                fScanner.nextLine(); // Cabecera

                while (fScanner.hasNext()) {
                    // Pasa las líneas del mes correspondiente al archivo final
                    String linea = fScanner.nextLine();
                    String fecha = linea.split(";")[0];
                    int mes = Integer.parseInt(fecha.split("-")[1]);
                    if (mes == month) {
                        temp.println(linea);
                        twMes++;
                    }
                }
                System.out.printf("Tweets en el mes %d = %d\n", month, twMes);
                fScanner.close();
            }
            temp.close();

            // TODO: cuenta esto como ficheros de entrada?? comprobar
            ArrayList<String> tags = ProcesarTweets.extraccionTagsFichero(ARCHIVO_TEMPORAL);
            Tag[] tags_ocurrencias = Tag.ocurrenciaTagsFichero(ARCHIVO_TEMPORAL, tags);
            Tag[] tags_ordenados = Tag.ordenarTagsPorOcurrencias(tags_ocurrencias);
            Tag.escribeTagPopulares(tags_ordenados, 5);

        } catch (IOException e) {
            System.out.println("Error en analisisTagMensual: " + e);
        }
    }

    public static void main(String[] args) {
    }
}
