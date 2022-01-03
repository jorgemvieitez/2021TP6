/*
Tag.java : Proyecto TP6 de Fundamentos de Informática (clase Tag)
Fecha: Curso 2021-2022 (1ª convocatoria)
Autor: Jorge Miguel Moreno Vieitez
Titulación/Grupo: 912 (Grado en Ingenierías y Servicios de Telecomunicación)
*/

import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
}