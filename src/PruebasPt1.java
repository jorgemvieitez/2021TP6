import java.util.ArrayList;

public class PruebasPt1 {
    public static void main(String[] args) {
        // Tarea 1
        ProcesarTweets.filtroContenido("data/tweets-100tw.txt", "data/out1.txt");

        // Tarea 2
        ProcesarTweets.filtroEstructura("data/out1.txt", "data/out2.txt");

        // Tarea 3
        ProcesarTweets.compactaBlancos("data/out2.txt", "data/out3.txt");

        // Tarea 4
        ArrayList<String> tags = ProcesarTweets.extraccionTagsFichero("data/out3.txt");
        for (int i = 0; i < tags.size(); i++) {
            System.out.println(tags.get(i));
        }
    }
}
