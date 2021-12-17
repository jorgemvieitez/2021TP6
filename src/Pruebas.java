import java.util.ArrayList;

public class Pruebas {
    public static void print(ArrayList<String> ar) {
        for (String str : ar) {
            System.out.printf(" - %s\n", str);
        }
    }

    public static void print(ArrayList<String> ar, int n) {
        for (int i = 0; i < n && i < ar.size(); i++) {
            System.out.printf(" - %s\n", ar.get(i));
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) 
            System.out.println("you need args idiot");
        else if (args[0].equals("1")) {
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
        } else if (args[0].equals("2")) {
            // Se utiliza lo de la tarea 4 para obtener los tags disponibles
            ArrayList<String> tags = ProcesarTweets.extraccionTagsFichero("data/out3.txt");

            // Tarea 6
            Tag[] a = Tag.ocurrenciaTagsFichero("data/out3.txt", tags);
            Tag[] b = Tag.ordenarTagsPorOcurrencias(a);
            Tag.escribeTagPopulares(b, 10);

            // Tarea 7
            ArrayList<String> c = Direcciones.porUsuario("data/out3.txt", "RTVCes");
            print(c, 10);

            // Tarea 8
            ArrayList<String> d = Direcciones.porMeses("data/out3.txt", 10, 10);
            print(d, 10);

            // Tarea 9
            Direcciones.abrirURL("https://example.com/hello");
        } else {
            System.out.println(args[0] + " not valid, sorry not sorry");
        }
    }
}
