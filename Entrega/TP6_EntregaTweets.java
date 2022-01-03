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
import java.awt.Desktop;

public class TP6_EntregaTweets {

	
	
	
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

