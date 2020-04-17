package edu.upc.eetac.dsa;
import edu.upc.eetac.dsa.models.*;

import java.util.List;

public interface Covid19Manager {

    //Añadir un Brote
    int añadirBrote(String idBrote, String nombreBrote, List<Caso> listaCasos);
    //Añadir un Brote
    int añadirBrote(Brote brote);
    //Listar los brotes disponibles
    List<Brote> getListaCasos();
    //Añadir un caso sobre un brote
    int añadirCasoBrote(String idBrote, Caso caso);
    //Listado de casos de un determinado brote ordenado por classificación (confirmado,sospechoso y no caso)
    List<Brote> getListaBroteClassificado(String classificacion);
    void liberarRecursos();
    int numBrotes();
}
