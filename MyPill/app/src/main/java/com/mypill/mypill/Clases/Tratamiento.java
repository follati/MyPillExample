package com.mypill.mypill.Clases;

/**
 * Created by Programador on 03/09/2017.
 */

public class Tratamiento {
    private int id_tratamiento;
    private String medicamento;
    private String metodo;
    private String duracion;
    private String repeticion;
    private int hora;
    private int minuto;

    public void setId_tratamiento(int id_tratamiento) {
        this.id_tratamiento = id_tratamiento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public void setRepeticion(String repeticion) {
        this.repeticion = repeticion;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }



    public int getId_tratamiento() {
        return id_tratamiento;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public String getMetodo() {
        return metodo;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getRepeticion() {
        return repeticion;
    }

    public int getHora() {
        return hora;
    }

    public int getMinuto() {
        return minuto;
    }



    public Tratamiento()
    {
    }
    public Tratamiento(int id_tratamiento, String medicamento, String metodo, String duracion, String repeticion, int hora, int minuto) {
        this.id_tratamiento = id_tratamiento;
        this.medicamento = medicamento;
        this.metodo = metodo;
        this.duracion = duracion;
        this.repeticion = repeticion;
        this.hora = hora;
        this.minuto = minuto;
    }







}
