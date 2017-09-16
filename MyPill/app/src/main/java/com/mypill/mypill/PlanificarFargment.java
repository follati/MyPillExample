package com.mypill.mypill;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mypill.mypill.Adaptadores.AdapterImages;
import com.mypill.mypill.Adaptadores.AdapterList;
import com.mypill.mypill.Clases.ImageList;
import com.mypill.mypill.Clases.ImageNameList;

import java.util.ArrayList;
import java.util.List;


public class PlanificarFargment extends Fragment {
    private AdapterList adapterList;
    ListView listItem;
    Context context;
    TextView textView;

    public List<ImageNameList> listItems = new ArrayList<ImageNameList>();

    public PlanificarFargment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planificar_fargment, container, false);

        if (view != null) {

            listItem = (ListView) view.findViewById(R.id.lvListado);
            ImageNameList item1 = new ImageNameList(1, "Medicación", R.drawable.medicine);
            ImageNameList item2 = new ImageNameList(2, "Mediciones", R.drawable.line_chart);
            ImageNameList item3 = new ImageNameList(3, "Actividad", R.drawable.flag);
            ImageNameList item4 = new ImageNameList(4, "Control de síntomas", R.drawable.notepad);
            listItems.add(item1);
            listItems.add(item2);
            listItems.add(item3);
            listItems.add(item4);

            adapterList = new AdapterList(getActivity(), listItems);
            listItem.setAdapter(adapterList);
            adapterList.notifyDataSetChanged();

            listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int actividad = listItems.get(i).getIdImage();

                    switch (actividad)
                    {
                        case 1:
                            AddMedication fragment2 = new AddMedication();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content_frame, fragment2);
                            fragmentTransaction.commit();
                            break;
                        case 2:

                            break;

                    }
                }
            });

        }



        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Planificar");
    }

}
