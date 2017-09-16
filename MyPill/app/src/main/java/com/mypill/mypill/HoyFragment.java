package com.mypill.mypill;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mypill.mypill.Adaptadores.AdapterList;
import com.mypill.mypill.Clases.DataSource;
import com.mypill.mypill.Clases.Historial;
import com.mypill.mypill.Clases.ImageNameList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HoyFragment extends Fragment {
    private AdapterList adapterList;
    public List<ImageNameList> Items = new ArrayList<ImageNameList>();
    public List<Historial> listDetTratamiento = new ArrayList<Historial>();
    ListView listview;
    private static DataSource dataSource;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        View view = inflater.inflate(R.layout.fragment_hoy, container, false);
        listview = (ListView) view.findViewById(R.id.lvListHoy);
        try {
            dataSource = new DataSource(getActivity());
            dataSource.open();
            Items.clear();
            listDetTratamiento.clear();
            listDetTratamiento = dataSource.GetHistorial();

            for (Historial item : listDetTratamiento) {
                if (item.getFecha().equals(day+"/"+month+"/"+year))
                {
                    ImageNameList imagelist = new ImageNameList();
                    imagelist.setIdImage(item.getId_historial());
                    imagelist.setImagen(R.drawable.time);
                    String hora = ( item.getHora() > 9 )?item.getHora()+"":"0"+item.getHora() ;
                    String minuto = ( item.getMinuto() > 9 )?item.getMinuto()+"":"0"+item.getMinuto() ;
                    //String hora = String.valueOf(item.getHora());
                    imagelist.setName("Asignada el "+item.getFecha()+" a las "+hora+":"+minuto+"  Estado : "+item.getEstado()+".");
                    Items.add(imagelist);
                }
            }
            adapterList = new AdapterList(getActivity(), Items);
            listview.setAdapter(adapterList);
            adapterList.notifyDataSetChanged();
            registerForContextMenu(listview);


            dataSource.close();

        }catch (Exception ex){dataSource.close();}

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Hoy");
    }
}
