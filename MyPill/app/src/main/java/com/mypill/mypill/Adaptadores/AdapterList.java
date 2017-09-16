package com.mypill.mypill.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mypill.mypill.Clases.ImageList;
import com.mypill.mypill.Clases.ImageNameList;
import com.mypill.mypill.R;

import java.util.List;

/**
 * Created by Programador on 31/08/2017.
 */

public class AdapterList extends BaseAdapter {
    protected Activity activity;
    public List<ImageNameList> items;

    public AdapterList(Activity activity, List<ImageNameList> items) {
        this.activity = activity;
        this.items = items;
    }

    public void  agregar(Activity activity, List<ImageNameList> items) {
        this.activity = activity;
        this.items = items;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getIdImage();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        // Generamos una convertView por motivos de eficiencia
        View v = convertView;

        //Asociamos el layout de la lista que hemos creado
        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.list_image, null);
        }

        // Creamos un objeto directivo
        ImageNameList item = items.get(position);

        //Seteamos valores a los campos de la lista
        TextView name = (TextView) v.findViewById(R.id.txtName);
        name.setText(item.getName());

        ImageView imagen = (ImageView)v.findViewById(R.id.imgList);
        imagen.setImageResource(item.getImagen());
        return v;
    }
}
