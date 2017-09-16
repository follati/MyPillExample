package com.mypill.mypill;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mypill.mypill.Adaptadores.AdapterImages;
import com.mypill.mypill.Clases.ImageList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EquipoFragment extends Fragment {
    private AdapterImages AImages;
    ListView listItem;
    Context context;
    TextView textView;
    public List<ImageList> imagesList = new ArrayList<ImageList>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_equipo, container, false);

        ImageList  item1 = new ImageList(1,"Añadir Doctor","Guardar información de contactos y citas del médico", R.drawable.doctor);
        ImageList  item2 = new ImageList(2,"Invitar amigos y familiares","Compartir su progreso", R.drawable.add_user);
        imagesList.add(item1);
        imagesList.add(item2);

            if (view != null)
            {
                listItem = (ListView) view.findViewById(R.id.lvListado);
                AImages = new AdapterImages(getActivity(), imagesList);
                listItem.setAdapter(AImages);
                AImages.notifyDataSetChanged();
            }

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Equipo");
    }
}
