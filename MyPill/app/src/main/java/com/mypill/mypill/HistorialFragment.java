package com.mypill.mypill;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mypill.mypill.Adaptadores.AdapterList;
import com.mypill.mypill.Clases.DataSource;
import com.mypill.mypill.Clases.Historial;
import com.mypill.mypill.Clases.ImageNameList;
import com.mypill.mypill.Clases.Tratamiento;

import java.util.ArrayList;
import java.util.List;


public class HistorialFragment extends Fragment {
    private static final String ARG_PARAM1 = "tratmiento";
    private String tratamiento;
    private AdapterList adapterList;
    public List<ImageNameList> Items = new ArrayList<ImageNameList>();
    public List<Historial> listDetTratamiento = new ArrayList<Historial>();
    ListView listview;
    private static DataSource dataSource;
    public HistorialFragment() {
        // Required empty public constructor
    }

    public static HistorialFragment newInstance(String param1) {
        HistorialFragment fragment = new HistorialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tratamiento = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial, container, false);
        listview = (ListView) view.findViewById(R.id.lvDetTratamiento);

        try{
            if (tratamiento.length() <= 0)
                return view;

            dataSource = new DataSource(getActivity());
            dataSource.open();
            Items.clear();
            listDetTratamiento.clear();
            listDetTratamiento = dataSource.GetHistorial(Integer.parseInt(tratamiento));
            for (Historial item : listDetTratamiento) {
                ImageNameList imagelist = new ImageNameList();
                imagelist.setIdImage(item.getId_historial());
                imagelist.setImagen(R.drawable.time);
                String hora = ( item.getHora() > 9 )?item.getHora()+"":"0"+item.getHora() ;
                String minuto = ( item.getMinuto() > 9 )?item.getMinuto()+"":"0"+item.getMinuto() ;
                //String hora = String.valueOf(item.getHora());
                imagelist.setName("Asignada el "+item.getFecha()+" a las "+hora+":"+minuto+"  Estado : "+item.getEstado()+".");
                Items.add(imagelist);
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
        getActivity().setTitle("Historial del  Tratamiento");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        {
            inflater.inflate(R.menu.menu_historial,menu);
            menu.setHeaderTitle("Dosis");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Object toRemove = adapterList.getItem(info.position);
        ImageNameList itemSelected = (ImageNameList) toRemove;
        try{
            dataSource = new DataSource(getActivity());
            dataSource.open();
            String estado = "Pendiente";
            switch (item.getItemId())
            {
                case R.id.itemSuministrada :
                    estado = "Suministrada";
                    break;
                case R.id.itemOlvidada :
                    estado = "No Suministrada";
                    break;
            }


            dataSource.ActualizarHistorial(itemSelected.getIdImage(), estado);
            Items.clear();
            listDetTratamiento.clear();
            listDetTratamiento = dataSource.GetHistorial(Integer.parseInt(tratamiento));
            for (Historial itemList : listDetTratamiento) {
                ImageNameList imagelist = new ImageNameList();
                imagelist.setIdImage(itemList.getId_historial());
                imagelist.setImagen(R.drawable.time);
                String hora = ( itemList.getHora() > 9 )?itemList.getHora()+"":"0"+itemList.getHora() ;
                String minuto = ( itemList.getMinuto() > 9 )?itemList.getMinuto()+"":"0"+itemList.getMinuto() ;
                imagelist.setName("Asignada el "+itemList.getFecha()+" a las "+hora+":"+minuto+"  Estado : "+itemList.getEstado()+".");
                Items.add(imagelist);
            }
            adapterList.notifyDataSetChanged();
        }catch (Exception ex){dataSource.close();}
        return super.onContextItemSelected(item);
    }

}
