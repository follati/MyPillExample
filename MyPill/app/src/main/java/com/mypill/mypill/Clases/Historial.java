package com.mypill.mypill.Clases;

/**
 * Created by Programador on 03/09/2017.
 */

public class Historial {


    private int id_historial;
    private int id_tratamiento;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Historial(int id_historial, int id_tratamiento, String fecha, String estado, int hora, int minuto) {
        this.id_historial = id_historial;
        this.id_tratamiento = id_tratamiento;
        this.fecha = fecha;
        this.estado = estado;
        this.hora = hora;
        this.minuto = minuto;
    }

    private String fecha;
    private String estado;

    public int getHora() {
        return hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    private int hora;
    private int minuto;

    public Historial(){}

    public Historial(int id_historial, int id_tratamiento, String estado) {
        this.id_historial = id_historial;
        this.id_tratamiento = id_tratamiento;
        this.estado = estado;
    }


    public void setId_historial(int id_historial) {
        this.id_historial = id_historial;
    }

    public void setId_tratamiento(int id_tratamiento) {
        this.id_tratamiento = id_tratamiento;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_historial() {
        return id_historial;
    }

    public int getId_tratamiento() {
        return id_tratamiento;
    }

    public String getEstado() {
        return estado;
    }
}
