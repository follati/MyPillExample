package com.mypill.mypill.Clases;

/**
 * Created by Programador on 29/08/2017.
 */

public class ImageList {
    private int IdImage;
    private String name;
    private String descripcion;
    private int imagen;

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public ImageList(int idMenu, String name, String descripcion, int imagen) {
        this.IdImage = idMenu;
        this.name = name;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public ImageList(){}

    public void setIdImage(int idMenu) {
        IdImage = idMenu;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImagen() {
        return imagen;
    }

    public int getIdImage() {
        return IdImage;
    }

    public String getName() {
        return name;
    }

    public String getDescripcion() {
        return descripcion;
    }



}

