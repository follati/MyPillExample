package com.mypill.mypill;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mypill.mypill.Clases.DataSource;
import com.mypill.mypill.Clases.Historial;
import com.mypill.mypill.Clases.ImageNameList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DiarioFragment extends Fragment {
    private TextView txtProgress;
    private ProgressBar progressBar;
    private int pStatus = 0;
    private Handler handler = new Handler();
    private Button btnHoy, btnSemana, btnMes, btnAnio;
    private static DataSource dataSource;
    public List<ImageNameList> Items = new ArrayList<ImageNameList>();
    public List<Historial> listDetTratamiento = new ArrayList<Historial>();

    public DiarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diario, container, false);
        txtProgress = (TextView) view.findViewById(R.id.txtProgress);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        btnHoy = (Button) view.findViewById(R.id.btnHooy);
        btnSemana = (Button) view.findViewById(R.id.Btn7dias);
        btnMes = (Button) view.findViewById(R.id.Btn30dias);
        btnAnio = (Button) view.findViewById(R.id.Btnanio);
        GetPorcentaje(1);

        btnHoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetPorcentaje(1);
        }
        });

        btnSemana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetPorcentaje(7);
            }
        });

        btnMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetPorcentaje(30);
            }
        });

        btnAnio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetPorcentaje(365);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Diario");
    }

    public void GetPorcentaje(int rango)
    {
        try{
            dataSource = new DataSource(getActivity());
            dataSource.open();
            Items.clear();
            listDetTratamiento.clear();
            listDetTratamiento = dataSource.GetHistorial();
            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            float suministrados = 0;
            float totales = 0;
            for (Historial item : listDetTratamiento) {
                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                switch (rango)
                {
                    case 1:
                        if (item.getFecha().equals(day+"/"+month+"/"+year))
                        {
                            if(item.getEstado().equals("Suministrada"))
                            suministrados++;
                            totales++;
                        }
                        break;
                    case 7:
                        for (int i=0; i < 7; i++)
                        {
                            if(day > 30){day = 1; month++;}
                            if (item.getFecha().equals(day+"/"+month+"/"+year))
                            {
                                if(item.getEstado().equals("Suministrada"))
                                suministrados++;
                                totales++;
                            }
                            day++;
                        }
                        break;
                    case 30:

                        for (int i=0; i <30; i++)
                        {
                            if(day > 30){day = 1; month++;}
                            if (item.getFecha().equals(day+"/"+month+"/"+year))
                            {
                                if(item.getEstado().equals("Suministrada"))
                                suministrados++;
                                totales++;
                            }
                            day++;
                        }
                        break;
                    case 365:
                        for (int i=0; i <365; i++)
                        {
                            if(day > 30){day = 1; month++;}
                            if (item.getFecha().equals(day+"/"+month+"/"+year))
                            {
                                if(item.getEstado().equals("Suministrada"))
                                suministrados++;
                                totales++;
                            }

                            day++;
                        }
                        break;
                }
            }
            dataSource.close();
            float porcentaje = (suministrados/totales) * 100;
            SetPorcentaje(Math.round(porcentaje));


        }catch (Exception ex){dataSource.close();}
    }


    public void SetPorcentaje(int porcentaje)
    {
        progressBar.clearFocus();
        progressBar.refreshDrawableState();
        pStatus = 0;
        final int progreso = porcentaje;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pStatus <= progreso) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(pStatus);
                            txtProgress.setText(pStatus + " %");
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus++;
                }
            }
        }).start();

    }

}
