package com.mypill.mypill.Clases;

/**
 * Created by Programador on 31/08/2017.
 */

public class ImageNameList {
        private int IdImage;
        private String name;
        private int imagen;

        public void setImagen(int imagen) {
            this.imagen = imagen;
        }

        public ImageNameList(int idMenu, String name, int imagen) {
            this.IdImage = idMenu;
            this.name = name;
            this.imagen = imagen;
        }

        public ImageNameList(){}

        public void setIdImage(int idMenu) {
            IdImage = idMenu;
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

}

