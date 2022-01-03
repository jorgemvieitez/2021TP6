/*
TP6_EntregaTweets.java : Proyecto TP6 de Fundamentos de Informática (programa principal)
Fecha: Curso 2021-2022 (1ª convocatoria)
Autor: Jorge Moreno Vieitez (método principal dado por el profesor Pedro Álvarez)
Titulación/Grupo: 912 (Grado en Ingenierías y Servicios de Telecomunicación)
*/

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

import java.net.URI;     // Nota: he usado java.net.URI en vez de java.net.URL
import java.net.URISyntaxException;
import java.awt.Desktop;

public class TP6_EntregaTweets {
	
	/**
	 * Escribe por pantalla una línea separadora
	 */
	public static void separador() {
		System.out.println("------------------------------------------------");
	}

	/**
	 * Elimina del fichero los caracteres no deseables
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
			separador();
			System.out.println("(Tarea 1) Estadísticas del filtro por contenido:");
			System.out.printf("Número de caracteres sustituidos = %d\n", cFiltrados);
			separador();
		} catch (IOException ioex) {
			System.out.println("Error en filtroContenido - IOException: " + ioex);
		}
	}

	/**
	 * Elimina del fichero los tweets que no constan de 4 campos de información
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

			separador();
			System.out.println("(Tarea 2) Estadísticas del filtro por estructura:");
			System.out.printf("Número de tweets incorrectos eliminados = %d\n", cFiltrados);
			separador();
		} catch (IOException ioex) {
			System.out.println("Error en filtroEstructura - IOException: " + ioex);
		}
	}

	/**
	 * Reemplaza las ocurrencias de dos o más espacios en blanco seguidos
	 * con un solo carácter de espacio
	 * 
	 * @param input   Nombre del fichero de entrada a procesar
	 * @param output  Nombre del fichero de resultado
	 */
	public static void compactaBlancos(String input, String output) {
		try {
			Scanner in = new Scanner(new FileReader(input));
			PrintWriter out = new PrintWriter(new FileWriter(output));
			int cFiltrados = 0;
			int secCompactadas = 0;

			out.println(in.nextLine()); // la cabecera no es necesaria filtrar, por lo que se pasa tal y como está 

			while (in.hasNext()) {
				String linea = in.nextLine();
				int lSecuencia = 0; // Longitud de la secuencia de espacios actual

				for (int i = 0; i < linea.length(); i++) {
					if (linea.charAt(i) == ' ') {
						if (lSecuencia == 0) {
							out.print(linea.charAt(i));
						}
						// Si el último carácter ha sido un espacio, este se ignora
						else {
							if (lSecuencia == 1)   // Se tiene que comprobar en el segundo carácter, ya que en
								secCompactadas++;  // caso contrario puede llevar a falsos positivos/negativos
							cFiltrados++;
						}
						lSecuencia++;
					}
					else {
						out.print(linea.charAt(i));
						lSecuencia = 0;
					}
				}
				out.println();
			}
			in.close();
			out.close();

			separador();
			System.out.println("(Tarea 3) Estadísticas del filtro de secuencias:");
			System.out.printf("Número de espacios en blanco compactados = %d\n", cFiltrados);
			System.out.printf("Número de secuencias compactadas = %d\n", secCompactadas);	
			separador();
		} catch (IOException ioex) {
			System.out.println("Error en filtroEstructura - IOException: " + ioex);
		}
	}

	/**
	 * Devuelve el número de tags encontrados en la línea de texto dada
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
	 * Devuelve los tags (String) encontrados en la línea dada
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
	 * Comprueba si un tag está contenido en un conjunto de Strings
	 * 
	 * @param tags  Estructura que contiene el conjunto de tags
	 * @param tag   Tag a buscar
	 * @return 	    Si el tag especificado está contenido en el conjunto
	 */
	public static boolean existeTag(ArrayList<String> tags, String tag) {
		// No se usa .contains() porque no ignora las mayúsculas del tag
		for (int i = 0; i < tags.size(); i++) {
			if (tag.equalsIgnoreCase(tags.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Devuelve todos los tags diferentes encontrados en un fichero de texto de entrada
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

			separador();
			System.out.println("(Tarea 4) Estadísticas de la extracción de tags:");
			System.out.printf("Número de tags diferentes extraídos = %d\n", tags.size());
			separador();
			return tags;
		} catch (IOException ioex) {
			System.out.println("Error en extraccionTagsFichero - IOException: " + ioex);
			return new ArrayList<String>();
		}
	}

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
                String[] tagsLinea = extraccionTagLinea(texto);

                if (tagsLinea == null)
                    continue;

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
    public static Tag[] ordernarTagsPorOcurrencias(Tag[] tags) {
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
        System.out.printf("Los %d tags más populares son:\n", n);

        for (int i = 0; i < n; i++) {
            System.out.println(" - " + tags[i].toString());
        }
    }


    /**
     * Devuelve todas las URLs encontradas en una línea de texto,
     * incluyendo repetidas.
     * @param texto  La línea de texto a procesar
     * @return       ArrayList con las URLs encontradas
     */
    public static ArrayList<String> encontrarURLs(String texto) {
        // Los URLs acaban cuando hay un espacio, por lo que se divide la línea en "palabras"
        String[] palabras = texto.split(" ");
        ArrayList<String> urls = new ArrayList<String>();

        for (int i = 0; i < palabras.length; i++) {
            String p = palabras[i];

            if (p.startsWith("http://") || p.startsWith("https://"))
                urls.add(p);
        }
        
        return urls;
    }

    /**
     * Devuelve todas las URLs diferentes enviadas en tweets de un usuario determinado.
     * 
     * @param input  El nombre del archivo a procesar
     * @param userName  El nombre de usuario cuyos URLs queremos obtener
     * @return          Listado de URLs encontradas en el fichero
     */
    public static ArrayList<String> URLsUsuario(String input, String userName) {
        ArrayList<String> out = new ArrayList<String>();
        try {
            Scanner in = new Scanner(new FileReader(input));

            // Estadísticas
            int tweetsUsuario = 0;
            int repetidas = 0;

            in.nextLine(); //cabecera
            
            while (in.hasNext()) {
                String linea = in.nextLine();
                String usuario = linea.split(";")[2];
                if (usuario.equals(userName)) {
                    tweetsUsuario++;
                    ArrayList<String> urls = encontrarURLs(linea.split(";")[3]);

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

            separador();
			System.out.println("(Tarea 7) Estadísticas del procesado de URLs por usuario:");
			System.out.printf("Nombre del usuario = %s\n", userName);
			System.out.printf("Número de tweets publicados por el usuario = %d\n", tweetsUsuario);
			System.out.printf("Número de URLs diferentes encontradas = %d\n", out.size());
			System.out.printf("Número de URLs repetidas = %d\n", repetidas);
			separador();

        } catch (IOException ioex) {
            System.out.println("Error en porUsuario" + ioex);
        }
        return out;
    }

    /**
     * Devuelve todas las URLs diferentes encontradas en un rango determinado de meses.
     * El rango se trata de forma cíclica, es decir, el rango 12-1 incluye a diciembre y enero.
     * 
     * @param input  El nombre del archivo a procesar
     * @param inicio    El mes que inicia el rango
     * @param fin       El mes que finaliza el rango
     * @return          ArrayList conteniendo las URLs correspondientes
     */
    public static ArrayList<String> URLsMeses(String input, int inicio, int fin) {
        ArrayList<String> out = new ArrayList<String>();
        try {
            Scanner in = new Scanner(new FileReader(input));

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
                System.out.println("Error en porMeses: Rango de meses inválido");
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
                    ArrayList<String> urls = encontrarURLs(linea.split(";")[3]);

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

            separador();
			System.out.println("(Tarea 8) Estadísticas del procesado de URLs por periodo de tiempo:");
			System.out.printf("Rango de meses = %d - %d\n", inicio, fin);
			System.out.printf("Número de URLs diferentes encontradas = %d\n", out.size());
			System.out.printf("Número de URLs repetidas = %d\n", repetidas);
			separador();

        } catch (IOException ioex) {
            System.out.println("Error en porMeses" + ioex);
        }
        return out;
    }

    /**
     * Abre una dirección web en el navegador
     * <p>
     * Obtenido de la documentación oficial de Java: <ul>
     * <li> https://docs.oracle.com/javase/7/docs/api/java/awt/Desktop.html </li>
     * <li> https://docs.oracle.com/javase/7/docs/api/java/net/URI.html </li> </ul>
     * ____________________
     * @param direccionWeb  La dirección web a abrir
     */
    public static void abrirEnlaceWeb(String direccionWeb) {
        separador();
        System.out.println("(Tarea 9) Abriendo dirección web " + direccionWeb);
        
        // Separar protocolo de resto de dirección como tal
        String protocolo = direccionWeb.split("://")[0];
        direccionWeb = direccionWeb.split("://")[1];

        // Obtener host y archivo
        String host = direccionWeb.split("/")[0];
        String archivo = "";

        String[] direcc = direccionWeb.split("/");

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
            System.out.println("Error en abrirURL:" + ex);
        }
    }

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

            ArrayList<String> tags = extraccionTagsFichero(ARCHIVO_TEMPORAL);
            Tag[] tags_ocurrencias = ocurrenciaTagsFichero(ARCHIVO_TEMPORAL, tags);
            Tag[] tags_ordenados = ordernarTagsPorOcurrencias(tags_ocurrencias);
            escribeTagPopulares(tags_ordenados, 5);

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
     * tag en diferentes meses.
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
                    String[] tags = extraccionTagLinea(tweet);
                    ocurrencias[mes - 1] += cuentaTag(tags, tag);
                }
            }
        } catch (IOException e) {
            System.out.println("Error en histogramaTag: " + e);
            return;
        }

        // Mostrar histograma por pantalla

        final String[] MESES = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
                "Octubre", "Noviembre", "Diciembre" };
        System.out.println("Tag: " + tag);
        for (int i = 0; i < 12; i++) {
            System.out.printf("%s (%d): ", MESES[i], ocurrencias[i]);

            for (int j = 0; j < ocurrencias[i]; j++)
                if (j % 5 == 1) // Como es un asterisco por cada 5 y redondeando hacia arriba,
                                // cada vez que sobrepasa un múltiplo de 5 se le añade 1 asterisco
                                // al histograma
                    System.out.print("*");

            System.out.println();
        }
    }

	public static void main(String[] args) {
		
		Scanner entrada = new Scanner(System.in);
		System.out.println("Nombre del fichero fuente (tweets): ");
		String nombreIn = entrada.nextLine();
		System.out.println("Nombre del fichero salida: ");
		String nombreOut = entrada.nextLine();
		
		System.out.println("----------------------------------------------------");
		System.out.println("---- PRIMERA PARTE DEL TRABAJO");
		System.out.println("----------------------------------------------------");
		
		System.out.println("----------------------------------------------------");
		System.out.println("---- TAREA 1 ");
		System.out.println("----------------------------------------------------");	
		filtroContenido(nombreIn,"temp1.txt");
		
		System.out.println("----------------------------------------------------");
		System.out.println("---- TAREA 2 ");
		System.out.println("----------------------------------------------------");
		filtroEstructura("temp1.txt", "temp2.txt");
		
		System.out.println("----------------------------------------------------");
		System.out.println("---- TAREA 3 ");
		System.out.println("----------------------------------------------------");
		compactaBlancos("temp2.txt",nombreOut);

		System.out.println("----------------------------------------------------");
		System.out.println("---- TAREA 4 ");
		System.out.println("----------------------------------------------------");
		ArrayList<String> tags = extraccionTagsFichero(nombreOut);
		if (tags.size()>0) {
			System.out.println("------------------------------------------");
			System.out.println("Extracción de los tags del fichero: " + nombreOut);
			System.out.println("Número de tags diferentes: " + tags.size());
			System.out.println("------------------------------------------");
		} else {
			System.out.println("------------------------------------------");
			System.out.println("PROBLEMA: No encuentra tags en el fichero de entrada!");
			System.out.println("------------------------------------------");
		}
		
		System.out.println("----------------------------------------------------");
		System.out.println("---- SEGUNDA PARTE DEL TRABAJO");
		System.out.println("----------------------------------------------------");
		
		System.out.println("----------------------------------------------------");
		System.out.println("---- TAREA 6 ");
		System.out.println("----------------------------------------------------");		
		Tag[] vector = ocurrenciaTagsFichero(nombreOut, tags);
		System.out.println("Longitud del vector de ocurrencias (tags) NO ORDENADO = " + vector.length);
		System.out.println("------------------------------------------");
		Tag[] vector_ordenado = ordernarTagsPorOcurrencias(vector);
		System.out.println("Longitud del vector de ocurrencias (tags) ORDENADO = " + vector_ordenado.length);
		System.out.println("------------------------------------------");
		System.out.println("Los 15 Tags más populares son :: ");
		escribeTagPopulares(vector_ordenado, 15);
		System.out.println("------------------------------------------");
			
		System.out.println("----------------------------------------------------");
		System.out.println("---- TAREA 7 ");
		System.out.println("----------------------------------------------------");			
		ArrayList<String> direcciones = URLsUsuario(nombreOut, "RTVCes");
		System.out.println("---- Chequeo extra: ");
		System.out.println("Número URLs en estructura resultado: " + direcciones.size());
		for(int i=0; i<direcciones.size(); i++) {
			System.out.println("-- " + direcciones.get(i) + " --");
		}
		System.out.println("----------------------------------------------------");
		
		System.out.println("----------------------------------------------------");
		System.out.println("---- TAREA 8 ");
		System.out.println("----------------------------------------------------");	
		ArrayList<String> dirFechas = URLsMeses(nombreOut, 9, 10);
		System.out.println("---- Chequeo extra: ");
		System.out.println("Número URLs en estructura resultado: " + dirFechas.size());
	
		System.out.println("----------------------------------------------------");
		System.out.println("---- TAREA 9 ");
		System.out.println("----------------------------------------------------");	
		if (direcciones.size()>0) {
			abrirEnlaceWeb(direcciones.get(0));
			System.out.println("Abriendo URL: " + direcciones.get(0));
			System.out.println("------------------------------------------");
		}
		else {
			System.out.println("------------------------------------------");
			System.out.println("PROBLEMA: No se encuentras URL disponibles!");
			System.out.println("------------------------------------------");
		}
		
		System.out.println("----------------------------------------------------");
		System.out.println("---- TERCERA PARTE DEL TRABAJO");
		System.out.println("----------------------------------------------------");
		
		System.out.println("----------------------------------------------------");
		System.out.println("---- TAREA 11 ");
		System.out.println("----------------------------------------------------");
		
		String[] ficheros = new String[3];
		ficheros[0] = new String("f1.txt");
		ficheros[1] = new String("f2.txt");
		ficheros[2] = new String("f3.txt");	
		
		analisisTagMensual(ficheros, 10);
		
		System.out.println("----------------------------------------------------");
		System.out.println("---- TAREA 12 ");
		System.out.println("----------------------------------------------------");
		histogramaTag(ficheros, "#LaPalmaEruption");
		System.out.println("----------------------------------------------------");
		
		entrada.close();
		
	}
}

