import java.util.ArrayList;

public class PruebasPt2 {
    public static void printAL(ArrayList<String> ar) {
        for (String str : ar) {
            System.out.printf(" - %s\n", str);
        }
    }
    public static void main(String[] args) {
        // Se utiliza lo de la tarea 4 para obtener los tags disponibles
        ArrayList<String> tags = ProcesarTweets.extraccionTagsFichero("data/out3.txt");

        // Tarea 6
        Tag[] a = Tag.ocurrenciaTagsFichero("data/out3.txt", tags);
        Tag[] b = Tag.ordenarTagsPorOcurrencias(a);
        //Tag.escribeTagPopulares(b, 10);

        // Tarea 7
        ArrayList<String> c = Direcciones.porUsuario("data/out3.txt", "RTVCes");
        //printAL(c);

        // Tarea 8
        ArrayList<String> d = Direcciones.porMeses("data/out3.txt", 13, 10);
        // printAL(d);

        // Tarea 9
        Direcciones.abrirURL("https://example.com/hello");
    }
}
