/*
Nombre del fichero: ProcesarTweets.java
Autor: Jorge Miguel Moreno Vieitez
Titulación/Grupo: 912
Fecha: 25/11/2021 - ??/01/2022
Histórico de cambios: ???
*/
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class ProcesarTweets {

	/**
	 * filtroContenido: elimina del fichero los caracteres no deseables
	 * 
	 * @param input   Nombre del fichero de entrada a procesar
	 * @param output  Nombre del fichero resultado
	 */
	public static void filtroContenido(String input, String output) {
		try {
			Scanner in = new Scanner(new FileReader(input));
			PrintWriter out = new PrintWriter(new FileWriter(output));
			int cFiltrados = 0; // contador de carácteres filtrados

			while (in.hasNext()) {
				String linea = in.nextLine();
				String lineaFiltro = ""; // La línea una vez se ha aplicado el filtro

				for (int i = 0; i < linea.length(); i++) {
					char chr = linea.charAt(i);

					// Filtro de carácteres
					if (

						// Carácteres sueltos
						chr == '#' || chr == '_' || chr == '@' || chr == ' ' ||

						// A-Z y a-z
						Character.isAlphabetic(chr) ||

						// ',', '-' , '.', '/', ':', ';', y 0-9
						((int) chr >= (int) ',' && (int) chr <= (int) ';')

					) {
						lineaFiltro += chr;
					} else {
						// Carácter es filtrado
						cFiltrados++;
						lineaFiltro += ' ';
					}
				}
				out.println(lineaFiltro);
			}
			in.close();
			out.close();
			System.out.println("------------------------------------------------");
			System.out.println("(Tarea 1) Estadísticas del filtro por contenido:");
			System.out.printf("Número de caracteres sustituidos = %d\n", cFiltrados);
			System.out.println("------------------------------------------------");
		} catch (IOException ioex) {
			System.out.println("Error en filtroContenido - IOException: " + ioex);
		}
	}

	/**
	 * filtroEstructura: elimina del fichero los tweets que no constan de 4 campos
	 * de información
	 * 
	 * @param input   Nombre del fichero de entrada a procesar
	 * @param output  Nombre de fichero de resultado
	 */
	public static void filtroEstructura(String input, String output) {
		try {
			Scanner in = new Scanner(new FileReader(input));
			PrintWriter out = new PrintWriter(new FileWriter(output));
			int cFiltrados = 0;

			out.println(in.nextLine()); // la cabecera no es necesaria filtrar, por lo que se pasa tal y como está

			// El código de este método es muy sencillo, por lo que no es necesario documentarlo
			while (in.hasNext()) {
				String linea = in.nextLine();
				String[] campos = linea.split(";");
				if (campos.length == 4)
					out.println(linea);
				else
					cFiltrados++;
			}
			in.close();
			out.close();

			System.out.println("------------------------------------------------");
			System.out.println("(Tarea 2) Estadísticas del filtro por estructura:");
			System.out.printf("Número de tweets incorrectos eliminados = %d\n", cFiltrados);
			System.out.println("------------------------------------------------");
		} catch (IOException ioex) {
			System.out.println("Error en filtroEstructura - IOException: " + ioex);
		}
	}

	/**
	 * compactaBlancos: sustituye dos o más caracteres ' ' consecutivos por una sola
	 * ocurrencia
	 * 
	 * @param input   Nombre del fichero de entrada a procesar
	 * @param output  Nombre del fichero de resultado
	 */
	public static void compactaBlancos(String input, String output) {
		try {
			Scanner in = new Scanner(new FileReader(input));
			PrintWriter out = new PrintWriter(new FileWriter(output));
			int cFiltrados = 0;

			out.println(in.nextLine()); // la cabecera no es necesaria filtrar, por lo que se pasa tal y como está 

			while (in.hasNext()) {
				String linea = in.nextLine();
				boolean esEspacio = false; // Si el último carácter ha sido un espacio, estará en true

				for (int i = 0; i < linea.length(); i++) {
					if (linea.charAt(i) == ' ') {
						if (!esEspacio) {
							out.print(linea.charAt(i));
							esEspacio = true;
						}
						// Si el último carácter ha sido un espacio, este se ignora
						else {
							cFiltrados++;
						}
					}
					else {
						out.print(linea.charAt(i));
						esEspacio = false;
					}
				}
				out.println();
			}
			in.close();
			out.close();

			System.out.println("------------------------------------------------");
			System.out.println("(Tarea 3) Estadísticas del filtro de secuencias:");
			System.out.printf("Número de espacios en blanco compactados = %d\n", cFiltrados);
			System.out.println("------------------------------------------------");
		} catch (IOException ioex) {
			System.out.println("Error en filtroEstructura - IOException: " + ioex);
		}
	}

	/**
	 * numTagLinea: devuelve el número de tags encontrados en la línea de texto de
	 * entrada
	 * 
	 * @param linea  Línea de texto a procesar
	 * @return 		 El número de tags encontrados
	 */
	public static int numTagLinea(String linea) {
		int numTags = 0;

		boolean esTag = false; // Se volverá true si está actualmente leyendo un tag
		for (int i = 0; i < linea.length(); i++) {
			char c = linea.charAt(i);

			// Un tag comienza en '#'...
			if (c == '#' && !esTag) {
				esTag = true;
				numTags ++;
			// ...y sus carácteres son alfanuméricos o '_'
			} else if (!Character.isLetterOrDigit(c) && c != '_') {
				esTag = false;
			}
		}

		return numTags;
	}

	/**
	 * extraccionTagLinea: devuelve los tag (como String) encontrados en la línea de
	 * texto de entrada
	 * 
	 * @param linea  Línea de texto a procesar
	 * @return 		 Los tags encontrados (o el valor null si no encuentra ninguno)
	 */
	public static String[] extraccionTagLinea(String linea) {
		String[] tags = new String[numTagLinea(linea)];

		String tagActual = "";
		int tagsEncontrados = 0;
		for (int i = 0; i < linea.length(); i++) {
			char c = linea.charAt(i);

			// Un tag comienza en '#'...
			if (c == '#' && tagActual.length() == 0) {
				tagActual += '#';
			// ...y sus carácteres son alfanuméricos o '_'
			} else if (!Character.isLetterOrDigit(c) && c != '_' && tagActual.length() != 0) {
				tags[tagsEncontrados] = tagActual;
				tagActual = "";
				tagsEncontrados += 1;
			// Añadir carácter al tag actual si existe
			} else if (tagActual.length() > 0) {
				tagActual += c;
			}
		}

		if (tagActual.length() != 0) {
			tags[tagsEncontrados] = tagActual;
		}

		if (tags.length == 0) {
			return null;
		} 
		return tags;
	}

	/**
	 * existeTag: chequea si un tag está contenido en un conjunto de tags
	 * 
	 * @param tags  Estructura que contiene el conjunto de tags
	 * @param tag   Tag a buscar
	 * @return 	    Si el tag especificado está contenido en el conjunto
	 */
	public static boolean existeTag(ArrayList<String> tags, String tag) {
		// No se usa .contains() porque no ignora la capitalización del tag
		for (int i = 0; i < tags.size(); i++) {
			if (tag.equalsIgnoreCase(tags.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * extraccionTagsFichero: devuelve todos los tags diferentes encontrados en un
	 * fichero de texto de entrada
	 * 
	 * @param input  Nombre del fichero a procesar
	 * @return 		 Estructura ArrayList que contiene los tags
	 */
	public static ArrayList<String> extraccionTagsFichero(String input) {
		try {
			// Aquí se almacenarán todos los tags del archivo
			ArrayList<String> tags = new ArrayList<String>();
			Scanner in = new Scanner(new FileReader(input));
			
			in.nextLine(); // la cabecera no se debe comprobar, por lo que se ignora
	
			while (in.hasNext()) {
				// Se extraen los tags de la línea...
				String linea = in.nextLine();
				String[] tags_linea = extraccionTagLinea(linea.split(";")[3]);

				if (tags_linea == null)
					continue;

				// Se comprueba, uno por uno, si están en tags
				// Si no lo están, se añaden a tags
				for (int i = 0; i < tags_linea.length; i++) {
					if (!existeTag(tags, tags_linea[i])) {
						tags.add(tags_linea[i]);
					}
				}
			}
			in.close();

			System.out.println("------------------------------------------------");
			System.out.println("(Tarea 4) Estadísticas de la extracción de tags:");
			System.out.printf("Número de tags diferentes extraídos = %d\n", tags.size());
			System.out.println("------------------------------------------------");
			return tags;
		} catch (IOException ioex) {
			System.out.println("Error en extraccionTagsFichero - IOException: " + ioex);
			return null;
		}
	}

	// Función de pruebas - no requiere documentación
	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		System.out.println("Nombre del fichero fuente (tweets): ");
		String nombreIn = entrada.nextLine();
		System.out.println("Nombre del fichero salida: ");
		String nombreOut = entrada.nextLine();

		filtroContenido(nombreIn, "temp1.txt");
		filtroEstructura("temp1.txt", "temp2.txt");
		compactaBlancos("temp2.txt", nombreOut);

		ArrayList<String> tags = extraccionTagsFichero(nombreOut);
		if (tags.size() > 0) {
			System.out.println("------------------------------------------------");
			System.out.println("Número de tags diferentes: " + tags.size());

			for (int i = 0; i < tags.size(); i++) {
				System.out.printf("%s, ",tags.get(i));
			}
			System.out.println();
		}

		entrada.close();

	}

}