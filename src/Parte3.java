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

    /**
     * Comprueba cuántas veces un tag está contenido en un conjunto de Strings.
     * Basado en existeTag
     * 
     * @param tags Vector de tags (Strings)
     * @param tag  Tag a buscar
     * @return Número de veces que aparece el tag especificado en el conjunto
     */
    public static int cuentaTag(String[] tags, String tag) {
        int num = 0;
        for (int i = 0; i < tags.length; i++) {
            if (tag.equalsIgnoreCase(tags[i])) {
                num++;
            }
        }
        return num;
    }

    /**
     * Escribe por pantalla un histograma que contiene cuánto se usó un determinado
     * tag
     * en diferentes meses.
     * 
     * @param files Vector de nombres de ficheros (los cuales contienen tweets)
     * @param tag   Tag a buscar
     */
    public static void histogramaTag(String[] files, String tag) {
        // Aquí se almacenan las ocurrencias por mes
        int[] ocurrencias = new int[12];
        for (int i = 0; i < 12; i++) {
            ocurrencias[i] = 0;
        }

        // Leer los archivos y separar ocurrencias por meses
        try {
            for (int i = 0; i < files.length; i++) {
                Scanner file = new Scanner(new FileReader(files[i]));

                file.nextLine(); // Cabecera

                while (file.hasNext()) {
                    String linea = file.nextLine();
                    int mes = Integer.parseInt(linea.split(";")[0].split("-")[1]);
                    String tweet = linea.split(";")[3];
                    String[] tags = ProcesarTweets.extraccionTagLinea(tweet);
                    ocurrencias[mes - 1] += cuentaTag(tags, tag);
                }
            }
        } catch (IOException e) {
            System.out.println("Error en histogramaTag: " + e);
            return;
        }

        // Mostrar histograma por pantalla
        final String[] MESES = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Agosto", "Septiembre",
                "Octubre", "Noviembre", "Diciembre" };
    }

    public static void main(String[] args) {
        String[] files = { "data/outpt1.txt" };
        histogramaTag(files, "#lapalmaeruption");
    }
}
