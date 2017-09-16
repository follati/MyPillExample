package com.mypill.mypill;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.mypill.mypill.Adaptadores.AdapterList;
import com.mypill.mypill.Clases.DataSource;
import com.mypill.mypill.Clases.ImageNameList;
import com.mypill.mypill.Clases.Tratamiento;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TratamientoFragment extends Fragment {
    Button BtnAddterapia;
    private AdapterList adapterList;
    public List<ImageNameList> Items = new ArrayList<ImageNameList>();
    public List<Tratamiento> listTratamiento = new ArrayList<Tratamiento>();
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
        View view = inflater.inflate(R.layout.fragment_tratamiento, container, false);

        if (view != null)
        {
            BtnAddterapia = (Button) view.findViewById(R.id.BtnInicioTerapia);
            listview = (ListView) view.findViewById(R.id.lvListadoTratamiento);

            try{

                dataSource = new DataSource(getActivity());
                dataSource.open();

                listTratamiento = dataSource.GetTratamientos();
                if (listTratamiento.size() <= 0)
                {
                    BtnAddterapia.setText("Comience con su terapia y añada su primer medicamento!");
                }
                else
                {
                    BtnAddterapia.setText("Agregue nuevas terapias!");
                }

                for (Tratamiento item : listTratamiento) {
                    ImageNameList imagelist = new ImageNameList();
                    imagelist.setIdImage(item.getId_tratamiento());
                    imagelist.setImagen(R.drawable.item);
                    imagelist.setName(item.getMedicamento()+" en "+item.getMetodo()+", "+item.getRepeticion()+".");
                    Items.add(imagelist);
                }
                adapterList = new AdapterList(getActivity(), Items);
                listview.setAdapter(adapterList);
                adapterList.notifyDataSetChanged();
                registerForContextMenu(listview);
                dataSource.close();
            }catch (Exception ex){dataSource.close();}


            BtnAddterapia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PlanificarFargment fragment2 = new PlanificarFargment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, fragment2);
                    fragmentTransaction.commit();
                }
            });
        }
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Terapia");
    }

    public void InicioTerapia(View v) {
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Object toRemove = adapterList.getItem(info.position);
        ImageNameList itemSelected = (ImageNameList) toRemove;
        switch (item.getItemId())
        {
            case R.id.itemEliminar :
                try{
                    dataSource = new DataSource(getActivity());
                    dataSource.open();
                    Items.remove(info.position);
                    adapterList.notifyDataSetChanged();
                    dataSource.EliminarTratamiento(itemSelected.getIdImage());
                    dataSource.close();
                }catch (Exception ex){dataSource.close();}
                break;
            case R.id.itemDetalle :
                HistorialFragment fragment = new HistorialFragment ();
                Bundle args = new Bundle();
                args.putString("tratmiento", itemSelected.getIdImage()+"");
                fragment.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.commit();

                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        {
            inflater.inflate(R.menu.menu_tratamiento,menu);
            menu.setHeaderTitle("Tratamiento");
        }
    }


}
