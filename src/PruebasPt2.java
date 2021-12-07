import java.util.ArrayList;

public class PruebasPt2 {
    public static void main(String[] args) {
        // Se utiliza lo de la tarea 4 para obtener los tags disponibles
        ArrayList<String> tags = ProcesarTweets.extraccionTagsFichero("data/out3.txt");

        // Tarea 6
        Tag[] a = Tag.ocurrenciaTagsFichero("data/out3.txt", tags);
        Tag[] b = Tag.ordenarTagsPorOcurrencias(a);
        Tag.escribeTagPopulares(b, 10);
    }
}
